package id.patchedpixel.zoomify.config.lib.gui.image;

import com.mojang.blaze3d.systems.RenderSystem;
import id.patchedpixel.zoomify.config.lib.impl.utils.ConfigConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class ImageRendererManager {
    private static final ExecutorService SINGLE_THREAD_EXECUTOR = Executors.newSingleThreadExecutor(task -> new Thread(task, "Config Image Prep"));

    private static final Map<Identifier, CompletableFuture<ImageRenderer>> IMAGE_CACHE = new ConcurrentHashMap<>();

    public static <T extends ImageRenderer> Optional<T> getImage(Identifier id) {
        if (IMAGE_CACHE.containsKey(id)) {
            return Optional.ofNullable((T) IMAGE_CACHE.get(id).getNow(null));
        }

        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public static <T extends ImageRenderer> CompletableFuture<T> registerOrGetImage(Identifier id, Supplier<ImageRendererFactory> factorySupplier) {
        if (IMAGE_CACHE.containsKey(id)) {
            return (CompletableFuture<T>) IMAGE_CACHE.get(id);
        }

        var future = new CompletableFuture<ImageRenderer>();
        IMAGE_CACHE.put(id, future);

        ImageRendererFactory factory = factorySupplier.get();
        SINGLE_THREAD_EXECUTOR.submit(() -> {
            Supplier<Optional<ImageRendererFactory.ImageSupplier>> supplier =
                    factory.requiresOffThreadPreparation()
                            ? new CompletedSupplier<>(safelyPrepareFactory(id, factory))
                            : () -> safelyPrepareFactory(id, factory);

            Minecraft.getInstance().execute(() -> completeImageFactory(id, supplier, future));
        });

        return (CompletableFuture<T>) future;
    }

    @Deprecated
    public static <T extends ImageRenderer> CompletableFuture<T> registerImage(Identifier id, ImageRendererFactory factory) {
        return registerOrGetImage(id, () -> factory);
    }

    private static <T extends ImageRenderer> void completeImageFactory(Identifier id, Supplier<Optional<ImageRendererFactory.ImageSupplier>> supplier, CompletableFuture<ImageRenderer> future) {
        RenderSystem.assertOnRenderThread();

        ImageRendererFactory.ImageSupplier completableImage = supplier.get().orElse(null);
        if (completableImage == null) {
            return;
        }

        // sanity check - this should never happen
        if (future.isDone()) {
            ConfigConstants.LOGGER.error("Image '{}' was already completed", id);
            return;
        }

        ImageRenderer image;
        try {
            image = completableImage.completeImage();
        } catch (Exception e) {
            ConfigConstants.LOGGER.error("Failed to create image '{}'", id, e);
            return;
        }

        future.complete(image);
    }

    public static void closeAll() {
        SINGLE_THREAD_EXECUTOR.shutdownNow();
        IMAGE_CACHE.values().removeIf(future -> {
            if (future.isDone()) {
                future.join().close();
            }
            return true;
        });
    }

    static Optional<ImageRendererFactory.ImageSupplier> safelyPrepareFactory(Identifier id, ImageRendererFactory factory) {
        try {
            return Optional.of(factory.prepareImage());
        } catch (Exception e) {
            ConfigConstants.LOGGER.error("Failed to prepare image '{}'", id, e);
            IMAGE_CACHE.remove(id);
            return Optional.empty();
        }
    }

    private record CompletedSupplier<T>(T get) implements Supplier<T> {
    }

}

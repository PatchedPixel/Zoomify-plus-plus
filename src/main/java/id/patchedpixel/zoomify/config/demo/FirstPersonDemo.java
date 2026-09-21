package id.patchedpixel.zoomify.config.demo;

import id.patchedpixel.zoomify.config.lib.gui.image.impl.AnimatedDynamicTextureImage;
import id.patchedpixel.zoomify.zoom.ZoomHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static id.patchedpixel.zoomify.utils.MinecraftExt.zoomifyRl;

public class FirstPersonDemo extends ZoomDemoImageRenderer {
    public static final int TEX_WIDTH = 1916;
    public static final int TEX_HEIGHT = 910;

    public static final ResourceLocation WORLD_TEXTURE = zoomifyRl("textures/demo/zoom-world.webp");
    public static final ResourceLocation HAND_TEXTURE = zoomifyRl("textures/demo/zoom-hand.webp");

    private boolean keepHandFov = false;

    private final CompletableFuture<AnimatedDynamicTextureImage> handRenderer = makeWebp(HAND_TEXTURE);
    private final CompletableFuture<AnimatedDynamicTextureImage> worldRenderer = makeWebp(WORLD_TEXTURE);

    public FirstPersonDemo(ZoomHelper zoomHelper, ControlEmulation zoomControl) {
        super(zoomHelper, zoomControl);
    }

    public boolean isKeepHandFov() {
        return keepHandFov;
    }

    public void setKeepHandFov(boolean keepHandFov) {
        this.keepHandFov = keepHandFov;
    }

    @Override
    public int render(GuiGraphics graphics, int x, int y, int renderWidth, float deltaTime) {
        double ratio = renderWidth / (double) TEX_WIDTH;
        int renderHeight = (int) (TEX_HEIGHT * ratio);
        if (!handRenderer.isDone() || !worldRenderer.isDone()) {
            return renderHeight;
        }

        graphics.enableScissor(x, y, x + renderWidth, y + renderHeight);

        graphics.pose().pushPose();
        graphics.pose().translate((float) x, (float) y, 0.0F);
        graphics.pose().scale((float) ratio, (float) ratio, 1.0F);

        float zoomScale = (float) getZoomHelper().getZoomDivisor(deltaTime);
        graphics.pose().pushPose();
        graphics.pose().translate(TEX_WIDTH / 2f, TEX_HEIGHT / 2f, 0.0F);
        graphics.pose().scale(zoomScale, zoomScale, 1.0F);
        graphics.pose().translate(-TEX_WIDTH / 2f, -TEX_HEIGHT / 2f, 0.0F);

        try {
            worldRenderer.get().render(graphics, 0, 0, TEX_WIDTH, deltaTime);

            if (keepHandFov) graphics.pose().popPose();
            handRenderer.get().render(graphics, 0, 0, TEX_WIDTH, deltaTime);
            if (!keepHandFov) graphics.pose().popPose();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        graphics.pose().popPose();

        graphics.disableScissor();

        return renderHeight;
    }

    @Override
    public void close() {
        Optional.ofNullable(worldRenderer.getNow(null)).ifPresent(AnimatedDynamicTextureImage::close);
        Optional.ofNullable(handRenderer.getNow(null)).ifPresent(AnimatedDynamicTextureImage::close);
    }
}

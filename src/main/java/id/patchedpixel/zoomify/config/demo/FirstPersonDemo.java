package id.patchedpixel.zoomify.config.demo;

import dev.isxander.yacl3.gui.image.impl.AnimatedDynamicTextureImage;
import id.patchedpixel.zoomify.zoom.ZoomHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static id.patchedpixel.zoomify.utils.MinecraftExt.zoomifyRl;

public class FirstPersonDemo extends ZoomDemoImageRenderer {
    public static final int TEX_WIDTH = 1916;
    public static final int TEX_HEIGHT = 910;

    public static final Identifier WORLD_TEXTURE = zoomifyRl("textures/demo/zoom-world.webp");
    public static final Identifier HAND_TEXTURE = zoomifyRl("textures/demo/zoom-hand.webp");

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
    public int render(GuiGraphicsExtractor graphics, int x, int y, int renderWidth, float deltaTime) {
        double ratio = renderWidth / (double) TEX_WIDTH;
        int renderHeight = (int) (TEX_HEIGHT * ratio);
        if (!handRenderer.isDone() || !worldRenderer.isDone()) {
            return renderHeight;
        }

        graphics.enableScissor(x, y, x + renderWidth, y + renderHeight);

        graphics.pose().pushMatrix();
        graphics.pose().translate((float) x, (float) y);
        graphics.pose().scale((float) ratio, (float) ratio);

        float zoomScale = (float) getZoomHelper().getZoomDivisor(deltaTime);
        graphics.pose().pushMatrix();
        graphics.pose().translate(TEX_WIDTH / 2f, TEX_HEIGHT / 2f);
        graphics.pose().scale(zoomScale, zoomScale);
        graphics.pose().translate(-TEX_WIDTH / 2f, -TEX_HEIGHT / 2f);

        try {
            worldRenderer.get().render(graphics, 0, 0, TEX_WIDTH, deltaTime);

            if (keepHandFov) graphics.pose().popMatrix();
            handRenderer.get().render(graphics, 0, 0, TEX_WIDTH, deltaTime);
            if (!keepHandFov) graphics.pose().popMatrix();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        graphics.pose().popMatrix();

        graphics.disableScissor();

        return renderHeight;
    }

    @Override
    public void close() {
        Optional.ofNullable(worldRenderer.getNow(null)).ifPresent(AnimatedDynamicTextureImage::close);
        Optional.ofNullable(handRenderer.getNow(null)).ifPresent(AnimatedDynamicTextureImage::close);
    }
}

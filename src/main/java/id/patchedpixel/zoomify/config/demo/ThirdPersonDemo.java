package id.patchedpixel.zoomify.config.demo;

import dev.isxander.yacl3.gui.image.impl.AnimatedDynamicTextureImage;
import id.patchedpixel.zoomify.zoom.ZoomHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static id.patchedpixel.zoomify.utils.MinecraftExt.zoomifyRl;

public class ThirdPersonDemo extends ZoomDemoImageRenderer {
    public static final int TEX_WIDTH = 1915;
    public static final int TEX_HEIGHT = 910;

    public static final Identifier PLAYER_VIEW = zoomifyRl("textures/demo/third-person-view.webp");
    public static final Identifier HUD_TEXTURE = zoomifyRl("textures/demo/third-person-hud.webp");

    private final CompletableFuture<AnimatedDynamicTextureImage> thirdPersonViewRenderer = makeWebp(PLAYER_VIEW);
    private final CompletableFuture<AnimatedDynamicTextureImage> hudRenderer = makeWebp(HUD_TEXTURE);

    private boolean renderHud = true;

    public ThirdPersonDemo(ZoomHelper zoomHelper, ControlEmulation zoomControl) {
        super(zoomHelper, zoomControl);
    }

    public boolean isRenderHud() {
        return renderHud;
    }

    public void setRenderHud(boolean renderHud) {
        this.renderHud = renderHud;
    }

    @Override
    public int render(GuiGraphicsExtractor graphics, int x, int y, int renderWidth, float deltaTime) {
        double ratio = renderWidth / (double) TEX_WIDTH;
        int renderHeight = (int) (TEX_HEIGHT * ratio);
        if (!thirdPersonViewRenderer.isDone() || !hudRenderer.isDone()) {
            return renderHeight;
        }

        graphics.enableScissor(x, y, x + renderWidth, y + renderHeight);

        graphics.pose().pushMatrix();
        graphics.pose().translate((float) x, (float) y);
        graphics.pose().scale((float) ratio, (float) ratio);

        float zoomScale = (float) getZoomHelper().getZoomDivisor(deltaTime);
        graphics.pose().pushMatrix();
        graphics.pose().translate(FirstPersonDemo.TEX_WIDTH / 2f, FirstPersonDemo.TEX_HEIGHT / 2f);
        graphics.pose().scale(zoomScale, zoomScale);
        graphics.pose().translate(-FirstPersonDemo.TEX_WIDTH / 2f, -FirstPersonDemo.TEX_HEIGHT / 2f);

        try {
            thirdPersonViewRenderer.get().render(graphics, 0, 0, TEX_WIDTH, deltaTime);

            graphics.pose().popMatrix();

            if (renderHud)
                hudRenderer.get().render(graphics, 0, 0, TEX_WIDTH, deltaTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        graphics.pose().popMatrix();
        graphics.disableScissor();

        return renderHeight;
    }

    @Override
    public void close() {
        Optional.ofNullable(thirdPersonViewRenderer.getNow(null)).ifPresent(AnimatedDynamicTextureImage::close);
        Optional.ofNullable(hudRenderer.getNow(null)).ifPresent(AnimatedDynamicTextureImage::close);
    }
}

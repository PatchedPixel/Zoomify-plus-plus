package id.patchedpixel.zoomify.config.demo;

import id.patchedpixel.zoomify.config.lib.gui.image.ImageRenderer;
import id.patchedpixel.zoomify.config.lib.gui.image.ImageRendererManager;
import id.patchedpixel.zoomify.config.lib.gui.image.impl.AnimatedDynamicTextureImage;
import id.patchedpixel.zoomify.zoom.ZoomHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public abstract class ZoomDemoImageRenderer implements ImageRenderer {
    private final ZoomHelper zoomHelper;
    private final ControlEmulation zoomControl;

    private boolean keyDown = false;
    private int scrollTiers = 0;

    protected ZoomDemoImageRenderer(ZoomHelper zoomHelper, ControlEmulation zoomControl) {
        this.zoomHelper = zoomHelper;
        this.zoomControl = zoomControl;
        zoomControl.setup(this);
    }

    public ZoomHelper getZoomHelper() {
        return zoomHelper;
    }

    public boolean getKeyDown() {
        return keyDown;
    }

    public void setKeyDown(boolean keyDown) {
        this.keyDown = keyDown;
    }

    public int getScrollTiers() {
        return scrollTiers;
    }

    public void setScrollTiers(int scrollTiers) {
        this.scrollTiers = scrollTiers;
    }

    @Override
    public abstract int render(GuiGraphics graphics, int x, int y, int renderWidth, float deltaTime);

    @Override
    public void tick() {
        zoomHelper.tick(keyDown, scrollTiers);
        zoomControl.tick(this);
    }

    public void pause() {
        zoomHelper.setToZero();
        zoomControl.pause(this);
    }

    protected static CompletableFuture<AnimatedDynamicTextureImage> makeWebp(ResourceLocation id) {
        return ImageRendererManager.registerOrGetImage(id, () -> AnimatedDynamicTextureImage.createWEBPFromTexture(id));
    }
}

package id.patchedpixel.zoomify.config.lib.gui.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import id.patchedpixel.zoomify.config.lib.platform.YACLPlatform;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class YACLRenderHelper {
    private static final ResourceLocation SLIDER_LOCATION = new ResourceLocation("textures/gui/slider.png");

    public static void renderButtonTexture(GuiGraphics graphics, int x, int y, int width, int height, boolean enabled, boolean focused) {
        int textureV;
        if (enabled) {
            textureV = focused ? 60 : 40;
        } else {
            textureV = focused ? 20 : 0;
        }

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        graphics.blitNineSliced(SLIDER_LOCATION, x, y, width, height, 20, 4, 200, 20, 0, textureV);
    }

    public static ResourceLocation getSpriteLocation(String path) {
        return YACLPlatform.rl("textures/gui/sprites/" + path + ".png");
    }
}

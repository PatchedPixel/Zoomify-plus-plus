package id.patchedpixel.zoomify.mixins.zoom.secondary;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import id.patchedpixel.zoomify.Zoomify;
import id.patchedpixel.zoomify.config.ZoomifySettings;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Gui.class)
public class GuiMixin {
    // FIXME: When secondary zoom hud is still rendered not hided, but the settings is hud hide when secondary zoom.
    @WrapMethod(method = "render")
    private void preventHudRender(GuiGraphics guiGraphics, float partialTick, Operation<Void> original) {
        if (!Zoomify.INSTANCE.getSecondaryZooming() || !ZoomifySettings.Companion.getSecondaryHideHUDOnZoom().get()) {
            System.out.println(Zoomify.INSTANCE.getSecondaryZooming());
            original.call(guiGraphics, partialTick);
        }
    }
}

package id.patchedpixel.zoomify.mixins.zoom.secondary;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import id.patchedpixel.zoomify.Zoomify;
import id.patchedpixel.zoomify.config.ZoomifySettings;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(net.minecraft.client.gui.Hud.class)
public class HudMixin {
    @WrapMethod(method = "extractRenderState")
    private void preventHudRender(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, Operation<Void> original) {
        if (!Zoomify.INSTANCE.getSecondaryZooming() || !ZoomifySettings.Companion.getSecondaryHideHUDOnZoom().get()) {
            original.call(graphics, deltaTracker);
        }
    }
}

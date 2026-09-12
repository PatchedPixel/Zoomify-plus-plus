package id.patchedpixel.zoomify.mixins.zoom;

import id.patchedpixel.zoomify.Zoomify;
import id.patchedpixel.zoomify.config.ZoomifySettings;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @ModifyArg(
            method = "turnPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/SmoothDouble;getNewDeltaValue(DD)D"
            ),
            index = 1
    )
    private double modifyCinematicSmoothness(double smoother) {
        if (Zoomify.INSTANCE.getZooming() && ZoomifySettings.Companion.getCinematicCamera().get() > 0)
            return smoother / (ZoomifySettings.Companion.getCinematicCamera().get() / 100.0);

        return smoother;
    }
}

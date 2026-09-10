package id.patchedpixel.zoomify.mixins.zoom;

import id.patchedpixel.zoomify.Zoomify;
import id.patchedpixel.zoomify.config.ZoomifySettings;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class CameraMixin {
    @Inject(method = "calculateFov", at = @At("RETURN"), cancellable = true)
    private void modifyFovWithZoom(float partialTicks, CallbackInfoReturnable<Float> cir) {
        float fov = cir.getReturnValue();
        cir.setReturnValue(fov / Zoomify.getZoomDivisor(partialTicks));
    }

    @Inject(method = "calculateHudFov", at = @At("RETURN"), cancellable = true)
    private void modifyHudFovWithZoom(float partialTicks, CallbackInfoReturnable<Float> cir) {
        if (ZoomifySettings.Companion.getAffectHandFov().get()) {
            float fov = cir.getReturnValue();
            cir.setReturnValue(fov / Zoomify.getZoomDivisor(partialTicks));
        }
    }

    @ModifyArg(
            method = "extractRenderState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/entity/ClientAvatarState;getInterpolatedBob(F)F"
            ),
            index = 0
    )
    private float modifyBobbingIntensity(float interpolatedBob) {
        if (!ZoomifySettings.Companion.getRelativeViewBobbing().get())
            return interpolatedBob;

        return (float) (interpolatedBob / Mth.lerp(0.2, 1.0, Zoomify.INSTANCE.getPreviousZoomDivisor()));
    }
}

package id.patchedpixel.zoomify.mixins.zoom;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import id.patchedpixel.zoomify.Zoomify;
import id.patchedpixel.zoomify.config.ZoomifySettings;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void modifyFovWithZoom(Camera camera, float partialTicks, boolean useFovSetting, CallbackInfoReturnable<Double> cir) {
        if (useFovSetting) {
            double fov = cir.getReturnValue();
            cir.setReturnValue(fov / Zoomify.getZoomDivisor(partialTicks));
        } else if (ZoomifySettings.Companion.getAffectHandFov().get()) {
            double fov = cir.getReturnValue();
            cir.setReturnValue(fov / Zoomify.getZoomDivisor(partialTicks));
        }
    }

    @WrapOperation(
            method = "bobView",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;lerp(FFF)F"
            )
    )
    private float modifyBobbingIntensity(float delta, float start, float end, Operation<Float> original) {
        float interpolatedBob = original.call(delta, start, end);

        if (!ZoomifySettings.Companion.getRelativeViewBobbing().get())
            return interpolatedBob;

        return (float) (interpolatedBob / Mth.lerp(0.2, 1.0, Zoomify.INSTANCE.getPreviousZoomDivisor()));
    }
}

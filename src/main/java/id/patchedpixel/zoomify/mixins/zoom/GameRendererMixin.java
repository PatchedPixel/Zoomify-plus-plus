package id.patchedpixel.zoomify.mixins.zoom;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import id.patchedpixel.zoomify.Zoomify;
import id.patchedpixel.zoomify.config.ZoomifySettings;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
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

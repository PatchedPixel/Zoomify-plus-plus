package id.patchedpixel.zoomify.mixins.spyglass;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import id.patchedpixel.zoomify.config.SpyglassBehaviour;
import id.patchedpixel.zoomify.config.ZoomifySettings;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerEntityMixin {
    @ModifyExpressionValue(
        method = "getFieldOfViewModifier",
        at = @At(value = "CONSTANT", args = "floatValue=0.1f")
    )
    private float modifySpyglassFovMultiplier(float multiplier) {
        if (ZoomifySettings.Companion.getSpyglassBehaviour().get() != SpyglassBehaviour.COMBINE)
            return 1f;
        return multiplier;
    }
}

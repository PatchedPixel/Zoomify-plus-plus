package id.patchedpixel.zoomify.mixins.spyglass;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import id.patchedpixel.zoomify.Zoomify;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(net.minecraft.client.gui.Hud.class)
public class HudMixin {
    @Shadow @Final private Minecraft minecraft;

    @ModifyExpressionValue(
        method = "extractCameraOverlays",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;isScoping()Z"
        )
    )
    private boolean shouldRenderSpyglassOverlay(boolean isUsingSpyglass) {
        return Zoomify.shouldRenderOverlay(minecraft.player, isUsingSpyglass);
    }
}

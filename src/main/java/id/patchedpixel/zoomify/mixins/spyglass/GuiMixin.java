package id.patchedpixel.zoomify.mixins.spyglass;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import id.patchedpixel.zoomify.Zoomify;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public class GuiMixin {
    @Shadow @Final private Minecraft minecraft;

    @ModifyExpressionValue(
            method = "render(Lnet/minecraft/client/gui/GuiGraphics;F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isScoping()Z"
            )
    )
    private boolean shouldRenderSpyglassOverlay(boolean isUsingSpyglass) {
        return Zoomify.shouldRenderOverlay(minecraft.player, isUsingSpyglass);
    }
}

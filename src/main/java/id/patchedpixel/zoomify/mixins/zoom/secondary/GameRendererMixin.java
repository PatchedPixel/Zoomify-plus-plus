package id.patchedpixel.zoomify.mixins.zoom.secondary;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import id.patchedpixel.zoomify.Zoomify;
import id.patchedpixel.zoomify.config.ZoomifySettings;
import net.minecraft.client.renderer.GameRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @ModifyExpressionValue(
            method = "render",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/Options;hideGui:Z",
                    opcode = Opcodes.GETFIELD
            )
    )
    private boolean shouldHideHUD(boolean p_91526_) {
        return p_91526_ || (Zoomify.INSTANCE.getSecondaryZooming() && ZoomifySettings.Companion.getSecondaryHideHUDOnZoom().get());
    }
}

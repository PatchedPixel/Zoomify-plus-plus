package id.patchedpixel.zoomify.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import id.patchedpixel.zoomify.Zoomify;
import id.patchedpixel.zoomify.config.lib.gui.image.ImageRendererManager;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "destroy", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;close()V", shift = At.Shift.BEFORE))
    private void closeImages(CallbackInfo ci) {
        ImageRendererManager.closeAll();
    }

    @ModifyExpressionValue(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/packs/resources/ReloadableResourceManager;createReload(Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Ljava/util/List;)Lnet/minecraft/server/packs/resources/ReloadInstance;"
            )
    )
    private ReloadInstance onReloadResources(ReloadInstance resourceReload) {
        resourceReload.done().thenRun(Zoomify.INSTANCE::onGameFinishedLoading);
        return resourceReload;
    }
}

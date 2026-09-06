package id.patchedpixel.zoomify.integrations;

import dev.isxander.controlify.api.ControlifyApi;
import dev.isxander.controlify.api.entrypoint.ControlifyEntrypoint;
import dev.isxander.controlify.api.entrypoint.InitContext;
import dev.isxander.controlify.api.entrypoint.PreInitContext;
import dev.isxander.controlify.api.event.ControlifyEvents;
import id.patchedpixel.zoomify.Zoomify;
import id.patchedpixel.zoomify.config.ZoomifySettings;
import net.minecraft.util.Mth;

public class ControlifyIntegration implements ControlifyEntrypoint {
    public static final ControlifyIntegration INSTANCE = new ControlifyIntegration();

    private ControlifyIntegration() {}

    @Override
    public void onControllersDiscovered(ControlifyApi controlify) {

    }

    @Override
    public void onControlifyInit(InitContext context) {
        ControlifyEvents.LOOK_INPUT_MODIFIER.register(it -> {
            it.lookInput().x /= (float) Mth.lerp(ZoomifySettings.Companion.getRelativeSensitivity().get() / 100.0, 1.0, Zoomify.INSTANCE.getPreviousZoomDivisor());
            it.lookInput().y /= (float) Mth.lerp(ZoomifySettings.Companion.getRelativeSensitivity().get() / 100.0, 1.0, Zoomify.INSTANCE.getPreviousZoomDivisor());
        });
    }

    @Override
    public void onControlifyPreInit(PreInitContext context) {}
}

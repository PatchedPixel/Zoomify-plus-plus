package id.patchedpixel.zoomify.config;

import id.patchedpixel.zoomify.config.lib.config.v3.ConfigEntry;
import id.patchedpixel.zoomify.utils.TransitionType;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public enum Presets {
    Default("zoomify.gui.preset.default", settings -> {
        for (ConfigEntry<?> entry : settings.getAllSettings()) {
            setToDefault(entry);
        }
    }),
    Optifine("zoomify.gui.preset.optifine", settings -> {
        Default.apply(settings);

        settings.getZoomInTransition().set(TransitionType.INSTANT);
        settings.getZoomOutTransition().set(TransitionType.INSTANT);
        settings.getScrollZoom().set(false);
        settings.getRelativeSensitivity().set(0);
        settings.getRelativeViewBobbing().set(false);
        settings.getCinematicCamera().set(100);
    }),
    OkZoomer("zoomify.gui.preset.ok_zoomer", settings -> {
        Default.apply(settings);

        settings.getZoomInTime().set(0.25);
        settings.getZoomOutTime().set(0.25);
        settings.getRelativeSensitivity().set(50);
        settings.getRelativeViewBobbing().set(false);
        settings.getScrollZoomSmoothness().set(25);
    });

    private final Component displayName;
    private final Consumer<ZoomifySettings> applyFunc;

    Presets(String displayName, Consumer<ZoomifySettings> applyFunc) {
        this.displayName = Component.translatable(displayName);
        this.applyFunc = applyFunc;
    }

    public Component getDisplayName() {
        return displayName;
    }

    /** Equivalent to Kotlin's `ZoomifySettings.() -> Unit` extension-function property. */
    public void apply(ZoomifySettings settings) {
        applyFunc.accept(settings);
    }

    private static <T> void setToDefault(ConfigEntry<T> entry) {
        entry.set(entry.defaultValue());
    }
}

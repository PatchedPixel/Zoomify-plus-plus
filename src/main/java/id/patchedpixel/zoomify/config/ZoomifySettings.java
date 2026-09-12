package id.patchedpixel.zoomify.config;

import com.mojang.serialization.Codec;
import dev.isxander.yacl3.config.v3.ConfigEntry;
import dev.isxander.yacl3.config.v3.JsonFileCodecConfig;
import id.patchedpixel.zoomify.utils.TransitionType;
import net.neoforged.fml.loading.FMLPaths;

public class ZoomifySettings extends JsonFileCodecConfig<ZoomifySettings> {

    public ZoomifySettings() {
        super(FMLPaths.CONFIGDIR.get().resolve("zoomify.json"));
    }

    private final ConfigEntry<Integer> initialZoom = register("initialZoom", 4, Codec.INT);

    private final ConfigEntry<Double> zoomInTime = register("zoomInTime", 1.0, Codec.DOUBLE);
    private final ConfigEntry<Double> zoomOutTime = register("zoomOutTime", 0.5, Codec.DOUBLE);

    private final ConfigEntry<TransitionType> zoomInTransition = register("zoomInTransition", TransitionType.EASE_OUT_EXP, TransitionType.CODEC);
    private final ConfigEntry<TransitionType> zoomOutTransition = register("zoomOutTransition", TransitionType.EASE_OUT_EXP, TransitionType.CODEC);

    private final ConfigEntry<Boolean> affectHandFov = register("affectHandFov", true, Codec.BOOL);

    private final ConfigEntry<Boolean> retainZoomSteps = register("retainZoomSteps", false, Codec.BOOL);

    private final ConfigEntry<Boolean> scrollZoom = register("scrollZoom", true, Codec.BOOL);
    private final ConfigEntry<Integer> scrollStepCount = register("scrollStepCount", 10, Codec.INT);
    private final ConfigEntry<Integer> zoomPerStep = register("zoomPerStep", 150, Codec.INT);
    private final ConfigEntry<Integer> scrollZoomSmoothness = register("scrollZoomSmoothness", 70, Codec.INT);

    private final ConfigEntry<ZoomKeyBehaviour> zoomKeyBehaviour = register("zoomKeyBehaviour", ZoomKeyBehaviour.HOLD, ZoomKeyBehaviour.CODEC);

    private boolean keybindScrolling = false;
    private final ConfigEntry<Boolean> _keybindScrolling = register("_keybindScrolling", keybindScrolling, Codec.BOOL);

    private final ConfigEntry<Integer> relativeSensitivity = register("relativeSensitivity", 100, Codec.INT);
    private final ConfigEntry<Boolean> relativeViewBobbing = register("relativeViewBobbing", true, Codec.BOOL);

    private final ConfigEntry<Integer> cinematicCamera = register("cinematicCamera", 0, Codec.INT);

    private final ConfigEntry<SpyglassBehaviour> spyglassBehaviour = register("spyglassBehaviour", SpyglassBehaviour.COMBINE, SpyglassBehaviour.CODEC);
    private final ConfigEntry<OverlayVisibility> spyglassOverlayVisibility = register("spyglassOverlayVisibility", OverlayVisibility.HOLDING, OverlayVisibility.CODEC);
    private final ConfigEntry<SoundBehaviour> spyglassSoundBehaviour = register("spyglassSoundBehaviour", SoundBehaviour.WITH_OVERLAY, SoundBehaviour.CODEC);

    private final ConfigEntry<Integer> secondaryZoomAmount = register("secondaryZoomAmount", 4, Codec.INT);
    private final ConfigEntry<Double> secondaryZoomInTime = register("secondaryZoomInTime", 10.0, Codec.DOUBLE);
    private final ConfigEntry<Double> secondaryZoomOutTime = register("secondaryZoomOutTime", 1.0, Codec.DOUBLE);
    private final ConfigEntry<Boolean> secondaryHideHUDOnZoom = register("secondaryHideHUDOnZoom", true, Codec.BOOL);

    private boolean firstLaunch = false;
    private final ConfigEntry<Boolean> _firstLaunch = register("_firstLaunch", true, Codec.BOOL);

    private final ConfigEntry<?>[] allSettings = new ConfigEntry<?>[] {
            initialZoom,
            zoomInTime,
            zoomOutTime,
            zoomInTransition,
            zoomOutTransition,
            affectHandFov,
            retainZoomSteps,
            scrollZoom,
            scrollStepCount,
            zoomPerStep,
            scrollZoomSmoothness,
            zoomKeyBehaviour,
            _keybindScrolling,
            relativeSensitivity,
            relativeViewBobbing,
            cinematicCamera,
            spyglassBehaviour,
            spyglassOverlayVisibility,
            spyglassSoundBehaviour,
            secondaryZoomAmount,
            secondaryZoomInTime,
            secondaryZoomOutTime,
            secondaryHideHUDOnZoom,
            _firstLaunch,
    };

    public ZoomifySettings(ZoomifySettings settings) {
        this();
        this.initialZoom.set(settings.initialZoom.get());
        this.zoomInTime.set(settings.zoomInTime.get());
        this.zoomOutTime.set(settings.zoomOutTime.get());
        this.zoomInTransition.set(settings.zoomInTransition.get());
        this.zoomOutTransition.set(settings.zoomOutTransition.get());
        this.affectHandFov.set(settings.affectHandFov.get());
        this.retainZoomSteps.set(settings.retainZoomSteps.get());
        this.scrollZoom.set(settings.scrollZoom.get());
        this.scrollStepCount.set(settings.scrollStepCount.get());
        this.zoomPerStep.set(settings.zoomPerStep.get());
        this.scrollZoomSmoothness.set(settings.scrollZoomSmoothness.get());
        this.zoomKeyBehaviour.set(settings.zoomKeyBehaviour.get());
        this.keybindScrolling = settings.keybindScrolling;
        this._keybindScrolling.set(settings._keybindScrolling.get());
        this.relativeSensitivity.set(settings.relativeSensitivity.get());
        this.relativeViewBobbing.set(settings.relativeViewBobbing.get());
        this.cinematicCamera.set(settings.cinematicCamera.get());
        this.spyglassBehaviour.set(settings.spyglassBehaviour.get());
        this.spyglassOverlayVisibility.set(settings.spyglassOverlayVisibility.get());
        this.spyglassSoundBehaviour.set(settings.spyglassSoundBehaviour.get());
        this.secondaryZoomAmount.set(settings.secondaryZoomAmount.get());
        this.secondaryZoomInTime.set(settings.secondaryZoomInTime.get());
        this.secondaryZoomOutTime.set(settings.secondaryZoomOutTime.get());
        this.secondaryHideHUDOnZoom.set(settings.secondaryHideHUDOnZoom.get());
        this.firstLaunch = settings.firstLaunch;
        this._firstLaunch.set(settings._firstLaunch.get());
    }

    public ConfigEntry<Integer> getInitialZoom() { return initialZoom; }

    public ConfigEntry<Double> getZoomInTime() { return zoomInTime; }
    public ConfigEntry<Double> getZoomOutTime() { return zoomOutTime; }

    public ConfigEntry<TransitionType> getZoomInTransition() { return zoomInTransition; }
    public ConfigEntry<TransitionType> getZoomOutTransition() { return zoomOutTransition; }

    public ConfigEntry<Boolean> getAffectHandFov() { return affectHandFov; }

    public ConfigEntry<Boolean> getRetainZoomSteps() { return retainZoomSteps; }

    public ConfigEntry<Boolean> getScrollZoom() { return scrollZoom; }
    public ConfigEntry<Integer> getScrollStepCount() { return scrollStepCount; }
    public ConfigEntry<Integer> getZoomPerStep() { return zoomPerStep; }
    public ConfigEntry<Integer> getScrollZoomSmoothness() { return scrollZoomSmoothness; }

    public ConfigEntry<ZoomKeyBehaviour> getZoomKeyBehaviour() { return zoomKeyBehaviour; }

    public boolean getKeybindScrolling() { return keybindScrolling; }
    public void setKeybindScrolling(boolean keybindScrolling) { this.keybindScrolling = keybindScrolling; }
    public ConfigEntry<Boolean> get_keybindScrolling() { return _keybindScrolling; }

    public ConfigEntry<Integer> getRelativeSensitivity() { return relativeSensitivity; }
    public ConfigEntry<Boolean> getRelativeViewBobbing() { return relativeViewBobbing; }

    public ConfigEntry<Integer> getCinematicCamera() { return cinematicCamera; }

    public ConfigEntry<SpyglassBehaviour> getSpyglassBehaviour() { return spyglassBehaviour; }
    public ConfigEntry<OverlayVisibility> getSpyglassOverlayVisibility() { return spyglassOverlayVisibility; }
    public ConfigEntry<SoundBehaviour> getSpyglassSoundBehaviour() { return spyglassSoundBehaviour; }

    public ConfigEntry<Integer> getSecondaryZoomAmount() { return secondaryZoomAmount; }
    public ConfigEntry<Double> getSecondaryZoomInTime() { return secondaryZoomInTime; }
    public ConfigEntry<Double> getSecondaryZoomOutTime() { return secondaryZoomOutTime; }
    public ConfigEntry<Boolean> getSecondaryHideHUDOnZoom() { return secondaryHideHUDOnZoom; }

    public boolean isFirstLaunch() { return firstLaunch; }
    public void setFirstLaunch(boolean firstLaunch) { this.firstLaunch = firstLaunch; }
    public ConfigEntry<Boolean> get_firstLaunch() { return _firstLaunch; }

    public ConfigEntry<?>[] getAllSettings() { return allSettings; }

    /**
     * Equivalent to Kotlin's `companion object : ZoomifySettings()`.
     * Acts as the single, global instance of the settings, loaded from disk on first access.
     */
    public static final class Companion extends ZoomifySettings {
        private Companion() {
            super();
            if (!loadFromFile()) {
                saveToFile();
            }

            if (get_firstLaunch().get()) {
                setFirstLaunch(true);
                get_firstLaunch().set(false);
                saveToFile();
            }

            get_keybindScrolling().set(getKeybindScrolling());
        }
    }

    public static final Companion Companion = new Companion();
}

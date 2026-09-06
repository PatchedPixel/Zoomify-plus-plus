package id.patchedpixel.zoomify.zoom;

import id.patchedpixel.zoomify.config.ZoomifySettings;
import net.minecraft.util.Mth;

public final class DefaultZoomHelpers {
    private DefaultZoomHelpers() {}

    public static ZoomHelper regularZoomHelper(ZoomifySettings settings) {
        return new ZoomHelper(
                new TransitionInterpolator(
                        () -> settings.getZoomInTransition().get(),
                        () -> settings.getZoomOutTransition().get(),
                        () -> settings.getZoomInTime().get(),
                        () -> settings.getZoomOutTime().get()
                ),
                new SmoothInterpolator(() -> Mth.lerp(
                        settings.getScrollZoomSmoothness().get() / 100.0,
                        1.0,
                        0.1
                )),
                () -> settings.getInitialZoom().get(),
                () -> settings.getZoomPerStep().get(),
                () -> settings.getScrollStepCount().get()
        );
    }

    public static ZoomHelper secondaryZoomHelper(ZoomifySettings settings) {
        return new ZoomHelper(
                new TimedInterpolator(
                        () -> settings.getSecondaryZoomInTime().get(),
                        () -> settings.getSecondaryZoomOutTime().get()
                ),
                InstantInterpolator.INSTANCE,
                () -> settings.getSecondaryZoomAmount().get(),
                () -> 100,
                () -> 0
        );
    }
}

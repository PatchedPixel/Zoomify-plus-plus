package id.patchedpixel.zoomify.zoom;

import net.minecraft.util.Mth;

import java.util.function.IntSupplier;

public class ZoomHelper {
    private final Interpolator initialInterpolator;
    private final Interpolator scrollInterpolator;

    private final IntSupplier initialZoom;
    private final IntSupplier zoomPerStep;
    private final IntSupplier maxScrollTiers;

    private double prevInitialInterpolation = 0.0;
    private double initialInterpolation = 0.0;

    private boolean zoomingLastTick = false;

    private double prevScrollInterpolation = 0.0;
    private double scrollInterpolation = 0.0;
    private int lastScrollTier = 0;

    private boolean resetting = false;
    private double resetMultiplier = 0.0;

    public ZoomHelper(
            Interpolator initialInterpolator,
            Interpolator scrollInterpolator,
            IntSupplier initialZoom,
            IntSupplier zoomPerStep,
            IntSupplier maxScrollTiers
    ) {
        this.initialInterpolator = initialInterpolator;
        this.scrollInterpolator = scrollInterpolator;
        this.initialZoom = initialZoom;
        this.zoomPerStep = zoomPerStep;
        this.maxScrollTiers = maxScrollTiers;
    }

    public int maxScrollTiers() {
        return maxScrollTiers.getAsInt();
    }

    public void tick(boolean zooming, int scrollTiers) {
        tick(zooming, scrollTiers, 0.05);
    }

    public void tick(boolean zooming, int scrollTiers, double lastFrameDuration) {
        tickInitial(zooming, lastFrameDuration);
        tickScroll(scrollTiers, lastFrameDuration);
    }

    private void tickInitial(boolean zooming, double lastFrameDuration) {
        if (zooming && !zoomingLastTick)
            resetting = false;

        double targetZoom = zooming ? 1.0 : 0.0;
        prevInitialInterpolation = initialInterpolation;
        initialInterpolation =
                initialInterpolator.tickInterpolation(targetZoom, initialInterpolation, lastFrameDuration);
        prevInitialInterpolation = initialInterpolator.modifyPrevInterpolation(prevInitialInterpolation);
        if (!initialInterpolator.isSmooth())
            prevInitialInterpolation = initialInterpolation;
        zoomingLastTick = zooming;
    }

    private void tickScroll(int scrollTiers, double lastFrameDuration) {
        if (scrollTiers > lastScrollTier)
            resetting = false;

        // scrollTiers can be negative (zoom out) or positive (zoom in)
        // Normalize to -1..1 range where 0 is initial zoom
        double targetZoom =
                maxScrollTiers() > 0
                        ? (double) scrollTiers / maxScrollTiers()
                        : 0.0;

        prevScrollInterpolation = scrollInterpolation;
        scrollInterpolation = scrollInterpolator.tickInterpolation(targetZoom, scrollInterpolation, lastFrameDuration);
        prevScrollInterpolation = scrollInterpolator.modifyPrevInterpolation(prevScrollInterpolation);
        if (!initialInterpolator.isSmooth())
            prevInitialInterpolation = initialInterpolation;
        lastScrollTier = scrollTiers;
    }

    public double getZoomDivisor() {
        return getZoomDivisor(1f);
    }

    public double getZoomDivisor(float tickDelta) {
        double initialMultiplier = getInitialZoomMultiplier(tickDelta);
        double baseDivisor = 1 / initialMultiplier;

        // Get the smoothed scroll interpolation value (-1 to 1)
        // Negative = zoom out, Positive = zoom in
        double scrollT;
        if (resetting) {
            scrollT = 0.0;
        } else {
            scrollT = scrollInterpolator.isSmooth()
                    ? scrollInterpolator.modifyInterpolation(
                    Mth.lerp((double) tickDelta, prevScrollInterpolation, scrollInterpolation))
                    : scrollInterpolation;
        }

        // zoomPerStep is stored as percentage (e.g., 150 = 1.5x per step)
        double stepMultiplier = zoomPerStep.getAsInt() / 100.0;
        int maxSteps = maxScrollTiers();
        // Current step number (interpolated for smooth animation)
        // Can be negative for zooming out below initial zoom
        double currentStep = scrollT * maxSteps;

        // Geometric interpolation for perceptually uniform zoom steps
        // Each step multiplies the divisor by stepMultiplier
        // divisor = baseDivisor × stepMultiplier^currentStep
        // When currentStep is negative, this zooms out (divisor < baseDivisor)
        double rawDivisor = baseDivisor * Math.pow(stepMultiplier, currentStep);

        // Safety limits to prevent rendering issues
        // Min 0.5x zoom (divisor 0.5), max 500x zoom (divisor 500)
        double finalDivisor = Mth.clamp(rawDivisor, 0.5, 500.0);

        if (initialInterpolation == 0.0 && scrollInterpolation == 0.0) resetting = false;
        if (!resetting) resetMultiplier = 1 / finalDivisor;

        return finalDivisor;
    }

    private double getInitialZoomMultiplier(float tickDelta) {
        return Mth.lerp(
                initialInterpolator.isSmooth()
                        ? initialInterpolator.modifyInterpolation(
                        Mth.lerp((double) tickDelta, prevInitialInterpolation, initialInterpolation))
                        : initialInterpolation,
                1.0,
                !resetting ? 1 / (double) initialZoom.getAsInt() : resetMultiplier
        );
    }

    public void reset() {
        if (!resetting && scrollInterpolation > 0.0) {
            resetting = true;
            scrollInterpolation = 0.0;
            prevScrollInterpolation = 0.0;
        }
    }

    public void setToZero() {
        setToZero(true, true);
    }

    public void setToZero(boolean initial, boolean scroll) {
        if (initial) {
            initialInterpolation = 0.0;
            prevInitialInterpolation = 0.0;
            zoomingLastTick = false;
        }
        if (scroll) {
            scrollInterpolation = 0.0;
            prevScrollInterpolation = 0.0;
            lastScrollTier = 0;
        }
        resetting = false;
    }

    public void skipInitial() {
        initialInterpolation = 1.0;
        prevInitialInterpolation = 1.0;
    }
}

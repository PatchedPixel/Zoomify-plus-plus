package id.patchedpixel.zoomify.zoom;

/**
 * Originally a Kotlin `sealed class`; only subclasses within this package
 * (TimedInterpolator, TransitionInterpolator, SmoothInterpolator) may extend it.
 */
public abstract class LinearInterpolator implements Interpolator {
    private boolean goingIn = true;

    protected boolean isGoingIn() {
        return goingIn;
    }

    @Override
    public double tickInterpolation(double targetInterpolation, double currentInterpolation, double tickDelta) {
        if (targetInterpolation > currentInterpolation) {
            goingIn = true;
            return Math.min(currentInterpolation + getTimeIncrement(false, tickDelta, targetInterpolation, currentInterpolation), targetInterpolation);
        } else if (targetInterpolation < currentInterpolation) {
            goingIn = false;
            return Math.max(currentInterpolation - getTimeIncrement(true, tickDelta, targetInterpolation, currentInterpolation), targetInterpolation);
        }
        goingIn = true;
        return targetInterpolation;
    }

    @Override
    public boolean isSmooth() {
        return true;
    }

    public abstract double getTimeIncrement(
            boolean zoomingOut,
            double tickDelta,
            double targetInterpolation,
            double currentInterpolation
    );
}

package id.patchedpixel.zoomify.zoom;

import id.patchedpixel.zoomify.utils.TransitionType;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class TransitionInterpolator extends TimedInterpolator {
    private final Supplier<TransitionType> transitionInSupplier;
    /** Equivalent to Kotlin's `val transitionOut = { transitionOut().opposite() }` */
    private final Supplier<TransitionType> transitionOut;

    private TransitionType activeTransition;
    private TransitionType inactiveTransition;
    private double prevTargetInterpolation = 0.0;
    private boolean justSwappedTransition = false;

    public TransitionInterpolator(
            Supplier<TransitionType> transitionIn,
            Supplier<TransitionType> transitionOut,
            DoubleSupplier timeIn,
            DoubleSupplier timeOut
    ) {
        super(timeIn, timeOut);
        this.transitionInSupplier = transitionIn;
        this.transitionOut = () -> transitionOut.get().opposite();

        this.activeTransition = transitionIn();
        this.inactiveTransition = transitionOut();
    }

    private TransitionType transitionIn() {
        return transitionInSupplier.get();
    }

    private TransitionType transitionOut() {
        return transitionOut.get();
    }

    @Override
    public double tickInterpolation(double targetInterpolation, double currentInterpolation, double tickDelta) {
        double currentInterpolationMod = currentInterpolation;

        if (targetInterpolation > currentInterpolation) {
            activeTransition = transitionIn();
            inactiveTransition = transitionOut();

            if (prevTargetInterpolation < targetInterpolation && activeTransition.hasInverse()) {
                justSwappedTransition = true;
                currentInterpolationMod = activeTransition.inverse(inactiveTransition.apply(currentInterpolationMod));
            }
        } else if (targetInterpolation < currentInterpolation) {
            activeTransition = transitionOut();
            inactiveTransition = transitionIn();

            if (prevTargetInterpolation > targetInterpolation && activeTransition.hasInverse()) {
                justSwappedTransition = true;
                currentInterpolationMod = activeTransition.inverse(inactiveTransition.apply(currentInterpolationMod));
            }
        }

        prevTargetInterpolation = targetInterpolation;

        if (activeTransition == TransitionType.INSTANT)
            return targetInterpolation;

        return super.tickInterpolation(targetInterpolation, currentInterpolationMod, tickDelta);
    }

    @Override
    public double modifyInterpolation(double interpolation) {
        return activeTransition.apply(interpolation);
    }

    @Override
    public double modifyPrevInterpolation(double interpolation) {
        if (justSwappedTransition) {
            justSwappedTransition = false;
            return activeTransition.inverse(inactiveTransition.apply(interpolation));
        }
        return interpolation;
    }

    @Override
    public boolean isSmooth() {
        return !justSwappedTransition && super.isSmooth() && activeTransition != TransitionType.INSTANT;
    }
}

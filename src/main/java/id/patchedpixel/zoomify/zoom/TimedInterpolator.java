package id.patchedpixel.zoomify.zoom;

import java.util.function.DoubleSupplier;

public class TimedInterpolator extends LinearInterpolator {
    private final DoubleSupplier timeIn;
    private final DoubleSupplier timeOut;

    public TimedInterpolator(DoubleSupplier timeIn, DoubleSupplier timeOut) {
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public double timeIn() {
        return timeIn.getAsDouble();
    }

    public double timeOut() {
        return timeOut.getAsDouble();
    }

    @Override
    public double getTimeIncrement(
            boolean zoomingOut,
            double tickDelta,
            double targetInterpolation,
            double currentInterpolation
    ) {
        return tickDelta / (zoomingOut ? timeOut() : timeIn());
    }

    @Override
    public boolean isSmooth() {
        return (isGoingIn() && timeIn() > 0.0) || (!isGoingIn() && timeOut() > 0.0);
    }
}

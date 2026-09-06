package id.patchedpixel.zoomify.utils;

@FunctionalInterface
public interface Transition {
    double apply(double t);

    default double inverse(double x) {
        throw new UnsupportedOperationException();
    }

    default boolean hasInverse() {
        try {
            inverse(0.0);
            return true;
        } catch (UnsupportedOperationException e) {
            return false;
        }
    }
}

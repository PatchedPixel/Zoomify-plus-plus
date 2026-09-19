package id.patchedpixel.zoomify.config.lib.api;

@FunctionalInterface
public interface OptionEventListener<T> {
    void onEvent(Option<T> option, Event event);

    enum Event {
        INITIAL,
        STATE_CHANGE,
        AVAILABILITY_CHANGE,
        OTHER,
    }
}

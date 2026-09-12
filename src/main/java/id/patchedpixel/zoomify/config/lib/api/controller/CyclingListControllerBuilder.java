package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.CyclingListControllerBuilderImpl;

public interface CyclingListControllerBuilder<T> extends ValueFormattableController<T, CyclingListControllerBuilder<T>> {
    @SuppressWarnings("unchecked")
    CyclingListControllerBuilder<T> values(T... values);

    CyclingListControllerBuilder<T> values(Iterable<? extends T> values);

    static <T> CyclingListControllerBuilder<T> create(Option<T> option) {
        return new CyclingListControllerBuilderImpl<>(option);
    }
}

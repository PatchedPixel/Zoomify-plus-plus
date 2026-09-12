package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.EnumControllerBuilderImpl;

public interface EnumControllerBuilder<T extends Enum<T>> extends ValueFormattableController<T, EnumControllerBuilder<T>> {
    EnumControllerBuilder<T> enumClass(Class<T> enumClass);

    static <T extends Enum<T>> EnumControllerBuilder<T> create(Option<T> option) {
        return new EnumControllerBuilderImpl<>(option);
    }
}

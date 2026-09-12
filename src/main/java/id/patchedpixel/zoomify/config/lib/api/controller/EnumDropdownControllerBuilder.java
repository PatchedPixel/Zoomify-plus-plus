package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.EnumDropdownControllerBuilderImpl;

public interface EnumDropdownControllerBuilder<E extends Enum<E>> extends ValueFormattableController<E, EnumDropdownControllerBuilder<E>> {
    static <E extends Enum<E>> EnumDropdownControllerBuilder<E> create(Option<E> option) {
        return new EnumDropdownControllerBuilderImpl<>(option);
    }
}

package id.patchedpixel.zoomify.config.lib.impl.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.ControllerBuilder;

public abstract class AbstractControllerBuilderImpl<T> implements ControllerBuilder<T> {
    protected final Option<T> option;

    protected AbstractControllerBuilderImpl(Option<T> option) {
        this.option = option;
    }
}

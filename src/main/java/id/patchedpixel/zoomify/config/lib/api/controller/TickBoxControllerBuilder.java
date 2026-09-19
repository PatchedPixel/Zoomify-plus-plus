package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.TickBoxControllerBuilderImpl;

public interface TickBoxControllerBuilder extends ControllerBuilder<Boolean> {
    static TickBoxControllerBuilder create(Option<Boolean> option) {
        return new TickBoxControllerBuilderImpl(option);
    }
}

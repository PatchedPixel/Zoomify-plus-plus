package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.StringControllerBuilderImpl;

public interface StringControllerBuilder extends ControllerBuilder<String> {
    static StringControllerBuilder create(Option<String> option) {
        return new StringControllerBuilderImpl(option);
    }
}

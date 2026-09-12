package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.FloatFieldControllerBuilderImpl;

public interface FloatFieldControllerBuilder extends NumberFieldControllerBuilder<Float, FloatFieldControllerBuilder> {
    static FloatFieldControllerBuilder create(Option<Float> option) {
        return new FloatFieldControllerBuilderImpl(option);
    }
}

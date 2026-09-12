package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.DoubleFieldControllerBuilderImpl;

public interface DoubleFieldControllerBuilder extends NumberFieldControllerBuilder<Double, DoubleFieldControllerBuilder> {
    static DoubleFieldControllerBuilder create(Option<Double> option) {
        return new DoubleFieldControllerBuilderImpl(option);
    }
}

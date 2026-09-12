package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.DoubleSliderControllerBuilderImpl;

public interface DoubleSliderControllerBuilder extends SliderControllerBuilder<Double, DoubleSliderControllerBuilder> {
    static DoubleSliderControllerBuilder create(Option<Double> option) {
        return new DoubleSliderControllerBuilderImpl(option);
    }
}

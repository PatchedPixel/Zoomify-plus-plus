package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.FloatSliderControllerBuilderImpl;

public interface FloatSliderControllerBuilder extends SliderControllerBuilder<Float, FloatSliderControllerBuilder> {
    static FloatSliderControllerBuilder create(Option<Float> option) {
        return new FloatSliderControllerBuilderImpl(option);
    }
}

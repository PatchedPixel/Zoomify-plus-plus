package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.IntegerSliderControllerBuilderImpl;

public interface IntegerSliderControllerBuilder extends SliderControllerBuilder<Integer, IntegerSliderControllerBuilder> {
    static IntegerSliderControllerBuilder create(Option<Integer> option) {
        return new IntegerSliderControllerBuilderImpl(option);
    }
}

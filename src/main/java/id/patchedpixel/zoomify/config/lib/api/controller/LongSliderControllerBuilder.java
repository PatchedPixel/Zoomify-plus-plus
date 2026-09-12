package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.LongSliderControllerBuilderImpl;

public interface LongSliderControllerBuilder extends SliderControllerBuilder<Long, LongSliderControllerBuilder> {
    static LongSliderControllerBuilder create(Option<Long> option) {
        return new LongSliderControllerBuilderImpl(option);
    }
}

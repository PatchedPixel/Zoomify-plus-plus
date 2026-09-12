package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.LongFieldControllerBuilderImpl;

public interface LongFieldControllerBuilder extends NumberFieldControllerBuilder<Long, LongFieldControllerBuilder> {
    static LongFieldControllerBuilder create(Option<Long> option) {
        return new LongFieldControllerBuilderImpl(option);
    }
}

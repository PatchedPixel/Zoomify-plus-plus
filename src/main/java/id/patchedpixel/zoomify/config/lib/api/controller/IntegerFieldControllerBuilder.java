package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.IntegerFieldControllerBuilderImpl;

public interface IntegerFieldControllerBuilder extends NumberFieldControllerBuilder<Integer, IntegerFieldControllerBuilder> {
    static IntegerFieldControllerBuilder create(Option<Integer> option) {
        return new IntegerFieldControllerBuilderImpl(option);
    }
}

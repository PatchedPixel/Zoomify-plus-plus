package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.BooleanControllerBuilderImpl;

public interface BooleanControllerBuilder extends ValueFormattableController<Boolean, BooleanControllerBuilder> {
    BooleanControllerBuilder coloured(boolean coloured);

    BooleanControllerBuilder onOffFormatter();
    BooleanControllerBuilder yesNoFormatter();
    BooleanControllerBuilder trueFalseFormatter();

    static BooleanControllerBuilder create(Option<Boolean> option) {
        return new BooleanControllerBuilderImpl(option);
    }
}

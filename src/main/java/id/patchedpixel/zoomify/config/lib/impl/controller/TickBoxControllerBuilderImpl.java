package id.patchedpixel.zoomify.config.lib.impl.controller;

import id.patchedpixel.zoomify.config.lib.api.Controller;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.TickBoxControllerBuilder;
import id.patchedpixel.zoomify.config.lib.gui.controllers.TickBoxController;

public class TickBoxControllerBuilderImpl extends AbstractControllerBuilderImpl<Boolean> implements TickBoxControllerBuilder {
    public TickBoxControllerBuilderImpl(Option<Boolean> option) {
        super(option);
    }

    @Override
    public Controller<Boolean> build() {
        return new TickBoxController(option);
    }
}

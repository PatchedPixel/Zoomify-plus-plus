package id.patchedpixel.zoomify.config.lib.impl.controller;

import id.patchedpixel.zoomify.config.lib.api.Controller;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.StringControllerBuilder;
import id.patchedpixel.zoomify.config.lib.gui.controllers.string.StringController;

public class StringControllerBuilderImpl extends AbstractControllerBuilderImpl<String> implements StringControllerBuilder {
    public StringControllerBuilderImpl(Option<String> option) {
        super(option);
    }

    @Override
    public Controller<String> build() {
        return new StringController(option);
    }
}

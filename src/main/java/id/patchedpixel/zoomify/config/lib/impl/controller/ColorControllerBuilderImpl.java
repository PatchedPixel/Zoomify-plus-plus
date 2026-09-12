package id.patchedpixel.zoomify.config.lib.impl.controller;

import id.patchedpixel.zoomify.config.lib.api.Controller;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.ColorControllerBuilder;
import id.patchedpixel.zoomify.config.lib.gui.controllers.ColorController;

import java.awt.Color;

public class ColorControllerBuilderImpl extends AbstractControllerBuilderImpl<Color> implements ColorControllerBuilder {
    private boolean allowAlpha = false;

    public ColorControllerBuilderImpl(Option<Color> option) {
        super(option);
    }

    @Override
    public ColorControllerBuilder allowAlpha(boolean allowAlpha) {
        this.allowAlpha = allowAlpha;
        return this;
    }

    @Override
    public Controller<Color> build() {
        return new ColorController(option, allowAlpha);
    }
}

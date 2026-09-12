package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.ColorControllerBuilderImpl;

import java.awt.Color;

public interface ColorControllerBuilder extends ControllerBuilder<Color> {
    ColorControllerBuilder allowAlpha(boolean allowAlpha);

    static ColorControllerBuilder create(Option<Color> option) {
        return new ColorControllerBuilderImpl(option);
    }
}

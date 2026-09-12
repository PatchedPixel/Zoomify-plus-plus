package id.patchedpixel.zoomify.config.lib.config.v2.impl.autogen;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.ColorControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.ControllerBuilder;
import id.patchedpixel.zoomify.config.lib.config.v2.api.ConfigField;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.SimpleOptionFactory;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.ColorField;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.OptionAccess;

import java.awt.Color;

public class ColorFieldImpl extends SimpleOptionFactory<ColorField, Color> {
    @Override
    protected ControllerBuilder<Color> createController(ColorField annotation, ConfigField<Color> field, OptionAccess storage, Option<Color> option) {
        return ColorControllerBuilder.create(option)
                .allowAlpha(annotation.allowAlpha());
    }
}

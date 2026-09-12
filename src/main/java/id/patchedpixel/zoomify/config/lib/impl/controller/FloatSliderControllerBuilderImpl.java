package id.patchedpixel.zoomify.config.lib.impl.controller;

import id.patchedpixel.zoomify.config.lib.api.Controller;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.FloatSliderControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.ValueFormatter;
import id.patchedpixel.zoomify.config.lib.gui.controllers.slider.FloatSliderController;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

public class FloatSliderControllerBuilderImpl extends AbstractControllerBuilderImpl<Float> implements FloatSliderControllerBuilder {
    private float min, max;
    private float step;
    private ValueFormatter<Float> formatter = FloatSliderController.DEFAULT_FORMATTER::apply;

    public FloatSliderControllerBuilderImpl(Option<Float> option) {
        super(option);
    }

    @Override
    public FloatSliderControllerBuilder range(Float min, Float max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public FloatSliderControllerBuilder step(Float step) {
        this.step = step;
        return this;
    }

    @Override
    public FloatSliderControllerBuilder formatValue(ValueFormatter<Float> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<Float> build() {
        return FloatSliderController.createInternal(option, min, max, step, formatter);
    }
}

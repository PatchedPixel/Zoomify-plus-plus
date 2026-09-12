package id.patchedpixel.zoomify.config.lib.impl.controller;

import id.patchedpixel.zoomify.config.lib.api.Controller;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.DoubleSliderControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.ValueFormatter;
import id.patchedpixel.zoomify.config.lib.gui.controllers.slider.DoubleSliderController;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

public class DoubleSliderControllerBuilderImpl extends AbstractControllerBuilderImpl<Double> implements DoubleSliderControllerBuilder {
    private double min, max;
    private double step;
    private ValueFormatter<Double> formatter = DoubleSliderController.DEFAULT_FORMATTER::apply;

    public DoubleSliderControllerBuilderImpl(Option<Double> option) {
        super(option);
    }

    @Override
    public DoubleSliderControllerBuilder range(Double min, Double max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public DoubleSliderControllerBuilder step(Double step) {
        this.step = step;
        return this;
    }

    @Override
    public DoubleSliderControllerBuilder formatValue(ValueFormatter<Double> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<Double> build() {
        return DoubleSliderController.createInternal(option, min, max, step, formatter);
    }
}

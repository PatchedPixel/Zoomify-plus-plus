package id.patchedpixel.zoomify.config.lib.impl.controller;

import id.patchedpixel.zoomify.config.lib.api.Controller;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.IntegerSliderControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.ValueFormatter;
import id.patchedpixel.zoomify.config.lib.gui.controllers.slider.IntegerSliderController;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

public class IntegerSliderControllerBuilderImpl extends AbstractControllerBuilderImpl<Integer> implements IntegerSliderControllerBuilder {
    private int min, max;
    private int step;
    private ValueFormatter<Integer> formatter = IntegerSliderController.DEFAULT_FORMATTER::apply;

    public IntegerSliderControllerBuilderImpl(Option<Integer> option) {
        super(option);
    }

    @Override
    public IntegerSliderControllerBuilder range(Integer min, Integer max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public IntegerSliderControllerBuilder step(Integer step) {
        this.step = step;
        return this;
    }

    @Override
    public IntegerSliderControllerBuilder formatValue(ValueFormatter<Integer> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<Integer> build() {
        return IntegerSliderController.createInternal(option, min, max, step, formatter);
    }
}

package id.patchedpixel.zoomify.config.lib.impl.controller;

import id.patchedpixel.zoomify.config.lib.api.Controller;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.BooleanControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.ValueFormatter;
import id.patchedpixel.zoomify.config.lib.gui.controllers.BooleanController;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.Validate;

import java.util.function.Function;

public class BooleanControllerBuilderImpl extends AbstractControllerBuilderImpl<Boolean> implements BooleanControllerBuilder {
    private boolean coloured = false;
    private ValueFormatter<Boolean> formatter = BooleanController.ON_OFF_FORMATTER::apply;

    public BooleanControllerBuilderImpl(Option<Boolean> option) {
        super(option);
    }

    @Override
    public BooleanControllerBuilder coloured(boolean coloured) {
        this.coloured = coloured;
        return this;
    }

    @Override
    public BooleanControllerBuilder formatValue(ValueFormatter<Boolean> formatter) {
        Validate.notNull(formatter, "formatter cannot be null");

        this.formatter = formatter;
        return this;
    }

    @Override
    public BooleanControllerBuilder onOffFormatter() {
        this.formatter = BooleanController.ON_OFF_FORMATTER::apply;
        return this;
    }

    @Override
    public BooleanControllerBuilder yesNoFormatter() {
        this.formatter = BooleanController.YES_NO_FORMATTER::apply;
        return this;
    }

    @Override
    public BooleanControllerBuilder trueFalseFormatter() {
        this.formatter = BooleanController.TRUE_FALSE_FORMATTER::apply;
        return this;
    }

    @Override
    public Controller<Boolean> build() {
        return BooleanController.createInternal(option, formatter, coloured);
    }
}

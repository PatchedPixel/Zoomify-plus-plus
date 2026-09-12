package id.patchedpixel.zoomify.config.lib.impl.controller;

import id.patchedpixel.zoomify.config.lib.api.Controller;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.EnumDropdownControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.ValueFormatter;
import id.patchedpixel.zoomify.config.lib.gui.controllers.cycling.EnumController;
import id.patchedpixel.zoomify.config.lib.gui.controllers.dropdown.EnumDropdownController;

public class EnumDropdownControllerBuilderImpl<E extends Enum<E>> extends AbstractControllerBuilderImpl<E> implements EnumDropdownControllerBuilder<E> {
    private ValueFormatter<E> formatter = EnumController.<E>getDefaultFormatter()::apply;

    public EnumDropdownControllerBuilderImpl(Option<E> option) {
        super(option);
    }

    @Override
    public EnumDropdownControllerBuilder<E> formatValue(ValueFormatter<E> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<E> build() {
        return new EnumDropdownController<>(option, formatter);
    }
}

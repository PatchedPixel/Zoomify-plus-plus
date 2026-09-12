package id.patchedpixel.zoomify.config.lib.impl.controller;

import com.google.common.collect.ImmutableList;
import id.patchedpixel.zoomify.config.lib.api.Controller;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.CyclingListControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.ValueFormatter;
import id.patchedpixel.zoomify.config.lib.gui.controllers.cycling.CyclingListController;

public final class CyclingListControllerBuilderImpl<T> extends AbstractControllerBuilderImpl<T> implements CyclingListControllerBuilder<T> {
    private Iterable<? extends T> values;
    private ValueFormatter<T> formatter = null;

    public CyclingListControllerBuilderImpl(Option<T> option) {
        super(option);
    }

    @Override
    public CyclingListControllerBuilder<T> values(Iterable<? extends T> values) {
        this.values = values;
        return this;
    }

    @SafeVarargs
    @Override
    public final CyclingListControllerBuilder<T> values(T... values) {
        this.values = ImmutableList.copyOf(values);
        return this;
    }

    @Override
    public CyclingListControllerBuilder<T> formatValue(ValueFormatter<T> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public Controller<T> build() {
        return CyclingListController.createInternal(option, values, formatter);
    }
}

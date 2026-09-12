package id.patchedpixel.zoomify.config.lib.api.controller;

import net.minecraft.network.chat.Component;

import java.util.function.Function;

public interface ValueFormattableController<T, B extends ValueFormattableController<T, B>> extends ControllerBuilder<T> {
    B formatValue(ValueFormatter<T> formatter);

    @Deprecated
    default B valueFormatter(Function<T, Component> formatter) {
        return formatValue(formatter::apply);
    }
}

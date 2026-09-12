package id.patchedpixel.zoomify.config.lib.config.v2.api.autogen;

import id.patchedpixel.zoomify.config.lib.api.controller.ValueFormatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows you to specify a custom {@link ValueFormatter} for a field.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CustomFormat {
    Class<? extends ValueFormatter<?>> value();
}

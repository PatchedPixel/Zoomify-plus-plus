package id.patchedpixel.zoomify.config.lib.config.v2.api.autogen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An option factory.
 * <p>
 * This creates a regular option with a
 * {@link id.patchedpixel.zoomify.config.lib.api.controller.ColorControllerBuilder} controller.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColorField {
    /**
     * Whether to show/allow the alpha channel in the color field.
     */
    boolean allowAlpha() default false;
}

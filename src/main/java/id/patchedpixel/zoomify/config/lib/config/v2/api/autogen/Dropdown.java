package id.patchedpixel.zoomify.config.lib.config.v2.api.autogen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An option factory.
 * <p>
 * This creates a regular option with a
 * {@link id.patchedpixel.zoomify.config.lib.api.controller.DropdownStringControllerBuilder} controller.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Dropdown {
	String[] values();

	/**
	 * Whether to accept the empty string as a valid value if it does not
	 * already appear in {@link #values()}. If it already appears there,
	 * the value of this does not apply.
	 */
	boolean allowEmptyValue() default false;

	/**
	 * Whether to accept any string as a valid value. The list of strings
	 * supplied in {@link #values()} are only used as dropdown suggestions.
	 * Empty strings are still prohibited unless the empty string appears in
	 * {@link #values()} or {@link #allowEmptyValue()}.
	 */
	boolean allowAnyValue() default false;
}

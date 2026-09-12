package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.DropdownStringControllerBuilderImpl;

import java.util.List;

public interface DropdownStringControllerBuilder extends StringControllerBuilder {
	DropdownStringControllerBuilder values(List<String> values);
	DropdownStringControllerBuilder values(String... values);
	DropdownStringControllerBuilder allowEmptyValue(boolean allowEmptyValue);
	DropdownStringControllerBuilder allowAnyValue(boolean allowAnyValue);


	static DropdownStringControllerBuilder create(Option<String> option) {
		return new DropdownStringControllerBuilderImpl(option);
	}
}

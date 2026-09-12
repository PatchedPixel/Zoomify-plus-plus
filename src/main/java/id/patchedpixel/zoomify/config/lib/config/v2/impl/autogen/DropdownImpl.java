package id.patchedpixel.zoomify.config.lib.config.v2.impl.autogen;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.ControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.DropdownStringControllerBuilder;
import id.patchedpixel.zoomify.config.lib.config.v2.api.ConfigField;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.Dropdown;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.OptionAccess;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.SimpleOptionFactory;

public class DropdownImpl extends SimpleOptionFactory<Dropdown, String> {
	@Override
	protected ControllerBuilder<String> createController(Dropdown annotation, ConfigField<String> field, OptionAccess storage, Option<String> option) {
		return DropdownStringControllerBuilder.create(option)
				.values(annotation.values())
				.allowEmptyValue(annotation.allowEmptyValue())
				.allowAnyValue(annotation.allowAnyValue());
	}
}

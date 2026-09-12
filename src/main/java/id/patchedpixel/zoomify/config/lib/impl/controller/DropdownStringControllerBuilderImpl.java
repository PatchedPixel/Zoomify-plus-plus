package id.patchedpixel.zoomify.config.lib.impl.controller;

import id.patchedpixel.zoomify.config.lib.api.Controller;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.DropdownStringControllerBuilder;
import id.patchedpixel.zoomify.config.lib.gui.controllers.dropdown.DropdownStringController;

import java.util.Arrays;
import java.util.List;

public class DropdownStringControllerBuilderImpl extends StringControllerBuilderImpl implements DropdownStringControllerBuilder {
	private List<String> values;
	private boolean allowEmptyValue = false;
	private boolean allowAnyValue = false;

	public DropdownStringControllerBuilderImpl(Option<String> option) {
		super(option);
	}

	@Override
	public DropdownStringControllerBuilder values(List<String> values) {
		this.values = values;
		return this;
	}

	@Override
	public DropdownStringControllerBuilderImpl values(String... values) {
		this.values = Arrays.asList(values);
		return this;
	}

	@Override
	public DropdownStringControllerBuilderImpl allowEmptyValue(boolean allowEmptyValue) {
		this.allowEmptyValue = allowEmptyValue;
		return this;
	}

	@Override
	public DropdownStringControllerBuilderImpl allowAnyValue(boolean allowAnyValue) {
		this.allowAnyValue = allowAnyValue;
		return this;
	}

	@Override
	public Controller<String> build() {
		return new DropdownStringController(option, values, allowEmptyValue, allowAnyValue);
	}

}

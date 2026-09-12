package id.patchedpixel.zoomify.config.lib.gui.controllers.dropdown;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.utils.Dimension;
import id.patchedpixel.zoomify.config.lib.gui.AbstractWidget;
import id.patchedpixel.zoomify.config.lib.gui.YACLScreen;

import java.util.List;

public class DropdownStringController extends AbstractDropdownController<String> {
	public DropdownStringController(Option<String> option, List<String> allowedValues, boolean allowEmptyValue, boolean allowAnyValue) {
		super(option, allowedValues, allowEmptyValue, allowAnyValue);
	}

	@Override
	public String getString() {
		return option().pendingValue();
	}

	@Override
	public void setFromString(String value) {
		option().requestSet(getValidValue(value));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractWidget provideWidget(YACLScreen screen, Dimension<Integer> widgetDimension) {
		return new DropdownStringControllerElement(this, screen, widgetDimension);
	}
}

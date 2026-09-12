package id.patchedpixel.zoomify.config.lib.gui.controllers.dropdown;

import id.patchedpixel.zoomify.config.lib.api.utils.Dimension;
import id.patchedpixel.zoomify.config.lib.gui.YACLScreen;

import java.util.List;

public class DropdownStringControllerElement extends AbstractDropdownControllerElement<String, String> {
	private final DropdownStringController controller;

	public DropdownStringControllerElement(DropdownStringController control, YACLScreen screen, Dimension<Integer> dim) {
		super(control, screen, dim);
		this.controller = control;
	}

	@Override
	public List<String> computeMatchingValues() {
		return controller.getAllowedValues(inputField).stream()
				.filter(this::matchingValue)
				.sorted((s1, s2) -> {
					if (s1.startsWith(inputField) && !s2.startsWith(inputField)) return -1;
					if (!s1.startsWith(inputField) && s2.startsWith(inputField)) return 1;
					return s1.compareTo(s2);
				})
				.toList();
	}

	public String getString(String object) {
		return object;
	}
}

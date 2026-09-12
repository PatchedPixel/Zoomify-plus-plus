package id.patchedpixel.zoomify.config.lib.gui.controllers.dropdown;

import id.patchedpixel.zoomify.config.lib.api.utils.Dimension;
import id.patchedpixel.zoomify.config.lib.gui.YACLScreen;

import java.util.List;

public class EnumDropdownControllerElement<E extends Enum<E>> extends AbstractDropdownControllerElement<E, String> {
    private final EnumDropdownController<E> controller;

    public EnumDropdownControllerElement(EnumDropdownController<E> control, YACLScreen screen, Dimension<Integer> dim) {
        super(control, screen, dim);
        this.controller = control;
    }

    @Override
    public List<String> computeMatchingValues() {
        return controller.getValidEnumConstants(inputField).toList();
    }

    @Override
    public String getString(String object) {
        return object;
    }
}

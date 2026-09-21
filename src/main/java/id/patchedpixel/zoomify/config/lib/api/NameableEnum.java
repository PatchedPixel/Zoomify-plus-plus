package id.patchedpixel.zoomify.config.lib.api;

import net.minecraft.network.chat.Component;

/**
 * Used for the default value formatter of {@link id.patchedpixel.zoomify.config.lib.gui.controllers.cycling.EnumController} and {@link id.patchedpixel.zoomify.config.lib.gui.controllers.dropdown.EnumDropdownController}
 */
public interface NameableEnum {
    Component getDisplayName();
}

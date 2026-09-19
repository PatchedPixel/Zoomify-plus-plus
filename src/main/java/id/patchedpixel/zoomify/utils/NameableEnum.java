package id.patchedpixel.zoomify.utils;

import net.minecraft.network.chat.Component;

public interface NameableEnum extends id.patchedpixel.zoomify.config.lib.api.NameableEnum {
    Component getLocalisedName();

    @Override
    default Component getDisplayName() {
        return getLocalisedName();
    }
}

package id.patchedpixel.zoomify.utils;

import net.minecraft.network.chat.Component;

public interface NameableEnum extends dev.isxander.yacl3.api.NameableEnum {
    Component getLocalisedName();

    @Override
    default Component getDisplayName() {
        return getLocalisedName();
    }
}

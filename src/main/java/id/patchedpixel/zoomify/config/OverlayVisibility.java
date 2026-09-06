package id.patchedpixel.zoomify.config;

import id.patchedpixel.zoomify.utils.NameableEnum;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum OverlayVisibility implements NameableEnum, StringRepresentable {
    NEVER("zoomify.overlay_visibility.never"),
    HOLDING("zoomify.overlay_visibility.holding"),
    CARRYING("zoomify.overlay_visibility.carrying"),
    ALWAYS("zoomify.overlay_visibility.always");

    private final Component localisedName;

    OverlayVisibility(String localisedName) {
        this.localisedName = Component.translatable(localisedName);
    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase();
    }

    @Override
    public Component getLocalisedName() {
        return localisedName;
    }

    public static final EnumCodec<OverlayVisibility> CODEC = StringRepresentable.fromEnum(OverlayVisibility::values);
}

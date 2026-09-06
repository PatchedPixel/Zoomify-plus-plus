package id.patchedpixel.zoomify.config;

import id.patchedpixel.zoomify.utils.NameableEnum;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum ZoomKeyBehaviour implements NameableEnum, StringRepresentable {
    HOLD("zoomify.zoom_key_behaviour.hold"),
    TOGGLE("zoomify.zoom_key_behaviour.toggle");

    private final Component localisedName;

    ZoomKeyBehaviour(String localisedName) {
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

    public static final EnumCodec<ZoomKeyBehaviour> CODEC = StringRepresentable.fromEnum(ZoomKeyBehaviour::values);
}

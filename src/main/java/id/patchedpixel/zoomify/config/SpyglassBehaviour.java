package id.patchedpixel.zoomify.config;

import id.patchedpixel.zoomify.utils.NameableEnum;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum SpyglassBehaviour implements NameableEnum, StringRepresentable {
    COMBINE("zoomify.spyglass_behaviour.combine"),
    OVERRIDE("zoomify.spyglass_behaviour.override"),
    ONLY_ZOOM_WHILE_HOLDING("zoomify.spyglass_behaviour.only_zoom_while_holding"),
    ONLY_ZOOM_WHILE_CARRYING("zoomify.spyglass_behaviour.only_zoom_while_carrying");

    private final Component localisedName;

    SpyglassBehaviour(String localisedName) {
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

    public static final EnumCodec<SpyglassBehaviour> CODEC = StringRepresentable.fromEnum(SpyglassBehaviour::values);
}

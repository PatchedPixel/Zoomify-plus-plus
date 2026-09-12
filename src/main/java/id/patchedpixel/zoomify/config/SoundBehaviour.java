package id.patchedpixel.zoomify.config;

import id.patchedpixel.zoomify.utils.NameableEnum;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum SoundBehaviour implements NameableEnum, StringRepresentable {
    NEVER("zoomify.sound_behaviour.never"),
    ALWAYS("zoomify.sound_behaviour.always"),
    ONLY_SPYGLASS("zoomify.sound_behaviour.only_spyglass"),
    WITH_OVERLAY("zoomify.sound_behaviour.with_overlay");

    private final Component localisedName;

    SoundBehaviour(String localisedName) {
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

    public static final EnumCodec<SoundBehaviour> CODEC = StringRepresentable.fromEnum(SoundBehaviour::values);
}

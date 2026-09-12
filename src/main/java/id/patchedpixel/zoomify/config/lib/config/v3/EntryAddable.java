package id.patchedpixel.zoomify.config.lib.config.v3;

import com.mojang.serialization.Codec;
import id.patchedpixel.zoomify.config.lib.config.v3.ReadonlyConfigEntry;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface EntryAddable {
    <T> ConfigEntry<T> register(String fieldName, T defaultValue, Codec<T> codec);

    <T extends CodecConfig<T>> ReadonlyConfigEntry<T> register(String fieldName, T configInstance);
}

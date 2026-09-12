package id.patchedpixel.zoomify.config.lib.api.controller;

import net.minecraft.network.chat.Component;

public interface ValueFormatter<T> {
    Component format(T value);
}

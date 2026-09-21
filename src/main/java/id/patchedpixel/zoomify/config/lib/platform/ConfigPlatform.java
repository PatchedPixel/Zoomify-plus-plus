package id.patchedpixel.zoomify.config.lib.platform;

import net.minecraft.resources.ResourceLocation;

public final class ConfigPlatform {
    public static ResourceLocation mcRl(String path) {
        return rl("minecraft", path);
    }

    public static ResourceLocation rl(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }

    private ConfigPlatform() {
    }
}

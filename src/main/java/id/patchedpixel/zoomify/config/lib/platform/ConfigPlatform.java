package id.patchedpixel.zoomify.config.lib.platform;

import net.minecraft.resources.Identifier;

public final class ConfigPlatform {
    public static Identifier mcRl(String path) {
        return rl("minecraft", path);
    }

    public static Identifier rl(String namespace, String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }

    private ConfigPlatform() {
    }
}

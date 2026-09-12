package id.patchedpixel.zoomify.config.lib.debug;

import id.patchedpixel.zoomify.config.lib.platform.YACLPlatform;

public final class DebugProperties {
    /** Applies GL filtering to rendering images. */
    public static final boolean IMAGE_FILTERING = boolProp("imageFiltering", false, false);

    private static boolean boolProp(String name, boolean defProd, boolean defDebug) {
        boolean defaultValue = YACLPlatform.isDevelopmentEnv() ? defDebug : defProd;
        return Boolean.parseBoolean(System.getProperty("yacl3.debug." + name, Boolean.toString(defaultValue)));
    }
}

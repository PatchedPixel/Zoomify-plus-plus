package id.patchedpixel.zoomify.config.lib.debug;

public final class DebugProperties {
    /** Applies GL filtering to rendering images. */
    public static final boolean IMAGE_FILTERING = boolProp("imageFiltering", false);

    private static boolean boolProp(String name, boolean defaultValue) {
        return Boolean.parseBoolean(System.getProperty("yacl3.debug." + name, Boolean.toString(defaultValue)));
    }

    private DebugProperties() {
    }
}

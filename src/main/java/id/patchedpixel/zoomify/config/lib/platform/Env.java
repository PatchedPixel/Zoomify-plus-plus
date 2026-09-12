package id.patchedpixel.zoomify.config.lib.platform;

public enum Env {
    CLIENT,
    SERVER;

    public boolean isClient() {
        return this == Env.CLIENT;
    }
}

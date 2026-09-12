package id.patchedpixel.zoomify.config.lib.config.v2.impl.autogen;

import id.patchedpixel.zoomify.config.lib.config.v2.api.ConfigField;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.OptionAccess;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.CustomImage;
import id.patchedpixel.zoomify.config.lib.gui.image.ImageRenderer;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class EmptyCustomImageFactory implements CustomImage.CustomImageFactory<Object> {

    @Override
    public CompletableFuture<ImageRenderer> createImage(Object value, ConfigField<Object> field, OptionAccess access) {
        throw new IllegalStateException();
    }
}

package id.patchedpixel.zoomify.config.lib.impl;

import id.patchedpixel.zoomify.config.lib.api.Binding;

public interface ProvidesBindingForDeprecation<T> {
    Binding<T> getBinding();
}

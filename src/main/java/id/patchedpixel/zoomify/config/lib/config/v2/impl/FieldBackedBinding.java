package id.patchedpixel.zoomify.config.lib.config.v2.impl;

import id.patchedpixel.zoomify.config.lib.api.Binding;
import id.patchedpixel.zoomify.config.lib.config.v2.api.FieldAccess;
import id.patchedpixel.zoomify.config.lib.config.v2.api.ReadOnlyFieldAccess;

public record FieldBackedBinding<T>(FieldAccess<T> field, ReadOnlyFieldAccess<T> defaultField) implements Binding<T> {
    @Override
    public T getValue() {
        return field.get();
    }

    @Override
    public void setValue(T value) {
        field.set(value);
    }

    @Override
    public T defaultValue() {
        return defaultField.get();
    }
}

package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Controller;
import org.jetbrains.annotations.ApiStatus;

@FunctionalInterface
public interface ControllerBuilder<T> {
    @ApiStatus.Internal
    Controller<T> build();
}

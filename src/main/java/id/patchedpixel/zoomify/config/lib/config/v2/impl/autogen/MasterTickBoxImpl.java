package id.patchedpixel.zoomify.config.lib.config.v2.impl.autogen;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.ControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.TickBoxControllerBuilder;
import id.patchedpixel.zoomify.config.lib.config.v2.api.ConfigField;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.SimpleOptionFactory;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.MasterTickBox;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.OptionAccess;

public class MasterTickBoxImpl extends SimpleOptionFactory<MasterTickBox, Boolean> {
    @Override
    protected ControllerBuilder<Boolean> createController(MasterTickBox annotation, ConfigField<Boolean> field, OptionAccess storage, Option<Boolean> option) {
        return TickBoxControllerBuilder.create(option);
    }

    @Override
    protected void listener(MasterTickBox annotation, ConfigField<Boolean> field, OptionAccess storage, Option<Boolean> option, Boolean value) {
        for (String child : annotation.value()) {
            storage.scheduleOptionOperation(child, childOpt -> {
                childOpt.setAvailable(annotation.invert() != value);
            });
        }
    }
}

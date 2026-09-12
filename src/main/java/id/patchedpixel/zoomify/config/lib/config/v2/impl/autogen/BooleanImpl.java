package id.patchedpixel.zoomify.config.lib.config.v2.impl.autogen;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.BooleanControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.ControllerBuilder;
import id.patchedpixel.zoomify.config.lib.config.v2.api.ConfigField;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.SimpleOptionFactory;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.Boolean;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.OptionAccess;
import net.minecraft.network.chat.Component;

public class BooleanImpl extends SimpleOptionFactory<Boolean, java.lang.Boolean> {
    @Override
    protected ControllerBuilder<java.lang.Boolean> createController(Boolean annotation, ConfigField<java.lang.Boolean> field, OptionAccess storage, Option<java.lang.Boolean> option) {
        var builder = BooleanControllerBuilder.create(option)
                .coloured(annotation.colored());
        switch (annotation.formatter()) {
            case ON_OFF -> builder.onOffFormatter();
            case YES_NO -> builder.yesNoFormatter();
            case TRUE_FALSE -> builder.trueFalseFormatter();
            case CUSTOM -> builder.formatValue(v -> Component.translatable(getTranslationKey(field, "fmt." + v)));
        }
        return builder;
    }
}

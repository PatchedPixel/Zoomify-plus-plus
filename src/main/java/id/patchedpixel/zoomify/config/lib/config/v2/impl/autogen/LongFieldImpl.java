package id.patchedpixel.zoomify.config.lib.config.v2.impl.autogen;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.ControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.LongFieldControllerBuilder;
import id.patchedpixel.zoomify.config.lib.config.v2.api.ConfigField;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.LongField;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.OptionAccess;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.SimpleOptionFactory;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;

public class LongFieldImpl extends SimpleOptionFactory<LongField, Long> {
    @Override
    protected ControllerBuilder<Long> createController(LongField annotation, ConfigField<Long> field, OptionAccess storage, Option<Long> option) {
        return LongFieldControllerBuilder.create(option)
                .formatValue(v -> {
                    String key = getTranslationKey(field, "fmt." + v);
                    if (Language.getInstance().has(key))
                        return Component.translatable(key);
                    key = getTranslationKey(field, "fmt");
                    if (Language.getInstance().has(key))
                        return Component.translatable(key, v);
                    return Component.literal(Long.toString(v));
                })
                .range(annotation.min(), annotation.max());
    }
}

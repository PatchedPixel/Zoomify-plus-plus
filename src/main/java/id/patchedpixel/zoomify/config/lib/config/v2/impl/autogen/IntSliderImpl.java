package id.patchedpixel.zoomify.config.lib.config.v2.impl.autogen;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.ControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.IntegerSliderControllerBuilder;
import id.patchedpixel.zoomify.config.lib.config.v2.api.ConfigField;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.SimpleOptionFactory;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.IntSlider;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.OptionAccess;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;

public class IntSliderImpl extends SimpleOptionFactory<IntSlider, Integer> {
    @Override
    protected ControllerBuilder<Integer> createController(IntSlider annotation, ConfigField<Integer> field, OptionAccess storage, Option<Integer> option) {
        return IntegerSliderControllerBuilder.create(option)
                .formatValue(v -> {
                    String key = getTranslationKey(field, "fmt." + v);
                    if (Language.getInstance().has(key))
                        return Component.translatable(key);
                    key = getTranslationKey(field, "fmt");
                    if (Language.getInstance().has(key))
                        return Component.translatable(key, v);
                    return Component.literal(Integer.toString(v));
                })
                .range(annotation.min(), annotation.max())
                .step(annotation.step());
    }
}

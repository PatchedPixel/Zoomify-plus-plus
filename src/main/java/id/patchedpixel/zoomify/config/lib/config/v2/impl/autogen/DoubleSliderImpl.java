package id.patchedpixel.zoomify.config.lib.config.v2.impl.autogen;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.ControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.DoubleSliderControllerBuilder;
import id.patchedpixel.zoomify.config.lib.config.v2.api.ConfigField;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.SimpleOptionFactory;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.DoubleSlider;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.OptionAccess;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;

public class DoubleSliderImpl extends SimpleOptionFactory<DoubleSlider, Double> {
    @Override
    protected ControllerBuilder<Double> createController(DoubleSlider annotation, ConfigField<Double> field, OptionAccess storage, Option<Double> option) {
        return DoubleSliderControllerBuilder.create(option)
                .formatValue(v -> {
                    String key = null;
                    if (v == annotation.min())
                        key = getTranslationKey(field, "fmt.min");
                    else if (v == annotation.max())
                        key = getTranslationKey(field, "fmt.max");
                    if (key != null && Language.getInstance().has(key))
                        return Component.translatable(key);
                    key = getTranslationKey(field, "fmt");
                    if (Language.getInstance().has(key))
                        return Component.translatable(key, v);
                    return Component.translatable(String.format(annotation.format(), v));
                })
                .range(annotation.min(), annotation.max())
                .step(annotation.step());
    }
}

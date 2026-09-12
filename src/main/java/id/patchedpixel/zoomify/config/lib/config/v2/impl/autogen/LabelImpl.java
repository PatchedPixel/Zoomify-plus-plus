package id.patchedpixel.zoomify.config.lib.config.v2.impl.autogen;

import id.patchedpixel.zoomify.config.lib.api.LabelOption;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.config.v2.api.ConfigField;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.OptionFactory;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.Label;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.OptionAccess;
import net.minecraft.network.chat.Component;

public class LabelImpl implements OptionFactory<Label, Component> {
    @Override
    public Option<Component> createOption(Label annotation, ConfigField<Component> field, OptionAccess optionAccess) {
        return LabelOption.create(field.access().get());
    }
}

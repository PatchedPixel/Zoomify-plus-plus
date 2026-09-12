package id.patchedpixel.zoomify.config.lib.config.v2.impl.autogen;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.ControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.ItemControllerBuilder;
import id.patchedpixel.zoomify.config.lib.config.v2.api.ConfigField;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.ItemField;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.OptionAccess;
import id.patchedpixel.zoomify.config.lib.config.v2.api.autogen.SimpleOptionFactory;
import net.minecraft.world.item.Item;

public class ItemFieldImpl extends SimpleOptionFactory<ItemField, Item> {
	@Override
	protected ControllerBuilder<Item> createController(ItemField annotation, ConfigField<Item> field, OptionAccess storage, Option<Item> option) {
		return ItemControllerBuilder.create(option);
	}
}

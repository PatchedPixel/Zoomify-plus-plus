package id.patchedpixel.zoomify.config.lib.api.controller;

import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.impl.controller.ItemControllerBuilderImpl;
import net.minecraft.world.item.Item;

public interface ItemControllerBuilder extends ControllerBuilder<Item> {
	static ItemControllerBuilder create(Option<Item> option) {
		return new ItemControllerBuilderImpl(option);
	}
}

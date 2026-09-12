package id.patchedpixel.zoomify.config.lib.impl.controller;

import id.patchedpixel.zoomify.config.lib.api.Controller;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.controller.ItemControllerBuilder;
import id.patchedpixel.zoomify.config.lib.gui.controllers.dropdown.ItemController;
import net.minecraft.world.item.Item;

public class ItemControllerBuilderImpl extends AbstractControllerBuilderImpl<Item> implements ItemControllerBuilder {
	public ItemControllerBuilderImpl(Option<Item> option) {
		super(option);
	}

	@Override
	public Controller<Item> build() {
		return new ItemController(option);
	}
}

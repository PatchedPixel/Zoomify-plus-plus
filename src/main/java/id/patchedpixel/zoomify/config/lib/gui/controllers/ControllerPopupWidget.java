package id.patchedpixel.zoomify.config.lib.gui.controllers;

import id.patchedpixel.zoomify.config.lib.api.Controller;
import id.patchedpixel.zoomify.config.lib.api.utils.Dimension;
import id.patchedpixel.zoomify.config.lib.gui.ConfigScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public abstract class ControllerPopupWidget<T extends Controller<?>> extends ControllerWidget<Controller<?>> implements GuiEventListener {
    public final ControllerWidget<?> entryWidget;
    public ControllerPopupWidget(T control, ConfigScreen screen, Dimension<Integer> dim, ControllerWidget<?> entryWidget) {
        super(control, screen, dim);
        this.entryWidget = entryWidget;
    }

    public ControllerWidget<?> entryWidget() {
        return entryWidget;
    }

    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {}

    @Override
    public boolean keyPressed(@NonNull KeyEvent event) {
        return entryWidget.keyPressed(event);
    }

    public void close() {}

    public Component popupTitle() {
        return Component.translatable("yacl.control.text.blank");
    }

    @Override
    protected int getHoveredControlWidth() {
        return 0;
    }

}

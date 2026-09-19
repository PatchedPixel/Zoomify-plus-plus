package id.patchedpixel.zoomify.config.lib.gui.controllers;

import id.patchedpixel.zoomify.config.lib.gui.ConfigScreen;
import id.patchedpixel.zoomify.config.lib.gui.utils.GuiUtils;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;

import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import org.jspecify.annotations.NonNull;

public class PopupControllerScreen extends Screen {
    private final ConfigScreen backgroundConfigScreen;
    private final ControllerPopupWidget<?> controllerPopup;
    public PopupControllerScreen(ConfigScreen backgroundConfigScreen, ControllerPopupWidget<?> controllerPopup) {
        super(controllerPopup.popupTitle()); //Gets narrated by the narrator
        this.backgroundConfigScreen = backgroundConfigScreen;
        this.controllerPopup = controllerPopup;
    }


    @Override
    protected void init() {
        this.addRenderableWidget(this.controllerPopup);
    }

    @Override
    protected void repositionElements() {
        super.repositionElements();
        this.onClose();
    }

    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        controllerPopup.extractBackground(graphics, mouseX, mouseY, a);
        this.backgroundConfigScreen.extractRenderState(graphics, -1, -1, a); // mouseX/Y set to -1 to prevent hovering outlines

        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    @Override
    public void extractBackground(
            GuiGraphicsExtractor guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        this.backgroundConfigScreen.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
    }


    @Override
    public boolean mouseClicked(@NonNull MouseButtonEvent event, boolean doubleClick) {
        if (!super.mouseClicked(event, doubleClick)) {
            this.onClose();
            return false;
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontal, double vertical) {
        if (controllerPopup.mouseScrolled(mouseX, mouseY, horizontal, vertical)) {
            return true;
        }
        backgroundConfigScreen.mouseScrolled(mouseX, mouseY, horizontal, vertical); //mouseX & mouseY are needed here
        return super.mouseScrolled(mouseX, mouseY, horizontal, vertical);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        controllerPopup.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean charTyped(@NonNull CharacterEvent characterEvent) {
        return controllerPopup.charTyped(characterEvent);
    }

    @Override
    public boolean keyPressed(@NonNull KeyEvent keyEvent) {
        return controllerPopup.keyPressed(keyEvent);
    }

    @Override
    public void tick() {
        super.tick();
        this.backgroundConfigScreen.tick();
    }

    @Override
    public void onClose() {
        GuiUtils.setScreen(backgroundConfigScreen, true);
        this.controllerPopup.close();
    }

}

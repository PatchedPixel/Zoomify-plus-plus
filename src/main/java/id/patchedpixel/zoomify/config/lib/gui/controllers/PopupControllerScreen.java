package id.patchedpixel.zoomify.config.lib.gui.controllers;

import id.patchedpixel.zoomify.config.lib.gui.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

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
    public void resize(Minecraft minecraft, int width, int height) {
        minecraft.setScreen(backgroundConfigScreen);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        controllerPopup.renderBackground(graphics, mouseX, mouseY, delta);
        this.backgroundConfigScreen.render(graphics, -1, -1, delta); //mouseX/Y set to -1 to prevent hovering outlines

        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(
            GuiGraphics guiGraphics
    ) {

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollY) {
        if (controllerPopup.mouseScrolled(mouseX, mouseY, scrollY)) {
            return true;
        }
        backgroundConfigScreen.mouseScrolled(mouseX, mouseY, scrollY); //mouseX & mouseY are needed here
        return super.mouseScrolled(mouseX, mouseY, scrollY);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        controllerPopup.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return controllerPopup.charTyped(codePoint, modifiers);
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return controllerPopup.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        this.minecraft.screen = backgroundConfigScreen;
    }

}

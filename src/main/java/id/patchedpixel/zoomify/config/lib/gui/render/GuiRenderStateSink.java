package id.patchedpixel.zoomify.config.lib.gui.render;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.client.gui.navigation.ScreenRectangle;

public interface GuiRenderStateSink {
    void config$submit(GuiElementRenderState renderState);

    static void submit(GuiGraphicsExtractor graphics, GuiElementRenderState renderState) {
        ((GuiRenderStateSink) graphics).config$submit(renderState);
    }

    ScreenRectangle config$peekScissorStack();

    static ScreenRectangle peekScissorStack(GuiGraphicsExtractor graphics) {
        return ((GuiRenderStateSink) graphics).config$peekScissorStack();
    }
}

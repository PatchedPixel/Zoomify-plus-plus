package id.patchedpixel.zoomify.mixins;

import id.patchedpixel.zoomify.config.lib.gui.render.GuiRenderStateSink;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiGraphicsExtractor.class)
public class GuiGraphicsExtractorMixin implements GuiRenderStateSink {
    @Shadow @Final
    public GuiRenderState guiRenderState;

    @Override
    public void config$submit(GuiElementRenderState renderState) {
        this.guiRenderState.addGuiElement(renderState);
    }

    @Shadow @Final private GuiGraphicsExtractor.ScissorStack scissorStack;

    @Override
    public ScreenRectangle config$peekScissorStack() {
        return this.scissorStack.peek();
    }
}

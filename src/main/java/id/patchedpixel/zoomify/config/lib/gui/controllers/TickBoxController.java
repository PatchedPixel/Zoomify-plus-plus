package id.patchedpixel.zoomify.config.lib.gui.controllers;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.cursor.CursorTypes;
import id.patchedpixel.zoomify.config.lib.api.Controller;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.utils.Dimension;
import id.patchedpixel.zoomify.config.lib.gui.AbstractWidget;
import id.patchedpixel.zoomify.config.lib.gui.ConfigScreen;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

/**
 * This controller renders a tickbox
 */
public record TickBoxController(Option<Boolean> option) implements Controller<Boolean> {
    /**
     * Constructs a tickbox controller
     *
     * @param option bound option
     */
    public TickBoxController {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<Boolean> option() {
        return option;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component formatValue() {
        return Component.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractWidget provideWidget(ConfigScreen screen, Dimension<Integer> widgetDimension) {
        return new TickBoxControllerElement(this, screen, widgetDimension);
    }

    public static class TickBoxControllerElement extends ControllerWidget<TickBoxController> {
        public TickBoxControllerElement(TickBoxController control, ConfigScreen screen, Dimension<Integer> dim) {
            super(control, screen, dim);
        }

        @Override
        protected void extractValueText(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
            int outlineSize = 10;
            int outlineX1 = getDimension().xLimit() - getXPadding() - outlineSize;
            int outlineY1 = getDimension().centerY() - outlineSize / 2;
            int outlineX2 = getDimension().xLimit() - getXPadding();
            int outlineY2 = getDimension().centerY() + outlineSize / 2;

            int color = getValueColor();
            int shadowColor = multiplyColor(color, 0.25f);

            graphics.outline(outlineX1 + 1, outlineY1 + 1, outlineX2 + 1 - (outlineX1 + 1), outlineY2 + 1 - (outlineY1 + 1), shadowColor);
            graphics.outline(outlineX1, outlineY1, outlineX2 - outlineX1, outlineY2 - outlineY1, color);
            if (control.option().pendingValue()) {
                graphics.fill(outlineX1 + 3, outlineY1 + 3, outlineX2 - 1, outlineY2 - 1, shadowColor);
                graphics.fill(outlineX1 + 2, outlineY1 + 2, outlineX2 - 2, outlineY2 - 2, color);
            }

            if (hovered) {
                graphics.requestCursor(isAvailable() ? CursorTypes.POINTING_HAND : CursorTypes.NOT_ALLOWED);
            }
        }

        @Override
        public boolean mouseClicked(@NonNull MouseButtonEvent event, boolean doubleClick) {
            if (!isMouseOver(event.x(), event.y()) || !isAvailable())
                return false;

            toggleSetting();
            return true;
        }

        @Override
        protected int getHoveredControlWidth() {
            return 10;
        }

        @Override
        protected int getUnhoveredControlWidth() {
            return 10;
        }

        public void toggleSetting() {
            control.option().requestSet(!control.option().pendingValue());
            playDownSound();
        }

        @Override
        public boolean keyPressed(@NonNull KeyEvent event) {
            if (!focused) {
                return false;
            }

            if (event.key() == InputConstants.KEY_RETURN || event.key() == InputConstants.KEY_SPACE || event.key() == InputConstants.KEY_NUMPADENTER) {
                toggleSetting();
                return true;
            }

            return false;
        }
    }
}

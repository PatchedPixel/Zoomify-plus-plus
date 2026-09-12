package id.patchedpixel.zoomify.config.lib.gui.tab;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.tabs.Tab;
import org.jetbrains.annotations.Nullable;

public interface TabExt extends Tab {
    @Nullable Tooltip getTooltip();

    default void tick() {}

    default void renderBackground(GuiGraphics graphics) {}
}

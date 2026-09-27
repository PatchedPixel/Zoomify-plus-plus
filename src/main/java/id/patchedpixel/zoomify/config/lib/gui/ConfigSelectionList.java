package id.patchedpixel.zoomify.config.lib.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import org.jspecify.annotations.NonNull;

public abstract class ConfigSelectionList<E extends ConfigSelectionList.Entry<E>> extends ContainerObjectSelectionList<E> {
    private boolean doneRefresh;

    public ConfigSelectionList(Minecraft minecraft, int width, int height, int y) {
        super(minecraft, width, height, y, 20);
    }

    @Override
    public void extractWidgetRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        // idk why but the scroll is broken initially and this fixes it.
        if (!doneRefresh) {
            this.repositionEntries();
            this.doneRefresh = true;
        }

        super.extractWidgetRenderState(graphics, mouseX, mouseY, a);
    }

    public static <T extends ConfigSelectionList<?>> WidgetAndType<T> asWidget(T list) {
        return WidgetAndType.ofWidget(list);
    }

    public static abstract class Entry<E extends Entry<E>> extends ContainerObjectSelectionList.Entry<E> {
        public Entry(ConfigSelectionList<E> parent) {
            super();
        }
    }
}

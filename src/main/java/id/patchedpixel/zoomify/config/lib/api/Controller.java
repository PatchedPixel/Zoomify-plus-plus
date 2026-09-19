package id.patchedpixel.zoomify.config.lib.api;

import id.patchedpixel.zoomify.config.lib.api.utils.Dimension;
import id.patchedpixel.zoomify.config.lib.gui.AbstractWidget;
import id.patchedpixel.zoomify.config.lib.gui.ConfigScreen;
import net.minecraft.network.chat.Component;

/**
 * Provides a widget to control the option.
 */
public interface Controller<T> {
    /**
     * Gets the dedicated {@link Option} for this controller
     */
    Option<T> option();

    /**
     * Gets the formatted value based on {@link Option#pendingValue()}
     */
    Component formatValue();

    /**
     * Provides a widget to display
     *
     * @param screen parent screen
     */
    AbstractWidget provideWidget(ConfigScreen screen, Dimension<Integer> widgetDimension);
}

package id.patchedpixel.zoomify.config.lib.api;

import id.patchedpixel.zoomify.config.lib.gui.ConfigScreen;
import id.patchedpixel.zoomify.config.lib.impl.ButtonOptionImpl;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ButtonOption extends Option<BiConsumer<ConfigScreen, ButtonOption>> {
    /**
     * Action to be executed upon button press
     */
    BiConsumer<ConfigScreen, ButtonOption> action();

    static Builder createBuilder() {
        return new ButtonOptionImpl.BuilderImpl();
    }

    interface Builder {
        /**
         * Sets the name to be used by the option.
         *
         * @see Option#name()
         */
        Builder name(@NotNull Component name);

        /**
         * Sets the button text to be displayed next to the name.
         */
        Builder text(@NotNull Component text);

        Builder description(@NotNull OptionDescription description);

        Builder action(@NotNull BiConsumer<ConfigScreen, ButtonOption> action);

        /**
         * Action to be executed upon button press
         *
         * @see ButtonOption#action()
         */
        @Deprecated
        Builder action(@NotNull Consumer<ConfigScreen> action);

        /**
         * Sets if the option can be configured
         *
         * @see Option#available()
         */
        Builder available(boolean available);

        ButtonOption build();
    }
}

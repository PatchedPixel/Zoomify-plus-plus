package id.patchedpixel.zoomify.config.lib.api;

import com.google.common.collect.ImmutableList;
import id.patchedpixel.zoomify.config.lib.gui.ConfigScreen;
import id.patchedpixel.zoomify.config.lib.impl.ConfigLibImpl;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Main class of the mod.
 * Contains all data and used to provide a {@link Screen}
 */
public interface ConfigLib {
    /**
     * Title of the GUI. Only used for Minecraft narration.
     */
    Component title();

    /**
     * Gets all config categories.
     */
    ImmutableList<ConfigCategory> categories();

    /**
     * Ran when changes are saved. Can be used to save config to a file etc.
     */
    Runnable saveFunction();

    /**
     * Ran every time the YACL screen initialises. Can be paired with FAPI to add custom widgets.
     */
    Consumer<ConfigScreen> initConsumer();

    /**
     * Generates a Screen to display based on this instance.
     *
     * @param parent parent screen to open once closed
     */
    Screen generateScreen(@Nullable Screen parent);

    /**
     * Creates a builder to construct YACL
     */
    static Builder createBuilder() {
        return new ConfigLibImpl.BuilderImpl();
    }

    interface Builder {
        /**
         * Sets title of GUI for Minecraft narration
         *
         * @see ConfigLib#title()
         */
        Builder title(@NotNull Component title);

        /**
         * Adds a new category.
         * To create a category you need to use {@link ConfigCategory#createBuilder()}
         *
         * @see ConfigLib#categories()
         */
        Builder category(@NotNull ConfigCategory category);

        /**
         * Adds multiple categories at once.
         * To create a category you need to use {@link ConfigCategory#createBuilder()}
         *
         * @see ConfigLib#categories()
         */
        Builder categories(@NotNull Collection<? extends ConfigCategory> categories);

        /**
         * Used to define a save function for when user clicks the Save Changes button
         *
         * @see ConfigLib#saveFunction()
         */
        Builder save(@NotNull Runnable saveFunction);

        /**
         * Defines a consumer that is accepted every time the YACL screen initialises
         *
         * @see ConfigLib#initConsumer()
         */
        Builder screenInit(@NotNull Consumer<ConfigScreen> initConsumer);

        ConfigLib build();
    }
}

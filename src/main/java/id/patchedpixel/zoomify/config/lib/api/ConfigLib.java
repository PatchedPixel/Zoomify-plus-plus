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
import java.util.function.Supplier;

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
         * To create a category you need to use {@link ConfigCategory#createBuilder()}.
         *
         * @see ConfigLib#categories()
         */
        Builder category(@NotNull ConfigCategory category);

        /**
         * Adds a new category.
         * To create a category you need to use {@link ConfigCategory#createBuilder()}.
         *
         * @param categorySupplier to be called to initialise the category. Called immediately.
         * @see ConfigLib#categories()
         */
        default Builder category(@NotNull Supplier<@NotNull ConfigCategory> categorySupplier) {
            return category(categorySupplier.get());
        }

        /**
         * Adds a new category if a condition is met.
         * To create a category you need to use {@link ConfigCategory#createBuilder()}.
         *
         * @param condition whether to add the category
         * @see ConfigLib#categories()
         */
        default Builder categoryIf(boolean condition, @NotNull ConfigCategory category) {
            return condition ? category(category) : this;
        }

        /**
         * Adds a new category if a condition is met.
         * To create a category you need to use {@link ConfigCategory#createBuilder()}.
         *
         * @param condition        whether to add the category
         * @param categorySupplier to be called to initialise the category. Called immediately if and only if condition is true.
         * @see ConfigLib#categories()
         */
        default Builder categoryIf(boolean condition, @NotNull Supplier<@NotNull ConfigCategory> categorySupplier) {
            return condition ? category(categorySupplier) : this;
        }

        /**
         * Adds multiple categories at once.
         * To create a category you need to use {@link ConfigCategory#createBuilder()}.
         *
         * @see ConfigLib#categories()
         */
        Builder categories(@NotNull Collection<? extends @NotNull ConfigCategory> categories);

        /**
         * Adds multiple categories at once.
         * To create a category you need to use {@link ConfigCategory#createBuilder()}.
         *
         * @param categoriesSupplier to be called to initialise the categories. Called immediately.
         * @see ConfigLib#categories()
         */
        default Builder categories(@NotNull Supplier<@NotNull Collection<? extends @NotNull ConfigCategory>> categoriesSupplier) {
            return categories(categoriesSupplier.get());
        }

        /**
         * Adds multiple categories at once if a condition is met.
         * To create a category you need to use {@link ConfigCategory#createBuilder()}.
         *
         * @param condition whether to add the categories
         * @see ConfigLib#categories()
         */
        default Builder categoriesIf(boolean condition, @NotNull Collection<? extends @NotNull ConfigCategory> categories) {
            return condition ? categories(categories) : this;
        }

        /**
         * Adds multiple categories at once if a condition is met.
         * To create a category you need to use {@link ConfigCategory#createBuilder()}.
         *
         * @param condition          whether to add the categories
         * @param categoriesSupplier to be called to initialise the categories. Called immediately if and only if condition is true.
         * @see ConfigLib#categories()
         */
        default Builder categoriesIf(boolean condition, @NotNull Supplier<@NotNull Collection<? extends @NotNull ConfigCategory>> categoriesSupplier) {
            return condition ? categories(categoriesSupplier) : this;
        }

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

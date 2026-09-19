package id.patchedpixel.zoomify.config.lib.impl;

import com.google.common.collect.ImmutableList;
import id.patchedpixel.zoomify.config.lib.api.ConfigCategory;
import id.patchedpixel.zoomify.config.lib.api.PlaceholderCategory;
import id.patchedpixel.zoomify.config.lib.api.ConfigLib;
import id.patchedpixel.zoomify.config.lib.gui.ConfigScreen;
import id.patchedpixel.zoomify.config.lib.impl.utils.ConfigConstants;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@ApiStatus.Internal
public final class ConfigLibImpl implements ConfigLib {
    private final Component title;
    private final ImmutableList<ConfigCategory> categories;
    private final Runnable saveFunction;
    private final Consumer<ConfigScreen> initConsumer;

    private boolean generated = false;

    public ConfigLibImpl(Component title, ImmutableList<ConfigCategory> categories, Runnable saveFunction, Consumer<ConfigScreen> initConsumer) {
        this.title = title;
        this.categories = categories;
        this.saveFunction = saveFunction;
        this.initConsumer = initConsumer;
    }

    @Override
    public Screen generateScreen(Screen parent) {
        if (generated)
            throw new UnsupportedOperationException("To prevent memory leaks, you should only generate a Screen once per instance. Please re-build the instance to generate another GUI.");

        ConfigConstants.LOGGER.info("Generating config screen");
        generated = true;
        return new ConfigScreen(this, parent);
    }

    @Override
    public Component title() {
        return title;
    }

    @Override
    public ImmutableList<ConfigCategory> categories() {
        return categories;
    }

    @Override
    public Runnable saveFunction() {
        return saveFunction;
    }

    @Override
    public Consumer<ConfigScreen> initConsumer() {
        return initConsumer;
    }

    @ApiStatus.Internal
    public static final class BuilderImpl implements Builder {
        private Component title;
        private final List<ConfigCategory> categories = new ArrayList<>();
        private Runnable saveFunction = () -> {};
        private Consumer<ConfigScreen> initConsumer = screen -> {};

        @Override
        public Builder title(@NotNull Component title) {
            Validate.notNull(title, "`title` cannot be null");

            this.title = title;
            return this;
        }

        @Override
        public Builder category(@NotNull ConfigCategory category) {
            Validate.notNull(category, "`category` cannot be null");

            this.categories.add(category);
            return this;
        }

        @Override
        public Builder categories(@NotNull Collection<? extends @NotNull ConfigCategory> categories) {
            Validate.notNull(categories, "`categories` cannot be null");

            this.categories.addAll(categories);
            return this;
        }

        @Override
        public Builder save(@NotNull Runnable saveFunction) {
            Validate.notNull(saveFunction, "`saveFunction` cannot be null");

            this.saveFunction = saveFunction;
            return this;
        }

        @Override
        public Builder screenInit(@NotNull Consumer<ConfigScreen> initConsumer) {
            Validate.notNull(initConsumer, "`initConsumer` cannot be null");

            this.initConsumer = initConsumer;
            return this;
        }

        @Override
        public ConfigLib build() {
            Validate.notNull(title, "`title must not be null to build `ConfigLib`");
            Validate.notEmpty(categories, "`categories` must not be empty to build `ConfigLib`");
            Validate.isTrue(!categories.stream().allMatch(category -> category instanceof PlaceholderCategory), "At least one regular category is required to build `ConfigLib`");

            return new ConfigLibImpl(title, ImmutableList.copyOf(categories), saveFunction, initConsumer);
        }
    }
}

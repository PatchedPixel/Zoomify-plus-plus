package id.patchedpixel.zoomify.config.lib.api.utils;

import id.patchedpixel.zoomify.config.lib.api.*;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class OptionUtils {
    public static Stream<Option<?>> getFlatOptions(ConfigLib config) {
        return config.categories().stream()
                .flatMap(category -> category.groups().stream())
                .flatMap(group -> group instanceof ListOption<?> list
                        ? Stream.of(list)
                        : group.options().stream());
    }

    /**
     * Consumes all options, ignoring groups and categories.
     * When consumer returns true, this function stops iterating.
     */
    public static void consumeOptions(ConfigLib config, Function<Option<?>, Boolean> consumer) {
        for (ConfigCategory category : config.categories()) {
            for (OptionGroup group : category.groups()) {
                if (group instanceof ListOption<?> list) {
                    if (consumer.apply(list)) return;
                } else {
                    for (Option<?> option : group.options()) {
                        if (consumer.apply(option)) return;
                    }
                }

            }
        }
    }

    /**
     * Consumes all options, ignoring groups and categories.
     *
     * @see OptionUtils#consumeOptions(ConfigLib, Function)
     */
    public static void forEachOptions(ConfigLib config, Consumer<Option<?>> consumer) {
        consumeOptions(config, (opt) -> {
            consumer.accept(opt);
            return false;
        });
    }
}

package id.patchedpixel.zoomify.config;

import id.patchedpixel.zoomify.config.lib.api.ButtonOption;
import id.patchedpixel.zoomify.config.lib.api.ConfigCategory;
import id.patchedpixel.zoomify.config.lib.api.LabelOption;
import id.patchedpixel.zoomify.config.lib.api.Option;
import id.patchedpixel.zoomify.config.lib.api.OptionDescription;
import id.patchedpixel.zoomify.config.lib.api.OptionFlag;
import id.patchedpixel.zoomify.config.lib.api.OptionGroup;
import id.patchedpixel.zoomify.config.lib.api.YetAnotherConfigLib;
import id.patchedpixel.zoomify.config.lib.api.controller.DoubleSliderControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.EnumControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.IntegerSliderControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.controller.TickBoxControllerBuilder;
import id.patchedpixel.zoomify.config.lib.api.utils.OptionUtils;
import id.patchedpixel.zoomify.config.lib.config.v3.ConfigEntry;

import id.patchedpixel.zoomify.config.demo.ControlEmulation;
import id.patchedpixel.zoomify.config.demo.FirstPersonDemo;
import id.patchedpixel.zoomify.config.demo.ThirdPersonDemo;
import id.patchedpixel.zoomify.config.demo.ZoomDemoImageRenderer;
import id.patchedpixel.zoomify.utils.TransitionType;
import id.patchedpixel.zoomify.zoom.DefaultZoomHelpers;
import id.patchedpixel.zoomify.zoom.ZoomHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static id.patchedpixel.zoomify.utils.MinecraftExt.toast;

public final class SettingsGuiFactory {

    private SettingsGuiFactory() {
    }

    public static Screen createSettingsGui(Screen parent) {
        return new Factory().createSettingsGui(parent);
    }

    private static final class Factory {

        /*
         * Copy the current settings.
         *
         * The actual config is ZoomifySettings.Companion.
         * This copy is used by the preview/demo so changes in the GUI
         * can be previewed before being saved.
         */
        final ZoomifySettings settings =
                new ZoomifySettings(ZoomifySettings.Companion);

        final FirstPersonDemo initialOnlyDemo;
        final FirstPersonDemo scrollOnlyDemo;
        final ThirdPersonDemo secondaryZoomDemo;

        Factory() {
            initialOnlyDemo = new FirstPersonDemo(
                    DefaultZoomHelpers.regularZoomHelper(settings),
                    ControlEmulation.InitialOnly.INSTANCE
            );

            initialOnlyDemo.setKeepHandFov(
                    !settings.getAffectHandFov().get()
            );

            scrollOnlyDemo = new FirstPersonDemo(
                    DefaultZoomHelpers.regularZoomHelper(settings),
                    ControlEmulation.ScrollOnly.INSTANCE
            );

            scrollOnlyDemo.setKeepHandFov(
                    !settings.getAffectHandFov().get()
            );

            secondaryZoomDemo = new ThirdPersonDemo(
                    DefaultZoomHelpers.secondaryZoomHelper(settings),
                    ControlEmulation.InitialOnly.INSTANCE
            );

            secondaryZoomDemo.setRenderHud(
                    !settings.getSecondaryHideHUDOnZoom().get()
            );
        }

        private <T> Option.Builder<T> bind(
                ConfigEntry<T> entry,
                Component name
        ) {
            return Option.<T>createBuilder()
                    .name(name)
                    .binding(
                            entry.get(),
                            entry::get,
                            entry::set
                    );
        }

        private <T> void updateDemo(
                Option.Builder<T> builder,
                BiConsumer<T, ZoomDemoImageRenderer> updateFunc
        ) {
            builder.listener((option, value) -> {
                updateFunc.accept(value, initialOnlyDemo);
                updateFunc.accept(value, scrollOnlyDemo);
                updateFunc.accept(value, secondaryZoomDemo);

                initialOnlyDemo.pause();
                scrollOnlyDemo.pause();
                secondaryZoomDemo.pause();
            });
        }

        private <T> Option<T> demoOption(
                ConfigEntry<T> entry,
                ConfigEntry<T> demoEntry,
                ZoomDemoImageRenderer demo,
                String key,
                Consumer<Option.Builder<T>> configure
        ) {
            Option.Builder<T> builder = bind(
                    entry,
                    Component.translatable(key)
            );

            builder.description(
                    OptionDescription.createBuilder()
                            .text(
                                    Component.translatable(
                                            key + ".description.1"
                                    )
                            )
                            .customImage(demo)
                            .build()
            );

            updateDemo(
                    builder,
                    (value, ignored) -> demoEntry.set(value)
            );

            configure.accept(builder);

            return builder.build();
        }

        Screen createSettingsGui(Screen parent) {

            YetAnotherConfigLib.Builder rootBuilder =
                    YetAnotherConfigLib.createBuilder()
                            .title(
                                    Component.translatable(
                                            "yacl3.config.zoomify.title"
                                    )
                            )
                            .save(
                                    ZoomifySettings.Companion::saveToFile
                            );

            ConfigCategory.Builder behaviour =
                    ConfigCategory.createBuilder()
                            .name(
                                    Component.translatable(
                                            "yacl3.config.zoomify.category.behaviour"
                                    )
                            );

            OptionGroup.Builder basic =
                    OptionGroup.createBuilder()
                            .name(
                                    Component.translatable(
                                            "yacl3.config.zoomify.category.behaviour.group.basic"
                                    )
                            );

            basic.option(
                    demoOption(
                            ZoomifySettings.Companion.getInitialZoom(),
                            settings.getInitialZoom(),
                            initialOnlyDemo,
                            "yacl3.config.zoomify.category.behaviour.group.basic.option.initialZoom",
                            builder -> builder.controller(
                                    opt -> IntegerSliderControllerBuilder
                                            .create(opt)
                                            .range(1, 10)
                                            .step(1)
                                            .formatValue(
                                                    v -> Component.literal(
                                                            v + "x"
                                                    )
                                            )
                            )
                    )
            );

            basic.option(
                    demoOption(
                            ZoomifySettings.Companion.getZoomInTime(),
                            settings.getZoomInTime(),
                            initialOnlyDemo,
                            "yacl3.config.zoomify.category.behaviour.group.basic.option.zoomInTime",
                            builder -> builder.controller(
                                    opt -> DoubleSliderControllerBuilder
                                            .create(opt)
                                            .range(0.1, 5.0)
                                            .step(0.1)
                                            .formatValue(
                                                    SettingsGuiFactory::formatSeconds
                                            )
                            )
                    )
            );

            basic.option(
                    demoOption(
                            ZoomifySettings.Companion.getZoomOutTime(),
                            settings.getZoomOutTime(),
                            initialOnlyDemo,
                            "yacl3.config.zoomify.category.behaviour.group.basic.option.zoomOutTime",
                            builder -> builder.controller(
                                    opt -> DoubleSliderControllerBuilder
                                            .create(opt)
                                            .range(0.1, 5.0)
                                            .step(0.1)
                                            .formatValue(
                                                    SettingsGuiFactory::formatSeconds
                                            )
                            )
                    )
            );

            basic.option(
                    demoOption(
                            ZoomifySettings.Companion.getZoomInTransition(),
                            settings.getZoomInTransition(),
                            initialOnlyDemo,
                            "yacl3.config.zoomify.category.behaviour.group.basic.option.zoomInTransition",
                            builder -> builder.controller(
                                    opt -> EnumControllerBuilder
                                            .create(opt)
                                            .enumClass(TransitionType.class)
                            )
                    )
            );

            basic.option(
                    demoOption(
                            ZoomifySettings.Companion.getZoomOutTransition(),
                            settings.getZoomOutTransition(),
                            initialOnlyDemo,
                            "yacl3.config.zoomify.category.behaviour.group.basic.option.zoomOutTransition",
                            builder -> builder.controller(
                                    opt -> EnumControllerBuilder
                                            .create(opt)
                                            .enumClass(TransitionType.class)
                            )
                    )
            );

            Option.Builder<Boolean> affectHandFov =
                    bind(
                            ZoomifySettings.Companion.getAffectHandFov(),
                            Component.translatable(
                                    "yacl3.config.zoomify.category.behaviour.group.basic.option.affectHandFov"
                            )
                    )
                            .description(
                                    OptionDescription.createBuilder()
                                            .text(
                                                    Component.translatable(
                                                            "yacl3.config.zoomify.category.behaviour.group.basic.option.affectHandFov.description.1"
                                                    )
                                            )
                                            .customImage(initialOnlyDemo)
                                            .build()
                            )
                            .controller(
                                    TickBoxControllerBuilder::create
                            );

            updateDemo(
                    affectHandFov,
                    (value, demo) -> {
                        if (demo instanceof FirstPersonDemo firstPersonDemo) {
                            firstPersonDemo.setKeepHandFov(!value);
                        }
                    }
            );

            basic.option(affectHandFov.build());

            behaviour.group(basic.build());

            OptionGroup.Builder scrolling =
                    OptionGroup.createBuilder()
                            .name(
                                    Component.translatable(
                                            "yacl3.config.zoomify.category.behaviour.group.scrolling"
                                    )
                            );

            List<Option<?>> innerScrollOpts =
                    new ArrayList<>();

            innerScrollOpts.add(
                    demoOption(
                            ZoomifySettings.Companion.getScrollStepCount(),
                            settings.getScrollStepCount(),
                            scrollOnlyDemo,
                            "yacl3.config.zoomify.category.behaviour.group.scrolling.option.scrollStepCount",
                            builder -> builder.controller(
                                    opt -> IntegerSliderControllerBuilder
                                            .create(opt)
                                            .range(3, 20)
                                            .step(1)
                            )
                    )
            );

            innerScrollOpts.add(
                    demoOption(
                            ZoomifySettings.Companion.getZoomPerStep(),
                            settings.getZoomPerStep(),
                            scrollOnlyDemo,
                            "yacl3.config.zoomify.category.behaviour.group.scrolling.option.zoomPerStep",
                            builder -> builder.controller(
                                    opt -> IntegerSliderControllerBuilder
                                            .create(opt)
                                            .range(110, 200)
                                            .step(10)
                                            .formatValue(
                                                    v -> Component.literal(
                                                            String.format(
                                                                    "%.1fx",
                                                                    v / 100.0
                                                            )
                                                    )
                                            )
                            )
                    )
            );

            innerScrollOpts.add(
                    demoOption(
                            ZoomifySettings.Companion.getScrollZoomSmoothness(),
                            settings.getScrollZoomSmoothness(),
                            scrollOnlyDemo,
                            "yacl3.config.zoomify.category.behaviour.group.scrolling.option.scrollZoomSmoothness",
                            builder -> builder.controller(
                                    opt -> IntegerSliderControllerBuilder
                                            .create(opt)
                                            .range(0, 100)
                                            .step(1)
                                            .formatValue(
                                                    v -> v == 0
                                                            ? Component.translatable(
                                                            "zoomify.gui.formatter.instant"
                                                    )
                                                            : Component.literal(
                                                            v + "%"
                                                    )
                                            )
                            )
                    )
            );

            innerScrollOpts.add(
                    demoOption(
                            ZoomifySettings.Companion.getRetainZoomSteps(),
                            settings.getRetainZoomSteps(),
                            scrollOnlyDemo,
                            "yacl3.config.zoomify.category.behaviour.group.scrolling.option.retainZoomSteps",
                            builder -> builder.controller(
                                    TickBoxControllerBuilder::create
                            )
                    )
            );

            Option.Builder<Boolean> scrollZoomBuilder =
                    bind(
                            ZoomifySettings.Companion.getScrollZoom(),
                            Component.translatable(
                                    "yacl3.config.zoomify.category.behaviour.group.scrolling.option.scrollZoom"
                            )
                    )
                            .description(
                                    OptionDescription.createBuilder()
                                            .text(
                                                    Component.translatable(
                                                            "yacl3.config.zoomify.category.behaviour.group.scrolling.option.scrollZoom.description.1"
                                                    )
                                            )
                                            .customImage(scrollOnlyDemo)
                                            .build()
                            )
                            .controller(
                                    TickBoxControllerBuilder::create
                            );

            updateDemo(
                    scrollZoomBuilder,
                    (value, ignored) -> {
                        for (Option<?> option : innerScrollOpts) {
                            option.setAvailable(value);
                        }
                    }
            );

            scrolling.option(scrollZoomBuilder.build());

            for (Option<?> option : innerScrollOpts) {
                scrolling.option(option);
            }

            boolean scrollEnabled =
                    ZoomifySettings.Companion
                            .getScrollZoom()
                            .get();

            for (Option<?> option : innerScrollOpts) {
                option.setAvailable(scrollEnabled);
            }

            behaviour.group(scrolling.build());

            OptionGroup.Builder spyglass =
                    OptionGroup.createBuilder()
                            .name(
                                    Component.translatable(
                                            "yacl3.config.zoomify.category.behaviour.group.spyglass"
                                    )
                            );

            spyglass.option(
                    bind(
                            ZoomifySettings.Companion.getSpyglassBehaviour(),
                            Component.translatable(
                                    "yacl3.config.zoomify.category.behaviour.group.spyglass.option.spyglassBehaviour"
                            )
                    )
                            .description(
                                    OptionDescription.createBuilder()
                                            .text(
                                                    Component.translatable(
                                                            "yacl3.config.zoomify.category.behaviour.group.spyglass.option.spyglassBehaviour.description.1"
                                                    )
                                            )
                                            .build()
                            )
                            .controller(
                                    opt -> EnumControllerBuilder
                                            .create(opt)
                                            .enumClass(
                                                    SpyglassBehaviour.class
                                            )
                            )
                            .build()
            );

            spyglass.option(
                    bind(
                            ZoomifySettings.Companion.getSpyglassOverlayVisibility(),
                            Component.translatable(
                                    "yacl3.config.zoomify.category.behaviour.group.spyglass.option.spyglassOverlayVisibility"
                            )
                    )
                            .description(
                                    OptionDescription.createBuilder()
                                            .text(
                                                    Component.translatable(
                                                            "yacl3.config.zoomify.category.behaviour.group.spyglass.option.spyglassOverlayVisibility.description.1"
                                                    )
                                            )
                                            .build()
                            )
                            .controller(
                                    opt -> EnumControllerBuilder
                                            .create(opt)
                                            .enumClass(
                                                    OverlayVisibility.class
                                            )
                            )
                            .build()
            );

            spyglass.option(
                    bind(
                            ZoomifySettings.Companion.getSpyglassSoundBehaviour(),
                            Component.translatable(
                                    "yacl3.config.zoomify.category.behaviour.group.spyglass.option.spyglassSoundBehaviour"
                            )
                    )
                            .description(
                                    OptionDescription.createBuilder()
                                            .text(
                                                    Component.translatable(
                                                            "yacl3.config.zoomify.category.behaviour.group.spyglass.option.spyglassSoundBehaviour.description.1"
                                                    )
                                            )
                                            .build()
                            )
                            .controller(
                                    opt -> EnumControllerBuilder
                                            .create(opt)
                                            .enumClass(
                                                    SoundBehaviour.class
                                            )
                            )
                            .build()
            );

            behaviour.group(spyglass.build());

            rootBuilder.category(behaviour.build());

            ConfigCategory.Builder controls =
                    ConfigCategory.createBuilder()
                            .name(
                                    Component.translatable(
                                            "yacl3.config.zoomify.category.controls"
                                    )
                            );

            controls.option(
                    bind(
                            ZoomifySettings.Companion.getZoomKeyBehaviour(),
                            Component.translatable(
                                    "yacl3.config.zoomify.category.controls.root.option.zoomKeyBehaviour"
                            )
                    )
                            .description(
                                    OptionDescription.createBuilder()
                                            .text(
                                                    Component.translatable(
                                                            "yacl3.config.zoomify.category.controls.root.option.zoomKeyBehaviour.description.1"
                                                    )
                                            )
                                            .build()
                            )
                            .controller(
                                    opt -> EnumControllerBuilder
                                            .create(opt)
                                            .enumClass(
                                                    ZoomKeyBehaviour.class
                                            )
                            )
                            .build()
            );

            controls.option(
                    bind(
                            ZoomifySettings.Companion.get_keybindScrolling(),
                            Component.translatable(
                                    "yacl3.config.zoomify.category.controls.root.option._keybindScrolling"
                            )
                    )
                            .description(
                                    OptionDescription.createBuilder()
                                            .text(
                                                    Component.translatable(
                                                            "yacl3.config.zoomify.category.controls.root.option._keybindScrolling.description.1"
                                                    )
                                            )
                                            .build()
                            )
                            .controller(
                                    TickBoxControllerBuilder::create
                            )
                            .flag(OptionFlag.GAME_RESTART)
                            .build()
            );

            controls.option(
                    bind(
                            ZoomifySettings.Companion.getRelativeSensitivity(),
                            Component.translatable(
                                    "yacl3.config.zoomify.category.controls.root.option.relativeSensitivity"
                            )
                    )
                            .description(
                                    OptionDescription.createBuilder()
                                            .text(
                                                    Component.translatable(
                                                            "yacl3.config.zoomify.category.controls.root.option.relativeSensitivity.description.1"
                                                    )
                                            )
                                            .build()
                            )
                            .controller(
                                    opt -> IntegerSliderControllerBuilder
                                            .create(opt)
                                            .range(0, 150)
                                            .step(10)
                                            .formatValue(
                                                    v -> v == 0
                                                            ? CommonComponents.OPTION_OFF
                                                            : Component.literal(
                                                            v + "%"
                                                    )
                                            )
                            )
                            .build()
            );

            controls.option(
                    bind(
                            ZoomifySettings.Companion.getRelativeViewBobbing(),
                            Component.translatable(
                                    "yacl3.config.zoomify.category.controls.root.option.relativeViewBobbing"
                            )
                    )
                            .description(
                                    OptionDescription.createBuilder()
                                            .text(
                                                    Component.translatable(
                                                            "yacl3.config.zoomify.category.controls.root.option.relativeViewBobbing.description.1"
                                                    )
                                            )
                                            .build()
                            )
                            .controller(
                                    TickBoxControllerBuilder::create
                            )
                            .build()
            );

            controls.option(
                    bind(
                            ZoomifySettings.Companion.getCinematicCamera(),
                            Component.translatable(
                                    "yacl3.config.zoomify.category.controls.root.option.cinematicCamera"
                            )
                    )
                            .description(
                                    OptionDescription.createBuilder()
                                            .text(
                                                    Component.translatable(
                                                            "yacl3.config.zoomify.category.controls.root.option.cinematicCamera.description.1"
                                                    )
                                            )
                                            .build()
                            )
                            .controller(
                                    opt -> IntegerSliderControllerBuilder
                                            .create(opt)
                                            .range(0, 250)
                                            .step(10)
                                            .formatValue(
                                                    v -> v == 0
                                                            ? CommonComponents.OPTION_OFF
                                                            : Component.literal(
                                                            v + "%"
                                                    )
                                            )
                            )
                            .build()
            );

            rootBuilder.category(controls.build());

            ConfigCategory.Builder secondary =
                    ConfigCategory.createBuilder()
                            .name(
                                    Component.translatable(
                                            "yacl3.config.zoomify.category.secondary"
                                    )
                            );

            secondary.option(
                    LabelOption.create(
                            Component.translatable(
                                    "yacl3.config.zoomify.category.secondary.root.label.infoLabel"
                            )
                    )
            );

            secondary.option(
                    demoOption(
                            ZoomifySettings.Companion.getSecondaryZoomAmount(),
                            settings.getSecondaryZoomAmount(),
                            secondaryZoomDemo,
                            "yacl3.config.zoomify.category.secondary.root.option.secondaryZoomAmount",
                            builder -> builder.controller(
                                    opt -> IntegerSliderControllerBuilder
                                            .create(opt)
                                            .range(2, 10)
                                            .step(1)
                                            .formatValue(
                                                    SettingsGuiFactory::formatPercent
                                            )
                            )
                    )
            );

            secondary.option(
                    demoOption(
                            ZoomifySettings.Companion.getSecondaryZoomInTime(),
                            settings.getSecondaryZoomInTime(),
                            secondaryZoomDemo,
                            "yacl3.config.zoomify.category.secondary.root.option.secondaryZoomInTime",
                            builder -> builder.controller(
                                    opt -> DoubleSliderControllerBuilder
                                            .create(opt)
                                            .range(6.0, 30.0)
                                            .step(2.0)
                                            .formatValue(
                                                    SettingsGuiFactory::formatSeconds
                                            )
                            )
                    )
            );

            secondary.option(
                    demoOption(
                            ZoomifySettings.Companion.getSecondaryZoomOutTime(),
                            settings.getSecondaryZoomOutTime(),
                            secondaryZoomDemo,
                            "yacl3.config.zoomify.category.secondary.root.option.secondaryZoomOutTime",
                            builder -> builder.controller(
                                    opt -> DoubleSliderControllerBuilder
                                            .create(opt)
                                            .range(0.0, 5.0)
                                            .step(0.25)
                                            .formatValue(
                                                    value -> value == 0.0
                                                            ? Component.translatable(
                                                            "zoomify.gui.formatter.instant"
                                                    )
                                                            : Component.translatable(
                                                            "zoomify.gui.formatter.seconds",
                                                            String.format(
                                                                    "%.2f",
                                                                    value
                                                            )
                                                    )
                                            )
                            )
                    )
            );

            Option.Builder<Boolean> hideHud =
                    bind(
                            ZoomifySettings.Companion.getSecondaryHideHUDOnZoom(),
                            Component.translatable(
                                    "yacl3.config.zoomify.category.secondary.root.option.secondaryHideHUDOnZoom"
                            )
                    )
                            .description(
                                    OptionDescription.createBuilder()
                                            .text(
                                                    Component.translatable(
                                                            "yacl3.config.zoomify.category.secondary.root.option.secondaryHideHUDOnZoom.description.1"
                                                    )
                                            )
                                            .customImage(secondaryZoomDemo)
                                            .build()
                            )
                            .controller(
                                    TickBoxControllerBuilder::create
                            );

            updateDemo(
                    hideHud,
                    (value, demo) -> {
                        if (demo instanceof ThirdPersonDemo thirdPersonDemo) {
                            thirdPersonDemo.setRenderHud(!value);
                        }
                    }
            );

            secondary.option(hideHud.build());

            rootBuilder.category(secondary.build());

            ConfigCategory.Builder misc =
                    ConfigCategory.createBuilder()
                            .name(
                                    Component.translatable(
                                            "yacl3.config.zoomify.category.misc"
                                    )
                            );

            OptionGroup.Builder presetsGroup =
                    OptionGroup.createBuilder()
                            .name(
                                    Component.translatable(
                                            "yacl3.config.zoomify.category.misc.group.presets"
                                    )
                            );

            presetsGroup.option(
                    LabelOption.create(
                            Component.translatable(
                                    "yacl3.config.zoomify.category.misc.group.presets.label.applyWarning"
                            )
                    )
            );

            String buttonKey =
                    "yacl3.config.zoomify.category.misc.group.presets.presetBtn";

            for (Presets preset : Presets.values()) {

                ZoomifySettings presetSettings =
                        new ZoomifySettings();

                preset.apply(presetSettings);

                ZoomHelper presetZoomHelper =
                        DefaultZoomHelpers.regularZoomHelper(
                                presetSettings
                        );

                FirstPersonDemo demo =
                        new FirstPersonDemo(
                                presetZoomHelper,
                                ControlEmulation.InitialOnly.INSTANCE
                        );

                presetsGroup.option(
                        ButtonOption.createBuilder()
                                .name(
                                        preset.getDisplayName()
                                )
                                .description(
                                        OptionDescription.createBuilder()
                                                .text(
                                                        Component.translatable(
                                                                buttonKey + ".description",
                                                                preset.getDisplayName()
                                                        )
                                                )
                                                .customImage(demo)
                                                .build()
                                )
                                .text(
                                        Component.translatable(
                                                buttonKey + ".button"
                                        )
                                )
                                .action((screen, option) -> {

                                    preset.apply(
                                            ZoomifySettings.Companion
                                    );

                                    toast(
                                            Component.translatable(
                                                    "zoomify.gui.preset.toast.title"
                                            ),
                                            Component.translatable(
                                                    "zoomify.gui.preset.toast.description",
                                                    preset.getDisplayName()
                                            )
                                    );

                                    OptionUtils.forEachOptions(
                                            screen.config,
                                            Option::forgetPendingValue
                                    );

                                    ZoomifySettings.Companion.saveToFile();

                                    screen.init(
                                            Minecraft.getInstance(),
                                            screen.width,
                                            screen.height
                                    );
                                })
                                .build()
                );
            }

            misc.group(presetsGroup.build());

            rootBuilder.category(misc.build());

            return rootBuilder
                    .build()
                    .generateScreen(parent);
        }
    }

    private static <T extends Number> Component formatSeconds(T value) {
        return Component.translatable(
                "zoomify.gui.formatter.seconds",
                String.format(
                        "%.1f",
                        value.doubleValue()
                )
        );
    }

    private static <T extends Number> Component formatPercent(T value) {
        return Component.literal(
                String.format(
                        "%dx",
                        value.intValue()
                )
        );
    }
}

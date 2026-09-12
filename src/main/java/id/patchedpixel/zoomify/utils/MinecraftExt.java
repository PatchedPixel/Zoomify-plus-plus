package id.patchedpixel.zoomify.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class MinecraftExt {
    private MinecraftExt() {}

    public static void toast(Component title, Component description) {
        toast(title, description, false);
    }

    public static void toast(Component title, Component description, boolean longer) {
        Minecraft minecraft = Minecraft.getInstance();

        SystemToast.SystemToastIds toastId = longer
                ? SystemToast.SystemToastIds.UNSECURE_SERVER_WARNING
                : SystemToast.SystemToastIds.PERIODIC_NOTIFICATION;

        SystemToast.add(
                minecraft.getToasts(),
                toastId,
                title,
                description
        );
    }

    public static ResourceLocation zoomifyRl(String path) {
        return ResourceLocation.fromNamespaceAndPath("zoomify", path);
    }

    public static void setScreen(Minecraft minecraft, Screen screen) {
        minecraft.setScreen(screen);
    }

    public static Screen getScreen(Minecraft minecraft) {
        return minecraft.screen;
    }
}

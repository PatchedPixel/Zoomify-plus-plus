package id.patchedpixel.zoomify.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public final class MinecraftExt {
    private MinecraftExt() {}

    public static void toast(Component title, Component description) {
        toast(title, description, false);
    }

    public static void toast(Component title, Component description, boolean longer) {
        Minecraft minecraft = Minecraft.getInstance();

        SystemToast.SystemToastId toastId = longer
                ? SystemToast.SystemToastId.UNSECURE_SERVER_WARNING
                : SystemToast.SystemToastId.PERIODIC_NOTIFICATION;

        SystemToast.add(
                minecraft.gui.toastManager(),
                toastId,
                title,
                description
        );
    }

    public static Identifier zoomifyRl(String path) {
        return Identifier.fromNamespaceAndPath("zoomify", path);
    }

    public static void setScreen(Minecraft minecraft, Screen screen) {
        minecraft.gui.setScreen(screen);
    }

    public static Screen getScreen(Minecraft minecraft) {
        return minecraft.gui.screen();
    }
}

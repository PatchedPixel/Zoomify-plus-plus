package id.patchedpixel.zoomify.config.lib.gui.utils;

import com.mojang.blaze3d.platform.InputConstants;

public final class KeyUtils {

    public static boolean hasShiftDown() {
        return isKeyDown(InputConstants.KEY_LSHIFT) ||
               isKeyDown(InputConstants.KEY_RSHIFT);
    }

    public static boolean hasControlDown() {
        return isKeyDown(InputConstants.KEY_LCONTROL) ||
               isKeyDown(InputConstants.KEY_RCONTROL);
    }

    public static boolean isKeyDown(int key) {
        return InputConstants.isKeyDown(key);
    }

    private KeyUtils() {
    }
}

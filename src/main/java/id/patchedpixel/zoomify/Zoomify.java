package id.patchedpixel.zoomify;

import com.mojang.blaze3d.platform.InputConstants;
import id.patchedpixel.zoomify.config.SpyglassBehaviour;
import id.patchedpixel.zoomify.config.ZoomifySettings;
import id.patchedpixel.zoomify.zoom.DefaultZoomHelpers;
import id.patchedpixel.zoomify.zoom.ZoomHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.commands.Commands;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static id.patchedpixel.zoomify.utils.MinecraftExt.setScreen;
import static id.patchedpixel.zoomify.utils.MinecraftExt.getScreen;
import static id.patchedpixel.zoomify.utils.MinecraftExt.zoomifyRl;

@Mod(value = Zoomify.MOD_ID, dist = Dist.CLIENT)
public class Zoomify {
    public static final String MOD_ID = "zoomify";
    public static Zoomify INSTANCE;

    public static final Logger LOGGER = LoggerFactory.getLogger("Zoomify");

    private final KeyMapping.Category zoomKeyCategory = KeyMapping.Category.register(zoomifyRl("category"));

    private final KeyMapping zoomKey = new KeyMapping("zoomify.key.zoom", InputConstants.Type.KEYSYM, InputConstants.KEY_C, zoomKeyCategory);
    private final KeyMapping secondaryZoomKey = new KeyMapping("zoomify.key.zoom.secondary", InputConstants.Type.KEYSYM, InputConstants.KEY_F6, zoomKeyCategory);
    private final KeyMapping scrollZoomIn = new KeyMapping("zoomify.key.zoom.in", -1, zoomKeyCategory);
    private final KeyMapping scrollZoomOut = new KeyMapping("zoomify.key.zoom.out", -1, zoomKeyCategory);

    private boolean zooming = false;
    private final ZoomHelper zoomHelper = DefaultZoomHelpers.regularZoomHelper(ZoomifySettings.Companion);

    private boolean secondaryZooming = false;
    private final ZoomHelper secondaryZoomHelper = DefaultZoomHelpers.secondaryZoomHelper(ZoomifySettings.Companion);

    private double previousZoomDivisor = 1.0;

    private int scrollSteps = 0;

    private boolean shouldPlaySound = false;

    private boolean displayGui = false;

    public Zoomify(IEventBus bus) {
        INSTANCE = this;

        bus.addListener(this::registerKeyMappings);

        NeoForge.EVENT_BUS.addListener(this::registerClientCommands);
        NeoForge.EVENT_BUS.addListener(this::onClientTick);
    }

    private Zoomify() {}

    public boolean getZooming() {
        return zooming;
    }

    public boolean getSecondaryZooming() {
        return secondaryZooming;
    }

    public double getPreviousZoomDivisor() {
        return previousZoomDivisor;
    }

    public static int getMaxScrollTiers() {
        return ZoomifySettings.Companion.getScrollStepCount().get();
    }

    public void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(this.zoomKey);
        event.register(this.secondaryZoomKey);

        if(ZoomifySettings.Companion.getKeybindScrolling()) {
            event.register(this.scrollZoomIn);
            event.register(this.scrollZoomOut);
        }
    }

    public void registerClientCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("zoomify")
                        .executes(ctx -> {
                            displayGui = true;
                            return 0;
                        })
        );
    }

    public void onClientTick(ClientTickEvent.Post event) {
        tick(Minecraft.getInstance());
    }

    private void tick(Minecraft minecraft) {
        boolean prevZooming = zooming;

        switch (ZoomifySettings.Companion.getZoomKeyBehaviour().get()) {
            case HOLD -> zooming = zoomKey.isDown();
            case TOGGLE -> {
                while (zoomKey.consumeClick()) {
                    zooming = !zooming;
                }
            }
        }

        while (secondaryZoomKey.consumeClick()) {
            secondaryZooming = !secondaryZooming;
        }

        if (ZoomifySettings.Companion.getKeybindScrolling()) {
            while (scrollZoomIn.consumeClick()) {
                scrollSteps++;
            }
            while (scrollZoomOut.consumeClick()) {
                scrollSteps--;
            }

            scrollSteps = Math.max(0, Math.min(scrollSteps, getMaxScrollTiers()));
        }

        handleSpyglass(minecraft, prevZooming);

        zoomHelper.tick(zooming, scrollSteps);
        secondaryZoomHelper.tick(secondaryZooming, scrollSteps);

        if (displayGui) {
            displayGui = false;
            setScreen(minecraft, id.patchedpixel.zoomify.config.SettingsGuiFactory.createSettingsGui(getScreen(minecraft)));
        }
    }

    public static float getZoomDivisor(float tickDelta) {
        Zoomify instance = INSTANCE;

        if (!instance.zooming) {
            if (!ZoomifySettings.Companion.getRetainZoomSteps().get())
                instance.scrollSteps = 0;

            instance.zoomHelper.reset();
        }

        double initial = instance.zoomHelper.getZoomDivisor(tickDelta);
        instance.previousZoomDivisor = initial;
        return (float) (initial * instance.secondaryZoomHelper.getZoomDivisor(tickDelta));
    }

    public static void mouseZoom(double mouseDelta) {
        Zoomify instance = INSTANCE;

        if (mouseDelta > 0) {
            instance.scrollSteps++;
        } else if (mouseDelta < 0) {
            instance.scrollSteps--;
        }

        instance.scrollSteps = Math.max(0, Math.min(instance.scrollSteps, getMaxScrollTiers()));
    }

    private void handleSpyglass(Minecraft minecraft, boolean prevZooming) {
        var cameraEntity = minecraft.getCameraEntity();

        if (cameraEntity instanceof AbstractClientPlayer player) {
            switch (ZoomifySettings.Companion.getSpyglassBehaviour().get()) {
                case ONLY_ZOOM_WHILE_HOLDING -> {
                    if (!player.isHolding(Items.SPYGLASS))
                        zooming = false;
                }
                case ONLY_ZOOM_WHILE_CARRYING -> {
                    if (!player.getInventory().hasAnyMatching(it -> it.is(Items.SPYGLASS)))
                        zooming = false;
                }
                case OVERRIDE -> {
                    if (player.isScoping())
                        zooming = zooming && minecraft.options.getCameraType().isFirstPerson();
                }
                default -> {}
            }

            boolean requiresSpyglass = ZoomifySettings.Companion.getSpyglassBehaviour().get() != SpyglassBehaviour.COMBINE;
            if (requiresSpyglass && player.isScoping()) {
                zooming = true;
            }

            if (shouldPlaySound) {
                if (!zooming && prevZooming) {
                    player.playSound(SoundEvents.SPYGLASS_STOP_USING, 1f, 1f);
                }
            }

            shouldPlaySound = switch (ZoomifySettings.Companion.getSpyglassSoundBehaviour().get()) {
                case NEVER -> false;
                case ALWAYS -> true;
                case ONLY_SPYGLASS -> player.isScoping() || (requiresSpyglass && zooming && player.isHolding(Items.SPYGLASS));
                case WITH_OVERLAY -> shouldRenderOverlay(
                        player,
                        minecraft.options.getCameraType().isFirstPerson() && player.isScoping()
                ) && requiresSpyglass;
            };

            if (shouldPlaySound) {
                if (zooming && !prevZooming) {
                    player.playSound(SoundEvents.SPYGLASS_USE, 1f, 1f);
                }
            }
        }
    }

    public static boolean shouldRenderOverlay(AbstractClientPlayer player, boolean isUsingSpyglass) {
        boolean zooming = INSTANCE.zooming;
        return switch (ZoomifySettings.Companion.getSpyglassOverlayVisibility().get()) {
            case NEVER -> false;
            case ALWAYS -> zooming;
            case HOLDING -> isUsingSpyglass
                    || (zooming && player.isHolding(Items.SPYGLASS))
                    && ZoomifySettings.Companion.getSpyglassBehaviour().get() != SpyglassBehaviour.COMBINE;
            case CARRYING -> zooming
                    && player.getInventory().hasAnyMatching((ItemStack stack) -> stack.is(Items.SPYGLASS));
        };
    }
}

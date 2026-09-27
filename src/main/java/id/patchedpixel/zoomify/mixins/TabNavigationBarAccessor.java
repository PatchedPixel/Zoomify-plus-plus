package id.patchedpixel.zoomify.mixins;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.components.TabButton;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TabNavigationBar.class)
public interface TabNavigationBarAccessor {
    @Accessor("tabManager")
    TabManager config$getTabManager();

    @Accessor("tabs")
    ImmutableList<Tab> config$getTabs();

    @Accessor("tabButtons")
    ImmutableList<TabButton> config$getTabButtons();
}

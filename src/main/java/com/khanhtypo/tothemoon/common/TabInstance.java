package com.khanhtypo.tothemoon.common;

import com.khanhtypo.tothemoon.utls.ModUtils;
import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegisterEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class TabInstance {
    public static final Set<TabInstance> ALL_TABS = new HashSet<>();
    private final ResourceLocation tabId;
    private final String translation;
    private final Set<ObjectSupplier<? extends ItemLike>> itemSet = ModUtils.resourceSortedSet();
    private final Supplier<ItemStack> icon;
    private final String translationKey;
    private boolean isLocked = false;

    public TabInstance(String name, String translation, Supplier<Supplier<? extends ItemLike>> icon) {
        this(ModUtils.location(name), translation, icon);
    }

    public TabInstance(ResourceLocation tabId, String translation, Supplier<Supplier<? extends ItemLike>> icon) {
        this.tabId = tabId;
        this.translationKey = "itemGroup." + getTabId().toLanguageKey();
        this.translation = translation;
        this.icon = () -> new ItemStack(icon.get().get());
        ALL_TABS.add(this);
    }

    public static void registerTabs(RegisterEvent event) {
        event.register(Registries.CREATIVE_MODE_TAB, helper -> {
            for (TabInstance tab : ALL_TABS) {
                helper.register(tab.getTabId(), CreativeModeTab.builder().icon(tab.getIcon()).title(Component.translatable(tab.getTranslationKey())).displayItems(tab.displayItemsGenerator()).build());
            }
        });
    }

    public final <T extends ItemLike> void addItem(ObjectSupplier<T> itemSupplier) {
        if (!this.isLocked) {
            this.itemSet.add(itemSupplier);
        } else {
            ToTheMoon.LOGGER.warn("Can not add item {} to {} because it is locked", itemSupplier.getId(), this.getTabId());
        }
    }

    public Supplier<ItemStack> getIcon() {
        return icon;
    }

    public ResourceLocation getTabId() {
        return tabId;
    }

    public String getTranslation() {
        return translation;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public CreativeModeTab.DisplayItemsGenerator displayItemsGenerator() {
        this.isLocked = true;
        return (param, output) -> output.acceptAll(this.itemSet.stream().map(Supplier::get).map(ItemStack::new).toList());
    }
}

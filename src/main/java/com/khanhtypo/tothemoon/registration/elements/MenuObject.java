package com.khanhtypo.tothemoon.registration.elements;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AccessibleMenuSupplier;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.registration.ModStats;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class MenuObject<T extends BaseMenu> extends SimpleObjectSupplier<MenuType<?>> {
    private static final Set<MenuObject<?>> ALL_MENU_TYPES = ModUtils.resourceSortedSet();
    private final ResourceLocation guiTexture;
    private final AccessibleMenuSupplier<T> menuSupplier;
    private @Nullable Component title = null;

    public MenuObject(String name, AccessibleMenuSupplier<T> menuSupplier) {
        super(ModRegistries.MENU_TYPES, name, () -> new MenuType<>(menuSupplier, FeatureFlags.DEFAULT_FLAGS));
        this.menuSupplier = menuSupplier;
        ModStats.CONTAINER_INTERACTION_MAP.put(this, this.getId().withPrefix("interact_with_"));
        this.guiTexture = texturePath(name);
        ALL_MENU_TYPES.add(this);
    }

    public static ResourceLocation texturePath(String fileName) {
        return ModUtils.location("textures/gui/%s.png".formatted(fileName));
    }

    public MenuObject<T> translateMenu(String enTranslation) {
        this.title = ModLanguageGenerator.createTranslatable("gui", this.getId().getPath(), enTranslation);
        return this;
    }

    public ResourceLocation getGuiPath() {
        return this.guiTexture;
    }


    public void openScreen(Player player, Level level, BlockPos clickedPos, MutableComponent name) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider((id, inv, p) -> this.menuSupplier.create(id, inv, ContainerLevelAccess.create(level, clickedPos)), name));
            this.awardOpenScreen(player);
        }
    }

    @Nullable
    public Component getGuiTitle() {
        return this.title;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MenuType<T> get() {
        return (MenuType<T>) super.get();
    }

    public void awardOpenScreen(Player player) {
        player.awardStat(ModStats.getCustomStat(this));
    }
}

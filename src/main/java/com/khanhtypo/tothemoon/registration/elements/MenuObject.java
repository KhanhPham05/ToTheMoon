package com.khanhtypo.tothemoon.registration.elements;

import com.khanhtypo.tothemoon.ModUtils;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AccessibleMenuSupplier;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicMenu;
import com.khanhtypo.tothemoon.data.c.ModLangProvider;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.registration.ModStats;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

public class MenuObject<T extends BasicMenu> extends BaseObjectSupplier<MenuType<?>> {
    private static final Set<MenuObject<?>> ALL_MENU_TYPES = ModUtils.resourceSortedSet();
    private final ResourceLocation guiTexture;
    private final AccessibleMenuSupplier<T> menuSupplier;
    private final MenuScreens.ScreenConstructor<T, ? extends AbstractContainerScreen<T>> screenConstructor;
    @Nullable
    private BlockObject<?> targetBlock;
    private @Nullable Component title = null;

    public MenuObject(String name, AccessibleMenuSupplier<T> menuSupplier, MenuScreens.ScreenConstructor<T, ? extends AbstractContainerScreen<T>> screenConstructor) {
        super(ModRegistries.MENU_TYPES, name, () -> new MenuType<>(menuSupplier, FeatureFlags.DEFAULT_FLAGS));
        this.menuSupplier = menuSupplier;
        this.screenConstructor = screenConstructor;
        ModStats.CONTAINER_INTERACTION_MAP.put(this, this.getId().withPrefix("interact_with_"));
        this.guiTexture = texturePath(name);
        ALL_MENU_TYPES.add(this);
    }

    @SuppressWarnings("unchecked")
    public static <A extends BasicMenu, B extends AbstractContainerScreen<A>> void registerScreen(BiConsumer<MenuObject<A>, MenuScreens.ScreenConstructor<A, B>> consumer) {
        ALL_MENU_TYPES.forEach(menu -> consumer.accept((MenuObject<A>) menu, (MenuScreens.ScreenConstructor<A, B>) menu.screenConstructor));
    }

    public static <M extends BasicMenu, B extends AbstractContainerScreen<M>> MenuObject<M> register(String name, BlockObject<?> targetBlock, AccessibleMenuSupplier<M> menuSupplier, MenuScreens.ScreenConstructor<M, B> screenConstructor) {
        return new MenuObject<>(name, menuSupplier, screenConstructor).setTargetedBlock(targetBlock);
    }

    private static ResourceLocation texturePath(String fileName) {
        return ModUtils.location("textures/gui/%s.png".formatted(fileName));
    }

    public MenuObject<T> translateMenu(String enTranslation) {
        this.title = ModLangProvider.createTranslatable("gui", this.getId().getPath(), enTranslation);
        return this;
    }

    @Nullable
    public BlockObject<?> getTargetedBlock() {
        return this.targetBlock != null ? this.targetBlock : null;
    }

    public MenuObject<T> setTargetedBlock(BlockObject<?> block) {
        this.targetBlock = block;
        return this;
    }

    public ResourceLocation getGuiPath() {
        return this.guiTexture;
    }


    public void openScreen(Player player, Level level, BlockPos clickedPos) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(
                    new SimpleMenuProvider(
                            (id, inv, p) -> this.createMenu(id, inv, level, clickedPos),
                            this.getGuiTitle()
                    )
            );
            this.awardOpenScreen(player);
        }
    }

    @Nullable
    public T createMenu(int window, Inventory inventory, @Nullable Level level, BlockPos blockPos) {
        return level instanceof ServerLevel ? this.menuSupplier.create(window, inventory, ContainerLevelAccess.create(level, blockPos)) : null;
    }

    public Component getGuiTitle() {
        return this.title != null ? this.title : Objects.requireNonNull(this.getTargetedBlock()).getTranslationName();
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

package com.khanhtypo.tothemoon.registration.elements;

import com.khanhtypo.tothemoon.ModUtils;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicMenu;
import com.khanhtypo.tothemoon.data.c.ModLangProvider;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.registration.ModStats;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class MenuObject<T extends BasicMenu> extends BaseObjectSupplier<MenuType<?>> {
    private final ResourceLocation guiTexture;
    private final BlockObject<?> targetBlock;
    private @Nullable Component title = null;


    private MenuObject(String name, Supplier<? extends MenuType<T>> builder, BlockObject<?> targetBlock) {
        super(ModRegistries.MENU_TYPES, name, builder);
        ModStats.CONTAINER_INTERACTION_MAP.put(this, this.getId().withPrefix("interact_with_"));
        this.guiTexture = texturePath(name);
        this.targetBlock = targetBlock;
    }

    public static <M extends BasicMenu> MenuObject<M> register(String name, BlockObject<?> targetBlock, IContainerFactory<M> factory) {
        return new MenuObject<>(name, () -> IForgeMenuType.create(factory), targetBlock);
    }

    public static <M extends BasicMenu> MenuObject<M> register(String name, BlockObject<?> targetBlock, MenuType.MenuSupplier<M> menuSupplier) {
        return new MenuObject<>(name, () -> new MenuType<>(menuSupplier, FeatureFlags.DEFAULT_FLAGS), targetBlock);
    }

    private static ResourceLocation texturePath(String fileName) {
        return ModUtils.location("textures/gui/%s.png".formatted(fileName));
    }

    public MenuObject<T> translateMenu(String enTranslation) {
        this.title = ModLangProvider.createTranslatable("gui", this.getId().getPath(), enTranslation);
        return this;
    }

    public Block getTargetedBlock() {
        return this.targetBlock.get();
    }

    public ResourceLocation getGuiPath() {
        return this.guiTexture;
    }

    public void openScreen(Player player, BlockPos clickedPos) {
        if (player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer,
                    new SimpleMenuProvider((id, inventory, p) -> this.get().create(id, inventory), this.title != null ? this.title : this.targetBlock.getTranslationName()),
                    clickedPos
            );
            serverPlayer.awardStat(ModStats.getCustomStat(this));
        }
    }

    public Component getGuiTitle() {
        return this.title != null ? this.title : this.getTargetedBlock().getName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public MenuType<T> get() {
        return (MenuType<T>) super.get();
    }
}

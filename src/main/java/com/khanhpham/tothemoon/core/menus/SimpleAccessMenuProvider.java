package com.khanhpham.tothemoon.core.menus;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class SimpleAccessMenuProvider implements MenuProvider {
    private final AccessMenuConstructor constructor;
    private final Block block;
    private final BlockPos blockPos;
    private final Component displayName;

    @Deprecated
    public SimpleAccessMenuProvider(AccessMenuConstructor constructor, Block block, BlockPos pos) {
        this.constructor = constructor;
        this.block = block;
        this.blockPos = pos;
        this.displayName = block.getName();
    }

    public SimpleAccessMenuProvider(AccessMenuConstructor constructor, Block block, BlockPos blockPos, Component displayName) {
        this.constructor = constructor;
        this.block = block;
        this.blockPos = blockPos;
        this.displayName = displayName;
    }

    @Override
    public Component getDisplayName() {
        return this.displayName;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return this.constructor.constructMenu(pContainerId, pPlayerInventory, this.block, ContainerLevelAccess.create(pPlayer.level, this.blockPos));
    }

    @FunctionalInterface
    public interface AccessMenuConstructor {
        AbstractContainerMenu constructMenu(int containerId, Inventory playerInventory, Block block, ContainerLevelAccess access);
    }
}

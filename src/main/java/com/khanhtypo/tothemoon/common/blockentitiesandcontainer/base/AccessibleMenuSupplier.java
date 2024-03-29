package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;

import java.util.Objects;

@FunctionalInterface
public interface AccessibleMenuSupplier<T extends BaseMenu> extends MenuType.MenuSupplier<T>, IForgeMenuType<T> {
    T create(int windowId, Inventory inventory, ContainerLevelAccess access);

    @Override
    default T create(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
        BlockPos pos = Objects.requireNonNull(extraData.readBlockPos(), "No Pos Value Is Set.");
        return this.create(windowId, playerInv, ContainerLevelAccess.create(playerInv.player.level(), pos));
    }

    @Override
    default T create(int p_39995_, Inventory p_39996_) {
        return this.create(p_39995_, p_39996_, ContainerLevelAccess.NULL);
    }
}

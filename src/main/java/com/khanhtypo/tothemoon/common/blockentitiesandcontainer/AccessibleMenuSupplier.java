package com.khanhtypo.tothemoon.common.blockentitiesandcontainer;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;

@FunctionalInterface
public interface AccessibleMenuSupplier<T extends BasicMenu> extends MenuType.MenuSupplier<T> {
    T create(int windowId, Inventory inventory, ContainerLevelAccess access);

    @Override
    default T create(int p_39995_, Inventory p_39996_) {
        return this.create(p_39995_, p_39996_, ContainerLevelAccess.NULL);
    }
}

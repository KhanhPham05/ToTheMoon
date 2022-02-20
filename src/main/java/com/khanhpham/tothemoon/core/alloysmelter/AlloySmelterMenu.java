package com.khanhpham.tothemoon.core.alloysmelter;

import com.khanhpham.tothemoon.utils.containers.BaseMenu;import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public class AlloySmelterMenu extends BaseMenu {
    protected AlloySmelterMenu(@Nullable MenuType<?> pMenuType, Container externalContainer, Inventory playerInventory, int pContainerId) {
        super(pMenuType, externalContainer, playerInventory, pContainerId);
    }
}

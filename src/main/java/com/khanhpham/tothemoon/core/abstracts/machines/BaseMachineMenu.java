package com.khanhpham.tothemoon.core.abstracts.machines;

import com.khanhpham.tothemoon.core.menus.BaseMenu;
import com.khanhpham.tothemoon.utils.slot.UpgradeSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public abstract class BaseMachineMenu extends BaseMenu {
    protected BaseMachineMenu(@Nullable MenuType<?> pMenuType, UpgradableContainer externalContainer, Inventory playerInventory, int pContainerId) {
        super(pMenuType, externalContainer, playerInventory, pContainerId);
    }

    @Override
    public UpgradableContainer getExternalContainer() {
        return (UpgradableContainer) externalContainer;
    }

    protected void addUpgradeSlots(int startIndex) {
        super.addSlot(new UpgradeSlot(getExternalContainer(), startIndex, 21, 24));
        super.addSlot(new UpgradeSlot(getExternalContainer(), startIndex + 1, 40, 24));
        super.addSlot(new UpgradeSlot(getExternalContainer(), startIndex + 2, 21, 43));
        super.addSlot(new UpgradeSlot(getExternalContainer(), startIndex + 3, 40, 43));
    }

    @Override
    protected void addPlayerInventorySlots(int beginX, int beginY) {
        super.addPlayerInventorySlots(beginX + 80, beginY);
    }

    @Override
    protected void addSlot(int index, int x, int y) {
        super.addSlot(index, x + 80, y);
    }
}

package com.khanhtypo.tothemoon.common.machine;

import com.khanhtypo.tothemoon.client.MutableSlot;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.AbstractMachineBlockEntity;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.SavableSimpleContainer;
import com.khanhtypo.tothemoon.common.item.upgrades.IUpgradeItem;
import com.khanhtypo.tothemoon.common.item.upgrades.UpgradeItemType;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("SameParameterValue")
public abstract class AbstractMachineMenu extends BaseMenu {
    public final Container upgradeContainer;
    protected final Container container;
    protected final ContainerData containerData;
    private int upgradeSlotIndex;

    public AbstractMachineMenu(MenuObject<? extends BaseMenu> menuObject, int windowId, Inventory playerInventory, ContainerLevelAccess accessor, Container container, Container upgradeContainer, ContainerData containerData) {
        super(menuObject, windowId, playerInventory, accessor);
        this.container = container;
        this.upgradeContainer = upgradeContainer;
        this.containerData = containerData;
        super.addDataSlots(this.containerData);
        super.addContainerListeners(container, upgradeContainer);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);

    }

    public int getData(int index) {
        return this.containerData.get(index);
    }

    public Container getContainer() {
        return this.container;
    }

    public boolean isActive() {
        return AbstractMachineBlockEntity.intToBoolean(this.containerData.get(this.containerData.getCount() - 1));
    }

    public void toggleActive() {
        int active = this.containerData.get(this.containerData.getCount() - 1);
        super.setData(
                this.containerData.getCount() - 1,
                active == 1 ? 0 : 1
        );
    }

    public void toggleRedstone(MachineRedstoneMode toSet) {
        super.setData(this.containerData.getCount() - 2, toSet.getIndex());
    }

    protected void addUpgradeSlots(int imageHeight, TagKey<Item> filter) {
        addUpgradeSlots(-60, imageHeight - 24, filter);
    }

    private void addUpgradeSlots(int topLeftX, int topLeftY, TagKey<Item> filter) {
        this.upgradeSlotIndex = super.slots.size();
        for (int i = 0; i < 3; i++) {
            super.addSlot(new MutableSlot(this.upgradeContainer, i, topLeftX - 22 + (i * 18), topLeftY) {
                @Override
                public boolean mayPlace(ItemStack toPut) {
                    if (toPut.getItem() instanceof IUpgradeItem upgradeItemToPut && toPut.is(filter)) {
                        return !container.hasAnyMatching(
                                toCheck -> {
                                    if (!ItemStack.isSameItem(toPut, toCheck) && toCheck.getItem() instanceof IUpgradeItem upgradeItemToCheck) {
                                        return UpgradeItemType.isSameTypeWith(upgradeItemToPut, upgradeItemToCheck);
                                    }

                                    return false;
                                }
                        );
                    }
                    return false;
                }

                @Override
                public boolean mayPickup(Player player) {
                    return container.canTakeItem(container, pSlot, container.getItem(pSlot));
                }

                @Override
                protected void onQuickCraft(ItemStack pStack, int pAmount) {
                    super.onQuickCraft(pStack, pAmount);
                }

                @Override
                public void onTake(Player pPlayer, ItemStack pStack) {
                    if (!pStack.isEmpty()) {
                        if (container instanceof SavableSimpleContainer savableSimpleContainer) {
                            savableSimpleContainer.onItemTaken(pSlot, pStack);
                        }
                    }
                }
            }.setActive(false));
        }
    }


    public int getUpgradeSlotIndex() {
        return this.upgradeSlotIndex;
    }
}

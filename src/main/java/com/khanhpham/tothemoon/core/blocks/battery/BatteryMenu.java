package com.khanhpham.tothemoon.core.blocks.battery;

import com.khanhpham.tothemoon.core.menus.BaseMenu;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.slot.EnergyItemSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BatteryMenu extends BaseMenu {
    private final ContainerData containerData;

    public BatteryMenu(Container externalContainer, Inventory playerInventory, int pContainerId, ContainerData containerData) {
        super(ModMenuTypes.BATTERY, pContainerId, playerInventory, externalContainer);
        this.containerData = containerData;

        addSlot(new EnergyItemSlot(externalContainer, 0, 10, 34));
        addSlot(new EnergyItemSlot(externalContainer, 1, 148, 34));
        addPlayerInventorySlots(8, 101);

        super.addDataSlots(containerData);
    }


    public BatteryMenu(int id, Inventory playerInventory) {
        this(new SimpleContainer(2), playerInventory, id, new SimpleContainerData(3));
    }

    public int getEnergyBar() {
        int i = containerData.get(0);
        int j = containerData.get(1);

        return j != 0 && i != 0 ? i * 147 / j : 0;
    }

    @NotNull
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            itemStack = stack1.copy();

            if (index <= 1) {
                if (!super.moveItemStackTo(stack1, 2, slots.size() - 1, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack1, itemStack);
            } else {
                if (!super.moveItemStackTo(stack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                } else if (index < 29) {
                    if (!super.moveItemStackTo(stack1, 29, 38, false)) {
                        return ItemStack.EMPTY;
                    } else if (!super.moveItemStackTo(stack1, 2, 29, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack1);
        }

        return itemStack;
    }

    public int getEnergyStored() {
        return containerData.get(0);
    }

    public int getEnergyCapacity() {
        return containerData.get(1);
    }

}



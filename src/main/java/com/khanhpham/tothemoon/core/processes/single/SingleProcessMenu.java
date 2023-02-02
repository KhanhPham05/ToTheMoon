package com.khanhpham.tothemoon.core.processes.single;

import com.khanhpham.tothemoon.core.menus.BaseMenu;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.MachineContainerData;
import com.khanhpham.tothemoon.utils.slot.EnergyItemSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class SingleProcessMenu extends BaseMenu {
    public SingleProcessMenu(int pContainerId, Inventory playerInventory) {
        this(pContainerId, playerInventory, new SimpleContainer(3), MachineContainerData.simple());
    }

    public SingleProcessMenu(int pContainerId, Inventory playerInventory, Container externalContainer, ContainerData data) {
        super(ModMenuTypes.SINGLE_PROCESS, pContainerId, playerInventory, externalContainer);

        super.addSlot(0, 63, 33);
        super.addSlot(1, 129, 33);
        super.addSlot(new EnergyItemSlot(externalContainer, 2, 3, 29));

        super.addDataSlots(data);

        super.addPlayerInventorySlots(30, 95);
    }


    public int getEnergyStatus() {
        int energyStored = super.getData(2);
        int capacity = super.getData(3);

        int percent = energyStored * 100 / (capacity > 0 ? capacity : 500000);

        if (percent <= 30) {
            return 0;
        } else if (percent <= 60) {
            return 1;
        } else return 2;
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = empty();
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
                if (!moveItemStackTo(stack1, 0, 1, false)) {
                    return empty();
                } else if (index < 29) {
                    if (moveItemStackTo(stack1, 29, 38, false)) {
                        return empty();
                    }
                } else if (index < 38 && !moveItemStackTo(stack1, 2, 38, false)) {
                    return empty();
                }
            }

            if (stack1.isEmpty()) {
                slot.set(empty());
            } else {
                slot.setChanged();
            }

            if (stack1.getCount() == itemStack.getCount()) {
                return empty();
            }

            slot.onTake(player, stack1);
        }

        return itemStack;
    }

    public int getEnergyBar() {
        int i = getData(2);
        int j = getData(3);

        return j != 0 && i != 0 ? i * 147 / j : 0;
    }

    public int getProcessBar() {
        int i = getData(0);
        int j = getData(1);

        return j != 0 && i != 0 ? i * 32 / j : 0;
    }

    @Override
    public int getEnergyCapacity() {
        return this.getData(3);
    }

    @Override
    public int getStoredEnergy() {
        return getData(2);
    }
}

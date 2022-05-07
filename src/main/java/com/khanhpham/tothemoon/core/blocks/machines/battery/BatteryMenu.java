package com.khanhpham.tothemoon.core.blocks.machines.battery;

import com.khanhpham.tothemoon.core.containers.BaseMenu;
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
        super(ModMenuTypes.BATTERY, externalContainer, playerInventory, pContainerId);
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

    public int getBatteryConnectionId() {
        return containerData.get(2);
    }

    @NotNull
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = empty();
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            itemStack = stack1.copy();

            if (index <= 1) {
                if (!super.moveItemStackTo(stack1, 2, slots.size() - 1, true)) {
                    return empty();
                }

                slot.onQuickCraft(stack1, itemStack);
            } else {
                if (!super.moveItemStackTo(stack1, 0, 1, false)) {
                    return empty();
                } else if (index < 29) {
                    if (!super.moveItemStackTo(stack1, 29, 38, false)) {
                        return empty();
                    } else if (!super.moveItemStackTo(stack1, 2, 29, false)) {
                        return empty();
                    }
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
}


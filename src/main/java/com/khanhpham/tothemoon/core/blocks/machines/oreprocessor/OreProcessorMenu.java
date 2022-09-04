package com.khanhpham.tothemoon.core.blocks.machines.oreprocessor;

import com.khanhpham.tothemoon.core.menus.BaseMenu;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OreProcessorMenu extends BaseMenu {
    private final ContainerData data;

    public OreProcessorMenu(int pContainerId, Inventory playerInventory) {
        this(pContainerId, playerInventory, new SimpleContainer(OreProcessorBlockEntity.CONTAINER_SIZE), new SimpleContainerData(OreProcessorBlockEntity.DATA_SIZE));
    }


    public OreProcessorMenu(int pContainerId, Inventory playerInventory, Container externalContainer, ContainerData data) {
        super(ModMenuTypes.ENERGY_PROCESSOR, pContainerId, playerInventory, externalContainer);
        this.data = data;

        super.addSlot(0, 44, 33);
        super.addSlot(1, 18, 37);
        super.addSlot(2, 116, 33);
        super.addSlot(3, 142, 37);

        super.addPlayerInventorySlots(8, 95);

        super.addDataSlots(data);
    }

    public int getEnergyBar() {
        int i = data.get(2);
        int j = data.get(3);

        return j != 0 && i != 0 ? i * 147 / j : 0;
    }

    public int getProcessBar() {
        int i = this.data.get(0);
        int j = this.data.get(1);

        return j != 0 && i != 0 ? i * 32 / j : 0;
    }

    @NotNull
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            itemStack = stack1.copy();

            int containerSize = OreProcessorBlockEntity.CONTAINER_SIZE;
            if (index < containerSize) {
                if (!super.moveItemStackTo(stack1, containerSize, slots.size() - 1, true)) return ItemStack.EMPTY;
                slot.onQuickCraft(stack1, itemStack);
            } else {
                if (!super.moveItemStackTo(stack1, 0, 2, false)) return ItemStack.EMPTY;
                else if (index < super.hotbarIndex - 1) {
                    if (!super.moveItemStackTo(stack1, super.hotbarIndex, super.containerEnds, false))
                        return ItemStack.EMPTY;
                } else if (index < super.containerEnds && !moveItemStackTo(stack1, containerSize - 1, super.containerEnds, false))
                    return ItemStack.EMPTY;
            }

            if (stack1.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();

            if (stack1.getCount() == itemStack.getCount()) return ItemStack.EMPTY;
            slot.onTake(player, stack1);
        }

        return itemStack;
    }
}

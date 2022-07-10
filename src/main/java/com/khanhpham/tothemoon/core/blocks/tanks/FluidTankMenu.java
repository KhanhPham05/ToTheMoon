package com.khanhpham.tothemoon.core.blocks.tanks;

import com.khanhpham.tothemoon.core.menus.BaseMenu;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.slot.FluidHandlerSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FluidTankMenu extends BaseMenu {
    public final ContainerData data;

    public FluidTankMenu(int pContainerId, Inventory playerInventory, Container externalContainer, ContainerData data) {
        super(ModMenuTypes.FLUID_TANK, pContainerId, playerInventory, externalContainer);
        super.addSlot(new FluidHandlerSlot(externalContainer, 0, 38, 40));
        super.addSlot(new FluidHandlerSlot(externalContainer, 1, 121, 40));

        this.data = data;

        super.addPlayerInventorySlots(8, 104);
        super.addDataSlots(this.data);
    }

    public FluidTankMenu(int pContainerId, Inventory playerInventory) {
        this(pContainerId, playerInventory, new SimpleContainer(2), new SimpleContainerData(2));
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
            } else if (!super.moveItemStackTo(stack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            } else if (index < 29) {
                if (!super.moveItemStackTo(stack1, 29, 38, false)) {
                    return ItemStack.EMPTY;
                } else if (!super.moveItemStackTo(stack1, 2, 29, false)) {
                    return ItemStack.EMPTY;
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

    int getAmount() {
        return data.get(0);
    }

    int getCapacity() {
        return data.get(1);
    }

    int getFluidHeight() {
        int store = data.get(0);
        int capacity = data.get(1);
        return capacity != 0 ? store * 69 / capacity : 0;
    }

    boolean canRenderFluid() {
        return getAmount() > 0 && getCapacity() > 0;
    }
}

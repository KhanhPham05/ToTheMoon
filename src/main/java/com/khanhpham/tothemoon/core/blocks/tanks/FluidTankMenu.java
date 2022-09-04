package com.khanhpham.tothemoon.core.blocks.tanks;

import com.khanhpham.tothemoon.core.menus.BaseMenu;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.slot.FluidHandlerSlot;
import net.minecraft.core.Registry;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class FluidTankMenu extends BaseMenu {
    public final ContainerData data;

    public FluidTankMenu(int pContainerId, Inventory playerInventory, Container externalContainer, ContainerData data) {
        super(ModMenuTypes.FLUID_TANK, pContainerId, playerInventory, externalContainer);
        super.addSlot(new FluidHandlerSlot(externalContainer, 0, 79, 24));
        super.addSlot(new Slot(externalContainer, 1, 79, 56));

        this.data = data;

        super.addPlayerInventorySlots(8, 104);
        super.addDataSlots(this.data);
    }

    public FluidTankMenu(int i, Inventory inventory) {
        this(i, inventory, new SimpleContainer(2), new SimpleContainerData(3));
    }

    @SuppressWarnings("deprecation")
    public FluidStack getFluid() {
        return new FluidStack(Registry.FLUID.byId(this.data.get(2)), this.data.get(0));
    }

    public int getTankCapacity() {
        return this.data.get(1);
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

    int getTankAmount() {
        return data.get(0);
    }

    int getCapacity() {
        return data.get(1);
    }

    @SuppressWarnings("deprecation")
    public Fluid getFluidObject() {
        return Registry.FLUID.byId(this.data.get(2));
    }
}

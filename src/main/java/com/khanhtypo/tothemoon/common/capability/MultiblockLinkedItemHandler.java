package com.khanhtypo.tothemoon.common.capability;

import com.khanhtypo.tothemoon.multiblock.IItemCapableMultiblockController;
import it.zerono.mods.zerocore.lib.multiblock.IMultiblockController;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Supplier;

public class MultiblockLinkedItemHandler<Controller extends IMultiblockController<Controller> & IItemCapableMultiblockController> implements IItemHandlerModifiable {
    protected final Supplier<Optional<Controller>> multiblockController;

    public MultiblockLinkedItemHandler(Supplier<Optional<Controller>> multiblockController) {
        this.multiblockController = multiblockController;
    }

    @Override
    public int getSlots() {
        return this.multiblockController.get().filter(IMultiblockController::isAssembled).map(IItemHandler::getSlots).orElse(0);
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return this.multiblockController.get().filter(IMultiblockController::isAssembled).map(container -> container.getStackInSlot(slot)).orElse(ItemStack.EMPTY);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return this.multiblockController.get().filter(IMultiblockController::isAssembled).map(container -> container.insertItem(slot, stack, simulate)).orElse(stack);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return this.multiblockController.get().filter(IMultiblockController::isAssembled).map(container -> container.extractItem(slot, amount, simulate)).orElse(ItemStack.EMPTY);
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.multiblockController.get().filter(IMultiblockController::isAssembled).map(container -> container.getSlotLimit(slot)).orElse(0);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return this.multiblockController.get().filter(IMultiblockController::isAssembled).map(container -> container.isItemValid(slot, stack)).orElse(true);
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        this.multiblockController.get().filter(IMultiblockController::isAssembled).ifPresent(controller -> controller.setStackInSlot(slot, stack));
    }
}

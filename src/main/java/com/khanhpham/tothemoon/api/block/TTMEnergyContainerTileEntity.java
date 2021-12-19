package com.khanhpham.tothemoon.api.block;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;

/**
 * Tile Entity that has energy storage and item storage
 */
public abstract class TTMEnergyContainerTileEntity extends TTMEnergyStorageTileEntity implements IInventory {

    public TTMEnergyContainerTileEntity(TileEntityType<?> tile) {
        super(tile);
    }

    protected abstract NonNullList<ItemStack> getInventorySlots();

    @Override
    public final CompoundNBT save(CompoundNBT nbt) {
        nbt = super.save(nbt);
        nbt = saveAllItems(nbt);
        saveExtra(nbt);
        return nbt;
    }

    @Override
    public final void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        loadAllItems(nbt);
        loadExtra(nbt);
    }

    protected abstract void loadExtra(CompoundNBT nbt);

    protected abstract void saveExtra(CompoundNBT nbt);

    protected CompoundNBT saveAllItems(CompoundNBT nbt) {
        return ItemStackHelper.saveAllItems(nbt, getInventorySlots());
    }

    protected void loadAllItems(CompoundNBT nbt) {
        ItemStackHelper.loadAllItems(nbt, getInventorySlots());
    }
}

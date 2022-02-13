package com.khanhpham.tothemoon.utils.te;

import com.khanhpham.tothemoon.utils.energy.Energy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public abstract class EnergyItemCapableTileEntity extends EnergyCapableTileEntity implements Container, MenuProvider, Nameable {
    protected final Component label;
    protected final int containerSize;
    protected NonNullList<ItemStack> items;

    public EnergyItemCapableTileEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @Nonnull Component label, final int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy);
        this.label = label;
        this.containerSize = containerSize;
        this.items = NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }


    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return this.items.get(pIndex);
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return ContainerHelper.removeItem(this.items, pIndex, pCount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return ContainerHelper.takeItem(this.items, pIndex);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        this.items.set(pIndex, pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return !(pPlayer instanceof FakePlayer);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public Component getName() {
        return label;
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return createMenu(pContainerId, pInventory);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ContainerHelper.saveAllItems(pTag, this.items);
        energy.save(pTag);
        saveExtra(pTag);
    }

    protected void saveExtra(CompoundTag tag) {}

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, this.items);
        energy.load(pTag);
        loadExtra(pTag);
    }

    protected void loadExtra(CompoundTag tag) {}

    @Nonnull
    protected abstract AbstractContainerMenu createMenu(int containerId, Inventory playerInventory);
}

package com.khanhpham.tothemoon.core.abstracts;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class ItemCapableBlockEntity extends BaseContainerBlockEntity {

    protected NonNullList<ItemStack> items;

    public ItemCapableBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, int inventorySize) {
        super(pType, pWorldPosition, pBlockState);

        this.items = NonNullList.withSize(inventorySize, ItemStack.EMPTY);
    }


    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return items.get(pIndex);
    }


    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return ContainerHelper.removeItem(items, pIndex, pIndex);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return ContainerHelper.takeItem(items, pIndex);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        items.set(pIndex, pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return !(pPlayer instanceof FakePlayer);
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ContainerHelper.saveAllItems(pTag, items);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, items);
    }
}

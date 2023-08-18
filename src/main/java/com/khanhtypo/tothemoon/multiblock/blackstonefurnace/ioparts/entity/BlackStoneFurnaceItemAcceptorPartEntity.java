package com.khanhtypo.tothemoon.multiblock.blackstonefurnace.ioparts.entity;

import com.khanhtypo.tothemoon.common.capability.MultiblockLinkedItemHandler;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.MultiblockBlackStoneFurnace;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class BlackStoneFurnaceItemAcceptorPartEntity extends AbstractBlackStoneFurnaceIOPartEntity<IItemHandler> {
    private final MultiblockLinkedItemHandler<MultiblockBlackStoneFurnace> targetStorage;
    private LazyOptional<IItemHandler> itemHandler;

    public BlackStoneFurnaceItemAcceptorPartEntity(BlockPos position, BlockState blockState) {
        super(ModBlockEntities.BLACK_STONE_FURNACE_ITEM_ACCEPTOR.get(), position, blockState);

        this.targetStorage = new MultiblockLinkedItemHandler<>(super::getMultiblockController);
        this.itemHandler = LazyOptional.of(() -> this.targetStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateForgeCaps();
        this.itemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveForgeCaps();
        this.itemHandler = LazyOptional.of(() -> this.targetStorage);
    }

    @Override
    @Nonnull
    protected Capability<IItemHandler> getRequiredCapability() {
        return ForgeCapabilities.ITEM_HANDLER;
    }

    @Override
    protected LazyOptional<IItemHandler> getHolder() {
        return this.itemHandler;
    }
}

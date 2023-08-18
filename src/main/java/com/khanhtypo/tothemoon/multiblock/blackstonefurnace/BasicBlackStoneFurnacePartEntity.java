package com.khanhtypo.tothemoon.multiblock.blackstonefurnace;

import it.zerono.mods.zerocore.base.multiblock.part.AbstractMultiblockEntity;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public abstract class BasicBlackStoneFurnacePartEntity extends AbstractMultiblockEntity<MultiblockBlackStoneFurnace> {
    public BasicBlackStoneFurnacePartEntity(BlockEntityType<?> type, BlockPos position, BlockState blockState) {
        super(type, position, blockState);
    }

    @Override
    protected ModelData getUpdatedModelData() {
        return ModelData.EMPTY;
    }

    @Override
    public Class<MultiblockBlackStoneFurnace> getControllerType() {
        return MultiblockBlackStoneFurnace.class;
    }

    @Override
    public MultiblockBlackStoneFurnace createController() {
        return new MultiblockBlackStoneFurnace(super.getCurrentWorld());
    }

    @Override
    public abstract boolean isGoodForPosition(PartPosition partPosition, IMultiblockValidator iMultiblockValidator);

    @Override
    public void onMachineActivated() {

    }

    @Override
    public void onMachineDeactivated() {

    }
}

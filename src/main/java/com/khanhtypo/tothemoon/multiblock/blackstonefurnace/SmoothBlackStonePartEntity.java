package com.khanhtypo.tothemoon.multiblock.blackstonefurnace;

import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import it.zerono.mods.zerocore.base.multiblock.part.AbstractMultiblockEntity;
import it.zerono.mods.zerocore.lib.multiblock.cuboid.PartPosition;
import it.zerono.mods.zerocore.lib.multiblock.validation.IMultiblockValidator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public class SmoothBlackStonePartEntity extends AbstractMultiblockEntity<MultiblockBlackStoneFurnace> {
    public SmoothBlackStonePartEntity(BlockPos position, BlockState blockState) {
        super(ModBlockEntities.SMOOTH_BLACK_STONE_PART.get(), position, blockState);
    }

    @Override
    public MultiblockBlackStoneFurnace createController() {
        return new MultiblockBlackStoneFurnace(getCurrentWorld());
    }

    @Override
    public Class<MultiblockBlackStoneFurnace> getControllerType() {
        return MultiblockBlackStoneFurnace.class;
    }

    @Override
    public void onMachineActivated() {

    }

    @Override
    public void onMachineDeactivated() {

    }

    @Override
    public boolean canOpenGui(Level world, BlockPos position, BlockState state) {
        return super.getMultiblockController().map(MultiblockBlackStoneFurnace::isAssembled).get();
    }

    @Override
    protected ModelData getUpdatedModelData() {
        return ModelData.EMPTY;
    }

    @Override
    public boolean isGoodForPosition(PartPosition partPosition, IMultiblockValidator iMultiblockValidator) {
        return partPosition.isFrame();
    }
}

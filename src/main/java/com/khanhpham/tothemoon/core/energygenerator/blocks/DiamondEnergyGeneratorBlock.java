package com.khanhpham.tothemoon.core.energygenerator.blocks;

import com.khanhpham.tothemoon.init.ModTileEntityTypes;
import com.khanhpham.tothemoon.utils.blocks.AbstractEnergyGeneratorBlock;
import com.khanhpham.tothemoon.utils.mining.MiningTool;
import com.khanhpham.tothemoon.utils.te.energygenerator.AbstractEnergyGeneratorTileEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class DiamondEnergyGeneratorBlock extends AbstractEnergyGeneratorBlock {
    public DiamondEnergyGeneratorBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<AbstractEnergyGeneratorTileEntity> supplier, MiningTool tool) {
        super(p_49224_, supplier, tool);
    }

    @Override
    protected <A extends BlockEntity> BlockEntityTicker<A> getTicker(Level level, BlockEntityType<A> pBlockEntityType) {
        return level.isClientSide ? null : createTickerHelper(pBlockEntityType, ModTileEntityTypes.DIAMOND_ENERGY_GENERATOR_TE, AbstractEnergyGeneratorTileEntity::serverTick);
    }
}

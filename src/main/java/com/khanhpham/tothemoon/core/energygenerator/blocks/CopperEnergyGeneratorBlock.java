package com.khanhpham.tothemoon.core.energygenerator.blocks;

import com.khanhpham.tothemoon.core.energygenerator.tileentities.CopperEnergyGeneratorTileEntity;
import com.khanhpham.tothemoon.init.ModTileEntityTypes;
import com.khanhpham.tothemoon.utils.blocks.AbstractEnergyGeneratorBlock;
import com.khanhpham.tothemoon.utils.mining.MiningTool;
import com.khanhpham.tothemoon.utils.te.energygenerator.AbstractEnergyGeneratorTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CopperEnergyGeneratorBlock extends AbstractEnergyGeneratorBlock {
    public CopperEnergyGeneratorBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<AbstractEnergyGeneratorTileEntity> supplier, MiningTool tool) {
        super(p_49224_, supplier, tool);

    }

    @NotNull
    @Override
    protected AbstractEnergyGeneratorTileEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CopperEnergyGeneratorTileEntity(pos, state);
    }

    @Override
    protected <A extends BlockEntity> BlockEntityTicker<A> getTicker(Level level, BlockEntityType<A> pBlockEntityType) {
        return level.isClientSide ? null : createTickerHelper(pBlockEntityType, ModTileEntityTypes.COPPER_ENERGY_GENERATOR_TE, CopperEnergyGeneratorTileEntity::serverTick);
    }
}

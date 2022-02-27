package com.khanhpham.tothemoon.core.alloysmelter;

import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.utils.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.utils.mining.MiningTool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AlloySmelterBlock extends BaseEntityBlock<AlloySmelterBlockEntity> {

    public AlloySmelterBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<AlloySmelterBlockEntity> supplier, MiningTool tool) {
        super(p_49224_, supplier, tool);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlockEntityTypes.ALLOY_SMELTER, AlloySmelterBlockEntity::serverTick);
    }
}

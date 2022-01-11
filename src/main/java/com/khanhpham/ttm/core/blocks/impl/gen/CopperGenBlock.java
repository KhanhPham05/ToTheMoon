package com.khanhpham.ttm.core.blocks.impl.gen;

import com.khanhpham.ttm.core.ToolType;
import com.khanhpham.ttm.core.blockentities.energygen.EnergyGeneratorBlockEntity;
import com.khanhpham.ttm.core.blockentities.energygen.impl.CopperGenEntity;
import com.khanhpham.ttm.core.blocks.BaseEnergyGenBlock;
import com.khanhpham.ttm.core.blocks.MiningLevel;
import com.khanhpham.ttm.init.ModMisc;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CopperGenBlock extends BaseEnergyGenBlock<CopperGenEntity> {
    public CopperGenBlock(Properties behaviour, ToolType toolType, MiningLevel miningLevel, BlockEntityType.BlockEntitySupplier<CopperGenEntity> blockEntity) {
        super(behaviour, toolType, miningLevel, blockEntity);
    }

    @Override
    protected void openMenu(Level level, BlockPos pos, Player player) {
        BlockEntity te = getBlockEntity(level, pos);
        if (te instanceof CopperGenEntity) {
            player.openMenu((MenuProvider) te);
        }
    }

    @Override
    public @Nullable <A extends BlockEntity> BlockEntityTicker<A> getTicker(Level pLevel, BlockState pState, BlockEntityType<A> pBlockEntityType) {
        return super.getTicker(pLevel, pState, pBlockEntityType);
    }

    @Override
    protected BlockEntityType<? extends EnergyGeneratorBlockEntity> getGeneratorEntity() {
        return ModMisc.COPPER_GEN_ENTITY;
    }
}

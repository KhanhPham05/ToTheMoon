package com.khanhpham.ttm.testfeatures;

import com.khanhpham.ttm.core.ToolType;
import com.khanhpham.ttm.core.blockentities.energygen.EnergyGeneratorBlockEntity;
import com.khanhpham.ttm.core.blocks.BaseEnergyGenBlock;
import com.khanhpham.ttm.core.blocks.MiningLevel;
import com.khanhpham.ttm.init.ModMisc;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class EnergyGenBlock extends BaseEnergyGenBlock<EnergyGeneratorBlockEntity> {
    public EnergyGenBlock(Properties behaviour, ToolType toolType, MiningLevel miningLevel, BlockEntityType.BlockEntitySupplier<EnergyGeneratorBlockEntity> blockEntity) {
        super(behaviour, toolType, miningLevel, blockEntity);
    }

    @Override
    protected BlockEntityType<? extends EnergyGeneratorBlockEntity> getGeneratorEntity() {
        return ModMisc.ENERGY_GEN_ENTITY;
    }

    @Override
    protected void openMenu(Level level, BlockPos pos, Player player) {
        BlockEntity te = getBlockEntity(level, pos);
        if (te instanceof EnergyGeneratorBlockEntity) {
            player.openMenu((EnergyGeneratorBlockEntity) te);
        }
    }
}

package com.khanhpham.ttm.core.blocks;

import com.khanhpham.ttm.core.ToolType;
import com.khanhpham.ttm.core.blockentities.energybank.BaseEnergyBankEntity;
import com.khanhpham.ttm.core.blockentities.energybank.EnergyBankEntity;
import com.khanhpham.ttm.init.ModMisc;
import com.khanhpham.ttm.utils.block.ModCapableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EnergyBankBlock extends ModCapableBlock<EnergyBankEntity> {
    public EnergyBankBlock(Properties behaviour, ToolType toolType, MiningLevel miningLevel, BlockEntityType.BlockEntitySupplier<EnergyBankEntity> blockEntity) {
        super(behaviour,toolType, miningLevel, blockEntity);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EnergyBankEntity(pPos, pState);
    }

    @Override
    protected void openMenu(Level level, BlockPos pos, Player player) {
        BlockEntity te = level.getBlockEntity(pos);
        if (te instanceof BaseEnergyBankEntity) {
            player.openMenu((MenuProvider) te);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, ModMisc.ENERGY_BANK_ENTITY, BaseEnergyBankEntity::tick);
    }


}

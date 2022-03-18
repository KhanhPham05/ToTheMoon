package com.khanhpham.tothemoon.core.storageblock;

import com.khanhpham.tothemoon.utils.blocks.BaseEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class MoonRockBarrel extends BaseEntityBlock<MoonBarrelTileEntity> {
    public MoonRockBarrel(Properties p_49224_, BlockEntityType.BlockEntitySupplier<MoonBarrelTileEntity> supplier) {
        super(p_49224_, supplier);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity te = pLevel.getBlockEntity(pPos);
            if (te instanceof MoonBarrelTileEntity barrelTileEntity) {
                pPlayer.openMenu(barrelTileEntity);
            }
        }
        return InteractionResult.SUCCESS;
    }
}

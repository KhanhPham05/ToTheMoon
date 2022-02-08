package com.khanhpham.tothemoon.core.storageblock;

import com.khanhpham.tothemoon.utils.blocks.TileEntityBlock;
import com.khanhpham.tothemoon.utils.mining.MiningTool;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * @see net.minecraft.world.level.block.AbstractFurnaceBlock
 */
public class MoonRockBarrel extends TileEntityBlock<MoonBarrelTileEntity> {
    public MoonRockBarrel(Properties p_49224_, BlockEntityType.BlockEntitySupplier<MoonBarrelTileEntity> supplier, MiningTool tool) {
        super(p_49224_, supplier, tool);
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

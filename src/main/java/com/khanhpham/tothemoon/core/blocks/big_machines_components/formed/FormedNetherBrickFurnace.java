package com.khanhpham.tothemoon.core.blocks.big_machines_components.formed;

import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceBlock;
import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceControllerBlockEntity;
import com.khanhpham.tothemoon.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class FormedNetherBrickFurnace extends BaseFormedMultiblock<NetherBrickFurnaceControllerBlockEntity> {

    public FormedNetherBrickFurnace() {
        super(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS));
    }

    public static void setFormedMultiblock(Level level, BlockPos blockPos, NetherBrickFurnaceControllerBlockEntity menu) {
        level.setBlock(blockPos, ModBlocks.NETHER_BRICK_FURNACE_FORMED.get().setFormed(menu, menu.getBlockState().getValue(NetherBrickFurnaceBlock.FACING)), 3);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            pPlayer.openMenu(super.multiblockController);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}

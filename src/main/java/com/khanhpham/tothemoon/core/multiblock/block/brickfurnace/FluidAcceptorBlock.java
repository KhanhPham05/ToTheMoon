package com.khanhpham.tothemoon.core.multiblock.block.brickfurnace;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;


@SuppressWarnings("deprecation")
public class FluidAcceptorBlock extends Block {
    /*controller perspective:
     *   x -> controller
     *      0
     *   3  x  1
     *      2
     */
    private @Nullable NetherBrickFurnaceControllerBlockEntity controllerBe;

    public FluidAcceptorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            ItemStack item = pPlayer.getMainHandItem();
            if (item.is(Items.LAVA_BUCKET)) {
                if (controllerBe != null) {
                    controllerBe.fillFromBucket(pPlayer);
                    if (!pPlayer.isCreative())
                        pPlayer.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET));
                    return InteractionResult.CONSUME;
                }
            }
        }
        return InteractionResult.FAIL;
    }

    public void setControllerBe(@Nullable NetherBrickFurnaceControllerBlockEntity controllerBe) {
        this.controllerBe = controllerBe;
    }

    public boolean isNotObstructed() {
        return this.controllerBe == null;
    }
}

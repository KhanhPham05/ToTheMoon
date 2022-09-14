package com.khanhpham.tothemoon.core.blocks.big_machines_components;

import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceControllerBlockEntity;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;


@SuppressWarnings("deprecation")
public final class FluidAcceptorBlock extends MultiblockComponentBlock implements IMultiblockComponent {
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
            ItemStack item = pPlayer.getItemInHand(pHand);
            if (item.is(Items.LAVA_BUCKET)) {
                if (this.controllerBe != null) {
                    if (this.controllerBe.getMultiblock() != null) {
                        this.contactToFurnace(pPlayer, pHand);
                    }
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    private void contactToFurnace(Player pPlayer, InteractionHand pHand) {
        if (this.controllerBe != null && this.controllerBe.canFillFromBucket()) {
            this.controllerBe.fillFromBucket(pPlayer);
            if (!pPlayer.isCreative())
                pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
        }
    }

    public void setControllerBe(@Nullable NetherBrickFurnaceControllerBlockEntity controllerBe) {
        this.controllerBe = controllerBe;
    }
}

package com.khanhpham.tothemoon.core.blocks.processblocks.metalpressingboard;

import com.khanhpham.tothemoon.core.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.datagen.tags.ModItemTags;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MetalPressingPlate extends BaseEntityBlock<MetalPressingPlateBlockEntity> {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape SHAPE = Shapes.join(Block.box(0, 0, 0, 16, 1, 16), Block.box(2, 1, 2, 14, 4, 14), BooleanOp.OR);

    public MetalPressingPlate(Properties properties, BlockEntityType.BlockEntitySupplier<MetalPressingPlateBlockEntity> supplier) {
        super(properties, supplier);
        super.registerDefaultState(getStateDefinition().any().setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity te = pLevel.getBlockEntity(pPos);
            if (te instanceof MetalPressingPlateBlockEntity be) {
                if (pLevel instanceof ServerLevel) {
                    Containers.dropContents(pLevel, pPos, be);
                }
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Nonnull
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pHand.equals(InteractionHand.MAIN_HAND)) {
            Optional<MetalPressingPlateBlockEntity> blockEntity = pLevel.getBlockEntity(pPos, ModBlockEntityTypes.METAL_PRESSING_PLATE.get());
            if (blockEntity.isPresent()) {
                MetalPressingPlateBlockEntity te = blockEntity.get();
                ItemStack item = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
                if (item.is(Tags.Items.INGOTS) || item.is(ModItemTags.GENERAL_PRESS_MOLDS.getMainTag())) {
                    if (te.attemptPutItem(item)) {
                        ItemStack copy = item.copy();
                        copy.setCount(item.getCount() - 1);
                        pPlayer.setItemInHand(InteractionHand.MAIN_HAND, copy);
                    }
                    return InteractionResult.PASS;
                } else if (item.is(ModItemTags.GENERAL_TTM_HAMMERS)) {
                    te.performPress(pLevel, pPos, pPlayer);
                } else if (item.isEmpty()) {
                    Block.popResourceFromFace(pLevel, pPlayer.blockPosition().above(), Direction.DOWN, te.takeItem());
                    te.checkSlots();
                }

                te.markDirty(pLevel, pPos, pState);
            }
        }


        return InteractionResult.PASS;
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntityTypes.METAL_PRESSING_PLATE.get();
    }
}

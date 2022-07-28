package com.khanhpham.tothemoon.core.multiblock.block.brickfurnace;

import com.khanhpham.tothemoon.core.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.core.blocks.HasCustomBlockItem;
import com.khanhpham.tothemoon.core.items.FluidCapableItem;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public final class NetherBrickFurnaceBlock extends BaseEntityBlock<NetherBrickFurnaceControllerBlockEntity> implements HasCustomBlockItem {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public NetherBrickFurnaceBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<NetherBrickFurnaceControllerBlockEntity> supplier) {
        super(p_49224_);
        registerDefaultState(defaultBlockState().setValue(LIT, false).setValue(FACING, Direction.NORTH));
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT, FACING);
    }

    @Override
    protected BlockEntityType<NetherBrickFurnaceControllerBlockEntity> getBlockEntityType() {
        return ModBlockEntities.BRICK_FURNACE.get();
    }

    @Override
    public @Nullable NetherBrickFurnaceControllerBlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new NetherBrickFurnaceControllerBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            if (pLevel.getBlockEntity(pPos) instanceof NetherBrickFurnaceControllerBlockEntity be) {
                if (be.getMultiblock() != null) {
                    super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
                } else return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        ModUtils.loadFluidToBlock(pLevel, pPos, pStack);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        if (pState.getValue(LIT)) {
            double d0 = (double)pPos.getX() + 0.5D;
            double d1 = pPos.getY();
            double d2 = (double)pPos.getZ() + 0.5D;
            Direction direction = pState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d4 = pRandom.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : d4;
            double d6 = pRandom.nextDouble() * 9.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : d4;
            pLevel.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            pLevel.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public NetherBrickFurnaceItem getRawItem() {
        return new NetherBrickFurnaceItem(this);
    }

    private static final class NetherBrickFurnaceItem extends FluidCapableItem {
        public NetherBrickFurnaceItem(Block pBlock) {
            super(pBlock, ModItems.GENERAL_PROPERTIES);
        }

        @Override
        protected int getFluidCapacity() {
            return NetherBrickFurnaceControllerBlockEntity.TANK_CAPACITY;
        }
    }
}


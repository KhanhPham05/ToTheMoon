package com.khanhpham.tothemoon.utils.blocks;

import com.khanhpham.tothemoon.utils.mining.MiningTool;
import com.khanhpham.tothemoon.utils.te.energygenerator.AbstractEnergyGeneratorTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * @see net.minecraft.world.level.block.AbstractFurnaceBlock
 */
public abstract class AbstractEnergyGeneratorBlock extends TileEntityBlock<AbstractEnergyGeneratorTileEntity> {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;


    public AbstractEnergyGeneratorBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<AbstractEnergyGeneratorTileEntity> supplier, MiningTool tool) {
        super(p_49224_.lightLevel(state -> state.getValue(LIT) ? 15 : 0), supplier, tool);

        registerDefaultState(super.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, Boolean.FALSE));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return createBlockEntity(pPos, pState);
    }

    protected abstract @Nonnull AbstractEnergyGeneratorTileEntity createBlockEntity(BlockPos pos, BlockState state);

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.FAIL;
        } else {
            BlockEntity te = pLevel.getBlockEntity(pPos);
            if (te instanceof AbstractEnergyGeneratorTileEntity energyGeneratorTileEntity) {
                pPlayer.openMenu(energyGeneratorTileEntity);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }

    /**
     * @see net.minecraft.world.level.block.AbstractFurnaceBlock
     *
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof AbstractEnergyGeneratorTileEntity energyGeneratorTileEntity) {
                if (pLevel instanceof ServerLevel) {
                    Containers.dropContents(pLevel, pPos, energyGeneratorTileEntity);
                }

                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public @Nullable <A extends BlockEntity> BlockEntityTicker<A> getTicker(Level pLevel, BlockState pState, BlockEntityType<A> pBlockEntityType) {
        return this.getTicker(pLevel, pBlockEntityType);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        if (pState.getValue(LIT)) {
            double d0 = (double) pPos.getX() + 0.5D;
            double d1 = pPos.getY();
            double d2 = (double) pPos.getZ() + 0.5D;
            if (pRandom.nextDouble() < 0.1D) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = pState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d4 = pRandom.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
            double d6 = pRandom.nextDouble() * 9.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;
            pLevel.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }

    protected abstract <A extends BlockEntity> BlockEntityTicker<A> getTicker(Level level, BlockEntityType<A> pBlockEntityType);
}

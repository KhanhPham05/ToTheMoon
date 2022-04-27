package com.khanhpham.tothemoon.core.blocks.cable;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AbstractCableBlock<T> extends Block {
    public static final Map<Direction, EnumProperty<com.khanhpham.tothemoon.core.blocks.cable.Connection>> DIRECTION_CONNECTION = new HashMap<>();

    static {
        for (Direction direction : Direction.values()) {
            DIRECTION_CONNECTION.put(direction, EnumProperty.create(direction.getName(), com.khanhpham.tothemoon.core.blocks.cable.Connection.class));
        }

    }

    protected final Capability<T> capability;

    public AbstractCableBlock(Capability<T> capability) {
        super(Properties.copy(Blocks.STONE).noOcclusion());

        BlockState state = this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE);
        for (EnumProperty<com.khanhpham.tothemoon.core.blocks.cable.Connection> connectionProperty : DIRECTION_CONNECTION.values()) {
            state.setValue(connectionProperty, com.khanhpham.tothemoon.core.blocks.cable.Connection.DISCONNECT);
        }
        super.registerDefaultState(state);
        this.capability = capability;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DIRECTION_CONNECTION.values().toArray(new EnumProperty[0])).add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return makeState(pContext.getLevel(), pContext.getClickedPos());
    }

    private BlockState makeState(Level level, BlockPos pos) {
        BlockState defaultBlockState = super.defaultBlockState();
        FluidState fluidState = level.getFluidState(pos);

        if (fluidState.is(Fluids.WATER) && fluidState.getAmount() == 8) {
            defaultBlockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.TRUE);
        }

        for (Direction direction : Direction.values()) {
            EnumProperty<com.khanhpham.tothemoon.core.blocks.cable.Connection> property = DIRECTION_CONNECTION.get(direction);
            com.khanhpham.tothemoon.core.blocks.cable.Connection connection = this.getConnection(level, pos, direction);
            defaultBlockState.setValue(property, connection);
        }
        return defaultBlockState;
    }

    private com.khanhpham.tothemoon.core.blocks.cable.Connection getConnection(Level level, BlockPos pos, Direction direction) {
        BlockPos directed = pos.relative(direction);
        if (!level.isLoaded(directed)) {
            return Connection.DISCONNECT;
        }

        Direction oppositeDirection = direction.getOpposite();
        CableConnectable capability = this.getCapability(ModUtils.PIPE_CONNECTABLE_CAPABILITY, level, pos, oppositeDirection);
        return com.khanhpham.tothemoon.core.blocks.cable.Connection.DISCONNECT;
    }

    @Deprecated
    @Nullable
    public T getCapability(Level level, BlockPos pos, Direction side) {
        return this.getCapability(this.capability, level, pos, side);
    }

    @Nullable
    public <A> A getCapability(Capability<A> capability, Level level, BlockPos pos, Direction side) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity != null ? blockEntity.getCapability(capability, side).orElse(null) : null;
    }
}

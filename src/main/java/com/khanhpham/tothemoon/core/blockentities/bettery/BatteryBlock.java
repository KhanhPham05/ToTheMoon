package com.khanhpham.tothemoon.core.blockentities.bettery;

import com.khanhpham.tothemoon.core.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class BatteryBlock extends BaseEntityBlock<BatteryBlockEntity> {

    public static final IntegerProperty ENERGY_LEVEL = ModUtils.ENERGY_LEVEL;
    public static final EnumProperty<BatteryConnectionMode> CONNECTION_MODE = ModUtils.BATTERY_CONNECTION_MODE;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public BatteryBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<BatteryBlockEntity> supplier) {
        super(p_49224_, supplier);
        super.registerDefaultState(defaultBlockState().setValue(ENERGY_LEVEL, 0).setValue(FACING, Direction.NORTH).setValue(CONNECTION_MODE, BatteryConnectionMode.NONE));
    }

    @Override
    protected BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntityTypes.BATTERY.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ENERGY_LEVEL, FACING, CONNECTION_MODE);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlockEntityTypes.BATTERY.get(), BatteryBlockEntity::serverTick);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();

        if (ModUtils.getBlock(level, pos.above()).is(this) && ModUtils.getBlock(level, pos.above()).is(this)) {
            state.setValue(CONNECTION_MODE, BatteryConnectionMode.CROSS);
        }
        if (ModUtils.getBlock(level, pos.above()).is(this)) {
            state.setValue(CONNECTION_MODE, BatteryConnectionMode.ABOVE);
        } else if (ModUtils.getBlock(level, pos.below()).is(this)) {
            state.setValue(CONNECTION_MODE, BatteryConnectionMode.BELOW);
        } else {
            state.setValue(CONNECTION_MODE, BatteryConnectionMode.NONE);
        }

        return state;
    }
}

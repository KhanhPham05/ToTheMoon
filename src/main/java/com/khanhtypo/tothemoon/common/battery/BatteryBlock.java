package com.khanhtypo.tothemoon.common.battery;

import com.khanhtypo.tothemoon.common.block.TickableEntityBlock;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class BatteryBlock extends TickableEntityBlock<BatteryBlockEntity> {
    public static final IntegerProperty ENERGY_LEVEL = IntegerProperty.create("battery_level", 0, 10);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private final BatteryLevel batteryLevel;

    public BatteryBlock(Properties p_49795_, BatteryLevel batteryLevel) {
        super(p_49795_.lightLevel(state -> {
            int level = state.getValue(ENERGY_LEVEL);
            return level > 0 ? 5 + level : 0;
        }), ModBlockEntities.BATTERY);
        this.batteryLevel = batteryLevel;
        super.registerDefaultState(state -> state.setValue(ENERGY_LEVEL, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ENERGY_LEVEL, FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        CompoundTag tag = pStack.getTag();
        if (tag != null && tag.contains("MachineData")) {
            pLevel.getBlockEntity(pPos, ModBlockEntities.BATTERY.get()).ifPresent(battery -> battery.loadEnergy(tag.getCompound("MachineData")));
        }
    }

    public int getEnergyCapacity() {
        return this.batteryLevel.energyCapacity;
    }
}

package com.khanhpham.tothemoon.core.blocks.machines.battery.creative;

import com.khanhpham.tothemoon.core.blocks.machines.battery.BatteryBlock;
import net.minecraft.core.Direction;

public class CreativeBatteryBlock extends BatteryBlock {
    public CreativeBatteryBlock(Properties p_49224_) {
        super(p_49224_, CreativeBatteryBlockEntity::new);
        super.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(ENERGY_LEVEL, 10));
    }
}

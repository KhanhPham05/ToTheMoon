package com.khanhpham.tothemoon.core.blocks.machines;

import com.khanhpham.tothemoon.core.abstracts.machines.BaseMachineBlockEntity;
import com.khanhpham.tothemoon.core.blocks.BaseEntityBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

@Deprecated
public abstract class BaseMachineBlock<T extends BaseMachineBlockEntity> extends BaseEntityBlock<T> {
    public BaseMachineBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<T> supplier) {
        super(p_49224_, supplier);
    }
}

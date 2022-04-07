package com.khanhpham.tothemoon.core.machines.energybank;

import com.khanhpham.tothemoon.utils.blocks.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class AbstractEnergyBankBlock extends BaseEntityBlock<AbstractEnergyBankBlockEntity> {
    public AbstractEnergyBankBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<AbstractEnergyBankBlockEntity> supplier) {
        super(p_49224_, supplier);
    }
}

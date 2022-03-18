package com.khanhpham.tothemoon.core.energybank;

import com.khanhpham.tothemoon.utils.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.utils.mining.MiningTool;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class AbstractEnergyBankBlock extends BaseEntityBlock<AbstractEnergyBankBlockEntity> {
    public AbstractEnergyBankBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<AbstractEnergyBankBlockEntity> supplier, MiningTool tool) {
        super(p_49224_, supplier, tool);
    }
}

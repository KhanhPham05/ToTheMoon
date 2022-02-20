package com.khanhpham.tothemoon.core.alloysmelter;

import com.khanhpham.tothemoon.utils.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.utils.mining.MiningTool;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class AlloySmelterBlock extends BaseEntityBlock<AlloySmelterBlockEntity> {

    public AlloySmelterBlock(Properties p_49224_, BlockEntityType.BlockEntitySupplier<AlloySmelterBlockEntity> supplier, MiningTool tool) {
        super(p_49224_, supplier, tool);
    }
}

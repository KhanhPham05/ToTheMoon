package com.khanhpham.tothemoon.utils.blocks;

import com.khanhpham.tothemoon.utils.mining.MiningTool;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MineableSlabBlocks extends SlabBlock{
    public MineableSlabBlocks(Properties p_56359_) {
        super(p_56359_);
    }

    public MineableSlabBlocks(Block parentBlock) {
        this(BlockBehaviour.Properties.copy(parentBlock));
    }
}

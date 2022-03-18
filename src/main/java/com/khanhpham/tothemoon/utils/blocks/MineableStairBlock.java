package com.khanhpham.tothemoon.utils.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MineableStairBlock extends StairBlock {

    public MineableStairBlock(BlockState pBaseState, Properties pProperties, Block parentBlock) {
        super(() -> pBaseState, pProperties);
    }

    public MineableStairBlock(Block parentBlock) {
        this(parentBlock.defaultBlockState(), BlockBehaviour.Properties.copy(parentBlock), parentBlock);
    }
}

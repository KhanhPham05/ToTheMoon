package com.khanhpham.tothemoon.utils.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ModStairBlock extends StairBlock {

    public ModStairBlock(BlockState pBaseState, Properties pProperties, Block parentBlock) {
        super(() -> pBaseState, pProperties);
        this.parentBlock = parentBlock;
    }

    final Block parentBlock;

    public ModStairBlock(Block parentBlock) {
        this(parentBlock.defaultBlockState(), BlockBehaviour.Properties.copy(parentBlock), parentBlock);
    }

    public Block parentBlock() {
        return this.parentBlock;
    }

}

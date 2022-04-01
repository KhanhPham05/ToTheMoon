package com.khanhpham.tothemoon.utils.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModSlabBlock extends SlabBlock {
    private final Block parentBlock;

    public ModSlabBlock(Block parentBlock) {
        super(BlockBehaviour.Properties.copy(parentBlock));
        this.parentBlock = parentBlock;
    }

    public Block parentBlock() {return this.parentBlock;}
}

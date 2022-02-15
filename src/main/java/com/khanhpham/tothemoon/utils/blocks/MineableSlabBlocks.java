package com.khanhpham.tothemoon.utils.blocks;

import com.khanhpham.tothemoon.utils.mining.MiningTool;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MineableSlabBlocks extends SlabBlock implements Mineable , CraftedBlock{
    private final MiningTool tool;
    private final Block parentBlock;

    public MineableSlabBlocks(Properties p_56359_, MiningTool tool, Block parentBlock) {
        super(p_56359_);
        this.tool = tool;
        this.parentBlock = parentBlock;
    }

    public MineableSlabBlocks(MiningTool tool, Block parentBlock) {
        this(BlockBehaviour.Properties.copy(parentBlock), tool, parentBlock);
    }

    @Override
    public MiningTool getTool() {
        return tool;
    }

    @Override
    public Block parentBlock() {
        return parentBlock;
    }
}

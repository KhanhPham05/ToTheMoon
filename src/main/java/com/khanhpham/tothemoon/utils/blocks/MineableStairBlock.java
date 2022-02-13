package com.khanhpham.tothemoon.utils.blocks;

import com.khanhpham.tothemoon.utils.mining.MiningTool;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MineableStairBlock extends StairBlock implements Mineable, CraftedBlock {
    private final Block parentBlock;
    private final MiningTool tool;

    public MineableStairBlock(BlockState pBaseState, Properties pProperties,Block parentBlock, MiningTool tool) {
        super(() -> pBaseState, pProperties);
        this.tool = tool;
        this.parentBlock = parentBlock;
    }

    public MineableStairBlock(Block parentBlock, MiningTool tool) {
        this(parentBlock.defaultBlockState(), BlockBehaviour.Properties.copy(parentBlock), parentBlock, tool);
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

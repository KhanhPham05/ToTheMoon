package com.khanhpham.ttm.core.blocks.variants;

import com.khanhpham.ttm.core.ToolType;
import com.khanhpham.ttm.core.blocks.MineableBlock;
import com.khanhpham.ttm.core.blocks.MiningLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

/**
 * @see Blocks#ACACIA_STAIRS
 */
public class MineableStairBlock extends StairBlock implements MineableBlock {
    private final ToolType tool;
    private final MiningLevel level;

    private MineableStairBlock(Supplier<BlockState> state, Properties properties, ToolType tool, MiningLevel level) {
        super(state, properties);
        this.level = level;
        this.tool = tool;
    }

    public MineableStairBlock(Block parentBlock, Properties properties, ToolType tool, MiningLevel level) {
        this(parentBlock::defaultBlockState, properties, tool, level);
    }

    @Override
    public ToolType getHarvestTool() {
        return tool;
    }

    @Override
    public MiningLevel getMiningLevel() {
        return level;
    }
}

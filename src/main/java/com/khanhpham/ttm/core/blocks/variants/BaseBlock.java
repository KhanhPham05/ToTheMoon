package com.khanhpham.ttm.core.blocks.variants;

import com.khanhpham.ttm.core.ToolType;
import com.khanhpham.ttm.core.blocks.MineableBlock;
import com.khanhpham.ttm.core.blocks.MiningLevel;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class BaseBlock extends Block implements MineableBlock {

    private final ToolType toolType;
    private final MiningLevel miningLevel;


    public BaseBlock(Properties p_49795_, ToolType toolType, MiningLevel miningLevel) {
        super(p_49795_);
        this.toolType = toolType;
        this.miningLevel = miningLevel;
    }

    @Override
    public @NotNull ToolType getHarvestTool() {
        return toolType;
    }

    @Override
    public @NotNull MiningLevel getMiningLevel() {
        return miningLevel;
    }
}

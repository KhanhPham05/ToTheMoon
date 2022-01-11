package com.khanhpham.ttm.core.blocks;

import com.khanhpham.ttm.core.ToolType;

public interface MineableBlock {
    ToolType getHarvestTool();

    MiningLevel getMiningLevel();
}

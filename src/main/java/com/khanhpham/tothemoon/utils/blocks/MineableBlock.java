package com.khanhpham.tothemoon.utils.blocks;

import com.khanhpham.tothemoon.utils.mining.MiningTool;
import net.minecraft.world.level.block.Block;

public class MineableBlock extends Block implements Mineable {
    private final MiningTool tool;

    public MineableBlock(Properties p_49795_, MiningTool tool) {
        super(p_49795_);
        this.tool = tool;
    }

    @Override
    public MiningTool getTool() {
        return this.tool;
    }
}

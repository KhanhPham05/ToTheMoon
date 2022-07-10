package com.khanhpham.tothemoon.core.blocks.others;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SmoothBlackStoneBlock extends Block {
    public SmoothBlackStoneBlock() {
        super(Properties.copy(Blocks.POLISHED_BLACKSTONE));
    }

    private boolean isObstructed = false;

    public boolean isObstructed() {
        return isObstructed;
    }

    public void setObstructed(boolean obstructed) {
        isObstructed = obstructed;
    }
}

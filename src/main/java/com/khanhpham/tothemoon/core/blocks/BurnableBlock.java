package com.khanhpham.tothemoon.core.blocks;

import net.minecraft.world.level.block.Block;

public class BurnableBlock extends Block {
    private final int burningTime;

    public BurnableBlock(Properties p_49795_, int burningTime) {
        super(p_49795_);
        this.burningTime = burningTime;
    }

    public int getBurningTime() {
        return burningTime;
    }
}

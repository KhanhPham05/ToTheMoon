package com.khanhpham.tothemoon.utils.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.HashSet;

public class MultiblockManager {
    private static final HashSet<Multiblock> MULTIBLOCKS = new HashSet<>();
    public static MultiblockManager INSTANCE;

    public MultiblockManager() {
        INSTANCE = this;
    }

    public void checkAndRemoveMultiblocks(Level level, BlockPos pos) {
        MULTIBLOCKS.removeIf(multiblock -> multiblock.isMultiblockDisconstructed(level, pos));
    }

    public Multiblock addMultiblock(Multiblock multiblock) {
        if (MULTIBLOCKS.add(multiblock)) {
            return multiblock;
        }
        throw new IllegalStateException("Duplicate Multiblock");
    }
}

package com.khanhpham.tothemoon.utils.multiblock;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Multiblock {
    final Map<BlockPos, PartType> multiblockPartPositions;
    final BlockPos controllerPos;

    public Multiblock(Map<BlockPos, PartType> multiblockPartPositions, BlockPos controllerPos) {
        this.multiblockPartPositions = multiblockPartPositions;
        this.controllerPos = controllerPos;
    }

    public boolean isMultiblockDistrusted(Level level, BlockPos brokenPos) {
        ModUtils.log("Checking multiblock with controller at {} because a block at {} is broken", controllerPos, brokenPos);
        if (multiblockPartPositions.containsKey(brokenPos)) {
            ModUtils.log("Multiblock with controller at {} has been removed", controllerPos);
            if (level.getBlockEntity(controllerPos) instanceof MultiblockEntity multiblock) {
                multiblock.setMultiblock(null);
            }
        }

        return false;
    }

    public boolean checkBlock(BlockPos pos) {
        return this.multiblockPartPositions.containsKey(pos);
    }

    public enum PartType {
        CONTROLLER,
        PART
    }

    public static final class Builder {
        final ArrayList<BlockPos> multiblockPartPositions;
        final ArrayList<PartType> multiblockPartType;
        final int cap;
        int controllerAt = -1;

        public Builder(int cap) {
            this.cap = cap;
            multiblockPartPositions = new ArrayList<>(cap);
            multiblockPartType = new ArrayList<>(cap);
        }

        public static Builder setup(int cap) {
            return new Builder(cap);
        }

        public void addPart(BlockPos pos, PartType type) {
            if (type == PartType.CONTROLLER && controllerAt < 0) this.controllerAt = multiblockPartPositions.size();
            multiblockPartPositions.add(pos);
            multiblockPartType.add(type);
        }

        public Multiblock build() {
            if (controllerAt < 0) ModUtils.log("Missing Controller");
            var map = new HashMap<BlockPos, PartType>();
            for (int i = 0; i < cap; i++) {
                map.put(multiblockPartPositions.get(i), multiblockPartType.get(i));
            }
            return new Multiblock(map, multiblockPartPositions.get(controllerAt));
        }
    }
}

package com.khanhpham.tothemoon.core.blocks;

import net.minecraft.util.StringRepresentable;

public enum WorkbenchPart implements StringRepresentable {
    RIGHT,
    LEFT;

    @Override
    public String getSerializedName() {
        return this.toString();
    }
}

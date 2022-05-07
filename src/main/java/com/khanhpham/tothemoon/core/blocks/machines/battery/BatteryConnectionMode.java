package com.khanhpham.tothemoon.core.blocks.machines.battery;

import net.minecraft.util.StringRepresentable;

public enum BatteryConnectionMode implements StringRepresentable {
    ABOVE,
    BELOW,
    CROSS,
    NONE;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}

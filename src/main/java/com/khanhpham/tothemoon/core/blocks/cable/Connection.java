package com.khanhpham.tothemoon.core.blocks.cable;

import net.minecraft.util.StringRepresentable;

public enum Connection implements StringRepresentable {
    CONNECTED,
    DISCONNECT;

    private final String serializedName;

    Connection() {
        this.serializedName = this.name();
    }

    @Override
    public String getSerializedName() {
        return this.serializedName;
    }
}

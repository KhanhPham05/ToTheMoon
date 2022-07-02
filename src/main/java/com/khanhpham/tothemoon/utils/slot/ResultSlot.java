package com.khanhpham.tothemoon.utils.slot;

import net.minecraft.world.Container;

public class ResultSlot extends FilterSlot {
    public ResultSlot(Container pContainer, int pIndex, int pX, int pY) {
        super(pContainer, pIndex, pX, pY, (stack) -> false);
    }
}

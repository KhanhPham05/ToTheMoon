package com.khanhtypo.tothemoon.api.abstracts;

import net.minecraft.nbt.CompoundTag;

public interface ICompoundNbtReader {
    void readCompound(CompoundTag toRead);
}

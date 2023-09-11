package com.khanhtypo.tothemoon.api.helpers;

import net.minecraft.nbt.CompoundTag;

@FunctionalInterface
public interface ICompoundTagGetter {
    CompoundTag getCompoundTag(CompoundTag rootTag);
}

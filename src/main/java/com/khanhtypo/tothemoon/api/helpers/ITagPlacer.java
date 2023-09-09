package com.khanhtypo.tothemoon.api.helpers;

import net.minecraft.nbt.CompoundTag;

@FunctionalInterface
public interface ITagPlacer {
    void placeTag(final MutableCompoundTag rootNbt, final String savedName, final CompoundTag toBePlaced);
}

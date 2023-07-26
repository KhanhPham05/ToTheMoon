package com.khanhtypo.tothemoon.common;

import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;
import java.util.Locale;

public interface StringRepresentableImpl extends StringRepresentable {
    @Override
    @Nonnull
    default String getSerializedName() {
        return this.toString().toLowerCase(Locale.ROOT);
    }
}
package com.khanhtypo.tothemoon.api.helpers;

import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class MutableCompoundTag {
    private final CompoundTag compoundTag;

    public MutableCompoundTag(CompoundTag compoundTag) {
        this.compoundTag = compoundTag;
    }

    public MutableCompoundTag putIf(String pKey, boolean pCondition, Tag pToPut) {
        if (pCondition) {
            return this.putIfPresent(pKey, pToPut);
        }
        return this;
    }

    public MutableCompoundTag putIfPresent(@Nonnull String pKey, @Nullable Tag pToPut) {
        if (pToPut != null) {
            this.compoundTag.put(pKey, pToPut);
        }

        return this;
    }

    public MutableCompoundTag put(String pKey, Tag pValue) {
        this.compoundTag.put(pKey, pValue);
        return this;
    }

    public MutableCompoundTag putInt(String pKey, int pValue) {
        this.compoundTag.putInt(pKey, pValue);
        return this;
    }

    public MutableCompoundTag putString(String pKey, ResourceLocation id) {
        return this.putString(pKey, id.toString());
    }

    public MutableCompoundTag putString(String pKey, String pValue) {
        this.compoundTag.putString(pKey, pValue);
        return this;
    }

    public CompoundTag getCompoundOrThrow(String pKey) {
        if (this.compoundTag.contains(pKey, Tag.TAG_COMPOUND)) return this.compoundTag.getCompound(pKey);
        throw ModUtils.fillCrashReport(new IllegalStateException(), "Member " + pKey + " not present or is not an instance of CompoundTag", "Gathering Tag Data", crashReportCategory -> crashReportCategory.setDetail("Tag", this));
    }

    public CompoundTag getOrCreateCompound(String pKey) {
        if (this.compoundTag.contains(pKey, Tag.TAG_COMPOUND)) {
            return this.compoundTag.getCompound(pKey);
        }

        this.put(pKey, new CompoundTag());
        return this.getOrCreateCompound(pKey);
    }

    public CompoundTag build() {
        return this.compoundTag;
    }

    @Override
    public String toString() {
        return this.compoundTag.toString();
    }
}

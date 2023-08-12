package com.khanhtypo.tothemoon.common.machine;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;


import java.util.List;

import static com.khanhtypo.tothemoon.data.c.ModLanguageGenerator.*;
public enum MachineRedstoneMode {
    IGNORE(0, REDSTONE_IGNORED, REDSTONE_IGNORED_DESC),
    NEED_REDSTONE(1, REDSTONE_REQUIRED, REDSTONE_REQUIRED_DESC),
    REDSTONE_PREVENT_MACHINE(2, REDSTONE_STOP_MACHINE, REDSTONE_STOP_MACHINE_DESC);
    private final int index;
    private final Component tooltipTitle;
    private final List<Component> tooltipComponents;
    public static final String TAG_SAVE_KEY = "RedstoneMode";

    MachineRedstoneMode(int index, Component tooltipTitle, Component tooltipDescription) {
        this.index = index;
        this.tooltipTitle = tooltipTitle;
        this.tooltipComponents = ImmutableList.of(REDSTONE_MODE.withParam(tooltipTitle), tooltipDescription);
    }

    public boolean canMachineWork(Level level, BlockPos pos) {
        if (this == IGNORE) return true;

        boolean isAffectedByRedstone = level.hasNeighborSignal(pos);
        if (this == NEED_REDSTONE) return isAffectedByRedstone;

        return !isAffectedByRedstone;
    }

    public int getIndex() {
        return this.index;
    }

    public void saveToTag(CompoundTag tag) {
        tag.putString(TAG_SAVE_KEY, this.toString());
    }

    public static MachineRedstoneMode loadFromTag(CompoundTag tag) {
        return MachineRedstoneMode.valueOf(tag.getString(TAG_SAVE_KEY));
    }

    public static MachineRedstoneMode valueFromIndex(int index) {
        Preconditions.checkState(index >= 0 && index <= 2);
        return index == 0 ? IGNORE : index == 1 ? NEED_REDSTONE : REDSTONE_PREVENT_MACHINE;
    }

    public Component getTooltipTitle() {
        return tooltipTitle;
    }

    public List<Component> getTooltipComponents() {
        return tooltipComponents;
    }
}

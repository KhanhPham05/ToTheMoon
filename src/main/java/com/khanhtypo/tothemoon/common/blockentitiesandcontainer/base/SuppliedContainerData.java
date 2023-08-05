package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import net.minecraft.world.inventory.ContainerData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;

public final class SuppliedContainerData implements ContainerData {
    private final List<IntSupplier> data;

    public SuppliedContainerData(IntSupplier... dataGetter) {
        this.data = Arrays.stream(dataGetter).toList();
    }

    @Override
    public int get(int pIndex) {
        return this.data.get(pIndex).getAsInt();
    }

    @Override
    public void set(int pIndex, int pValue) {

    }

    @Override
    public int getCount() {
        return this.data.size();
    }
}

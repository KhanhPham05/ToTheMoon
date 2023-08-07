package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base;

import net.minecraft.world.inventory.ContainerData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;

@Deprecated
public final class SuppliedContainerData implements ContainerData {
    private final List<IntSupplier> data;

    public SuppliedContainerData(IntSupplier... dataGetter) {
        this.data = Arrays.stream(dataGetter).collect(LinkedList::new, List::add, List::addAll);
    }

    public void addData(IntSupplier data) {
        this.data.add(data);
    }

    @Override
    public int get(int pIndex) {
        return this.data.get(pIndex).getAsInt();
    }

    @Override
    public void set(int pIndex, int pValue) {
        //this.data.set(pIndex, pValue);
    }

    @Override
    public int getCount() {
        return this.data.size();
    }


}

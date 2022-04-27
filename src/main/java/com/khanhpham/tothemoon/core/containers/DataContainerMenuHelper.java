package com.khanhpham.tothemoon.core.containers;

import net.minecraft.world.inventory.ContainerData;

public interface DataContainerMenuHelper {
    ContainerData getContainerData();

    default int getEnergyBar(int i1, int i2) {
        int i = getContainerData().get(i1);
        int j = getContainerData().get(i2);

        return j != 0 && i != 0 ? i * 146 / j : 0;
    }
}

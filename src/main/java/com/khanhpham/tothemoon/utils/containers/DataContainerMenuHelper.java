package com.khanhpham.tothemoon.utils.containers;

import net.minecraft.world.inventory.ContainerData;

public interface DataContainerMenuHelper {
    ContainerData getContainerData();

    default int getEnergyBar(int i1, int i2) {
        int i = getContainerData().get(i1);
        int j = getContainerData().get(i2);

        return j != 0 && i != 0 ? i * 147 / j : 0;
    }
}

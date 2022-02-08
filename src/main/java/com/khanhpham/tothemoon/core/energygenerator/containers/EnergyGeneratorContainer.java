package com.khanhpham.tothemoon.core.energygenerator.containers;

import com.khanhpham.tothemoon.init.ModContainerTypes;
import com.khanhpham.tothemoon.utils.containers.energycontainer.AbstractEnergyGeneratorContainer;
import com.khanhpham.tothemoon.utils.te.energygenerator.AbstractEnergyGeneratorTileEntity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import org.jetbrains.annotations.Nullable;

public class EnergyGeneratorContainer extends AbstractEnergyGeneratorContainer {
    protected EnergyGeneratorContainer(@Nullable MenuType<?> pMenuType, Container externalContainer, Inventory playerInventory, int pContainerId, ContainerData intData) {
        super(pMenuType, externalContainer, playerInventory, pContainerId, intData);
    }

    public EnergyGeneratorContainer(AbstractEnergyGeneratorTileEntity externalContainer, Inventory inventory, int containerId, ContainerData intData) {
        this(ModContainerTypes.ENERGY_GENERATOR_CONTAINER, externalContainer, inventory, containerId, intData);
    }

    public EnergyGeneratorContainer(int containerId, Inventory inventory) {
        this(ModContainerTypes.ENERGY_GENERATOR_CONTAINER, new SimpleContainer(AbstractEnergyGeneratorTileEntity.INVENTORY_CAPACITY), inventory, containerId, new SimpleContainerData(AbstractEnergyGeneratorTileEntity.CONTAINER_DATA_COUNT));
    }
}

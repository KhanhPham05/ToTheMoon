package com.khanhpham.tothemoon.core.energygenerator.containers;

import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.containers.energycontainer.AbstractEnergyGeneratorMenu;
import com.khanhpham.tothemoon.utils.te.energygenerator.AbstractEnergyGeneratorBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import org.jetbrains.annotations.Nullable;

public class EnergyGeneratorMenu extends AbstractEnergyGeneratorMenu {
    protected EnergyGeneratorMenu(@Nullable MenuType<?> pMenuType, Container externalContainer, Inventory playerInventory, int pContainerId, ContainerData intData) {
        super(pMenuType, externalContainer, playerInventory, pContainerId, intData);
    }

    public EnergyGeneratorMenu(AbstractEnergyGeneratorBlockEntity externalContainer, Inventory inventory, int containerId, ContainerData intData) {
        this(ModMenuTypes.ENERGY_GENERATOR_CONTAINER, externalContainer, inventory, containerId, intData);
    }

    public EnergyGeneratorMenu(int containerId, Inventory inventory) {
        this(ModMenuTypes.ENERGY_GENERATOR_CONTAINER, new SimpleContainer(AbstractEnergyGeneratorBlockEntity.INVENTORY_CAPACITY), inventory, containerId, new SimpleContainerData(AbstractEnergyGeneratorBlockEntity.CONTAINER_DATA_COUNT));
    }
}

package com.khanhpham.ttm.core.containers;

import com.khanhpham.ttm.core.blockentities.energygen.BaseGeneratorEntity;
import com.khanhpham.ttm.core.containers.bases.BaseGeneratorMenuContainer;
import com.khanhpham.ttm.init.ModBlocks;
import com.khanhpham.ttm.init.ModMisc;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.Block;

public class EnergyGenMenu extends BaseGeneratorMenuContainer {
    public EnergyGenMenu(int pContainerId, Inventory inventory, Container container, ContainerData data) {
        super(ModMisc.ENERGY_GEN_MENU, pContainerId, inventory, container, data);
    }

    public EnergyGenMenu(int pContainerId, Inventory inventory) {
        this(pContainerId, inventory, new SimpleContainer(BaseGeneratorEntity.STORAGE_CAPACITY), new SimpleContainerData(BaseGeneratorEntity.DATA_SLOT_CAPACITY));
    }

    @Override
    protected Block getBlockForContainer() {
        return ModBlocks.ENERGY_GEN;
    }
}

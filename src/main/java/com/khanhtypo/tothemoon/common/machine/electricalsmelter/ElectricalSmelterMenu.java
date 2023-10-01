package com.khanhtypo.tothemoon.common.machine.electricalsmelter;

import com.khanhtypo.tothemoon.common.machine.AbstractSingleItemProcessingMachineMenu;
import com.khanhtypo.tothemoon.registration.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

@SuppressWarnings("unchecked")
public class ElectricalSmelterMenu extends AbstractSingleItemProcessingMachineMenu {
    public ElectricalSmelterMenu(int windowId, Inventory playerInventory, ContainerLevelAccess accessor) {
        this(windowId, playerInventory, accessor, new SimpleContainer(1), new SimpleContainer(3), new SimpleContainerData(ElectricalSmelterBlockEntity.DATA_COUNT));
    }

    public ElectricalSmelterMenu(int windowId, Inventory playerInventory, ContainerLevelAccess accessor, Container container, Container upgradeContainer, ContainerData containerData) {
        super(ModMenuTypes.ELECTRICAL_SMELTER, windowId, playerInventory, accessor, container, upgradeContainer, containerData);
    }

    @Override
    protected RecipeType<SmeltingRecipe> getRecipeType() {
        return RecipeType.SMELTING;
    }
}

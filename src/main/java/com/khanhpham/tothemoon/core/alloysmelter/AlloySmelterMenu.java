package com.khanhpham.tothemoon.core.alloysmelter;

import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.containers.BaseMenu;
import com.khanhpham.tothemoon.utils.containers.DataContainerMenuHelper;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import org.jetbrains.annotations.Nullable;


public class AlloySmelterMenu extends BaseMenu implements DataContainerMenuHelper {
    private final ContainerData data;

    protected AlloySmelterMenu(@Nullable MenuType<?> pMenuType, Container externalContainer, Inventory playerInventory, int pContainerId, ContainerData data) {
        super(pMenuType, externalContainer, playerInventory, pContainerId);
        this.data = data;

        addSlot(0, 45, 19);
        addSlot(1, 45, 47);
        addSlot(2, 107, 33);
        addPlayerInventorySlots(8, 95);
    }

    public AlloySmelterMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(AlloySmelterBlockEntity.MENU_SIZE), new SimpleContainerData(AlloySmelterBlockEntity.DATA_CAPACITY));
    }

    public AlloySmelterMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        this(ModMenuTypes.ALLOY_SMELTER, container, playerInventory, containerId, data);
    }


    @Override
    public ContainerData getContainerData() {
        return this.data;
    }

    public int getEnergyBar() {
        return getEnergyBar(2, 3);
    }

    /**
     * @see net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen
     * @see net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
     */
    public int getAlloyingProcess() {
        int i = data.get(0);
        int j = data.get(1);
        return j != 0 && i != 0 ? i * 35 / j : 0;
    }
}

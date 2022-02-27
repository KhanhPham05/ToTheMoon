package com.khanhpham.tothemoon.core.alloysmelter;

import com.khanhpham.tothemoon.ModUtils;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.containers.BaseMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import org.jetbrains.annotations.Nullable;

public class AlloySmelterMenu extends BaseMenu {
    private final ContainerData data;
    public static final ResourceLocation GUI = ModUtils.modLoc("textures/gui/alloy_smelter.png");

    protected AlloySmelterMenu(@Nullable MenuType<?> pMenuType, Container externalContainer, Inventory playerInventory, int pContainerId, ContainerData data) {
        super(pMenuType, externalContainer, playerInventory, pContainerId);
        this.data = data;

        addSlot(0, 45, 19);
        addSlot(1, 45, 47);
        addSlot(2, 107, 33);
        addPlayerInventorySlots(8, 95);
    }

    //UNFINISHED
    //public int getEnergyBar() {}

    public AlloySmelterMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(AlloySmelterBlockEntity.MENU_SIZE), new SimpleContainerData(AlloySmelterBlockEntity.DATA_CAPACITY));
    }

    public AlloySmelterMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        this(ModMenuTypes.ALLOY_SMELTER, container, playerInventory, containerId, data);
    }


}

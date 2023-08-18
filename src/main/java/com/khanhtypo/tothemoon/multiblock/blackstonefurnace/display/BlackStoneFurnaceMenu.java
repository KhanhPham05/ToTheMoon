package com.khanhtypo.tothemoon.multiblock.blackstonefurnace.display;

import com.khanhtypo.tothemoon.client.SlotUtils;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.multiblock.blackstonefurnace.MultiblockBlackStoneFurnace;
import com.khanhtypo.tothemoon.registration.ModBlocks;
import com.khanhtypo.tothemoon.registration.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nonnull;

public class BlackStoneFurnaceMenu extends BaseMenu {
    public static final int CONTAINER_DATA_SIZE = 5;

    public BlackStoneFurnaceMenu(int windowId, Inventory playerInventory, ContainerLevelAccess accessor) {
        this(windowId, playerInventory, accessor, new SimpleContainer(3), new SimpleContainerData(CONTAINER_DATA_SIZE));
    }

    public BlackStoneFurnaceMenu(int windowId, Inventory playerInventory, ContainerLevelAccess accessor, Container furnaceSlot, ContainerData containerData) {
        super(ModMenuTypes.BLACK_STONE_FURNACE, windowId, playerInventory, accessor, ModBlocks.BLACK_STONE_FURNACE_CONTROLLER.get());

        super.addSlot(new Slot(furnaceSlot, 0, 40, 43));
        super.addSlot(SlotUtils.createTakeOnly(furnaceSlot, 1, 146, 43));
        super.addSlot(SlotUtils.createPlaceFilter(furnaceSlot, 2, 170, 72, BlackStoneFurnaceMenu::isBucketOrHandler));

        super.addPlayerInvSlots(22, 95);
        super.addDataSlots(containerData);
    }

    public static boolean isBucketOrHandler(ItemStack itemStack) {
        if (itemStack.is(Items.LAVA_BUCKET)) return true;
        return isHandler(itemStack);
    }

    public static boolean isHandler(ItemStack itemStack) {
        return FluidUtil.getFluidContained(itemStack).map(fluid -> MultiblockBlackStoneFurnace.FLUID_CHECKER.test(0, fluid)).orElse(false);
    }

    @Override
    @Nonnull
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = super.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();

            if (pIndex < 3) {
                if (!super.moveItemStackTo(itemStack1, 3, super.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (isBucketOrHandler(itemStack1)) {
                    if (!super.moveItemStackTo(itemStack1, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!super.moveItemStackTo(itemStack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemStack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else slot.setChanged();

            slot.onTake(pPlayer, itemStack1);
        }

        return itemStack;
    }
}

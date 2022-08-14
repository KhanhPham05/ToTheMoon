package com.khanhpham.tothemoon.core.multiblock.block.brickfurnace;

import com.khanhpham.tothemoon.core.menus.BaseMenu;
import com.khanhpham.tothemoon.core.recipes.HighHeatSmeltingRecipe;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.slot.SlotPlacePredicate;
import com.khanhpham.tothemoon.utils.slot.ResultSlotPredicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class NetherBrickFurnaceControllerMenu extends BaseMenu {
    private final ContainerData data;

    public NetherBrickFurnaceControllerMenu(int pContainerId, Inventory playerInventory, Container externalContainer, ContainerData data) {
        super(ModMenuTypes.NETHER_BRICK_FURNACE, pContainerId, playerInventory, externalContainer);

        //blaze powder slot
        addSlot(new SlotPlacePredicate(externalContainer, 0, 56, 43));
        //input slot
        addSlot(new SlotPlacePredicate(externalContainer, 1, 31, 43, (stack) -> stack.is(Items.BLAZE_POWDER)));
        //result slot
        addSlot(new ResultSlotPredicate(externalContainer, 2, 146, 43));
        //lava bucket slot
        addSlot(new SlotPlacePredicate(externalContainer, 3, 170, 72, stack -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()));

        super.addPlayerInventorySlots(22, 95);

        this.data = data;
        super.addDataSlots(data);
    }

    public NetherBrickFurnaceControllerMenu(int pContainerId, Inventory playerInventory) {
        this(pContainerId, playerInventory, new SimpleContainer(NetherBrickFurnaceControllerBlockEntity.CONTAINER_SIZE), new SimpleContainerData(NetherBrickFurnaceControllerBlockEntity.DATA_COUNT));
    }

    public int getStoredFluid() {
        return this.data.get(0);
    }

    public int getFluidCapacity() {
        return data.get(1);
    }

    public int getFluidAmount() {
        int store = data.get(0);
        int capacity = data.get(1);
        return capacity != 0 ? store * 75 / capacity : 0;
    }

    public int getBlazeFuel() {
        return data.get(4);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(access, pPlayer, ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER.get());
    }

    @NotNull
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();

            if (index == 2) {
                if (!moveItemStackTo(stack1, 4, 40, true))
                    return ItemStack.EMPTY;

                slot.onQuickCraft(stack1, stack);
            } else if (index != 1 && index != 0 && index != 3) {
                if (stack1.is(Items.BLAZE_POWDER)) {
                    if (!moveItemStackTo(stack1, 1, 2, false)) return ItemStack.EMPTY;
                } else if (stack1.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
                    if (!moveItemStackTo(stack1, 3, 4, false)) return ItemStack.EMPTY;
                } else if (!moveItemStackTo(stack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                } else if (index > 3 && index < 31) {
                    if (!moveItemStackTo(stack1, 31, 40, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 31 && index < 40 && !moveItemStackTo(stack1, 4, 31, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(stack1, 4, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (stack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack1.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack1);
        }

        return stack;
    }

    public int getBurningProcess() {
        int time = data.get(2);
        int duration = data.get(3);
        return duration != 0 && time != 0 ? time * 50 / duration : 0;
    }
}

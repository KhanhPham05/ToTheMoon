package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.workbench;

import com.khanhtypo.tothemoon.client.SlotUtils;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicMenu;
import com.khanhtypo.tothemoon.data.ModItemTags;
import com.khanhtypo.tothemoon.registration.ModMenus;
import com.khanhtypo.tothemoon.registration.ModRecipeTypes;
import com.khanhtypo.tothemoon.serverdata.WorkbenchRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Optional;

public class WorkbenchMenu extends BasicMenu {
    private final InvWrapper craftingSlots;
    private final InvWrapper resultSlot;

    public WorkbenchMenu(int windowId, Inventory playerInventory, FriendlyByteBuf buf) {
        super(ModMenus.WORKBENCH, windowId, playerInventory, buf);
        this.craftingSlots = super.simpleHandler(27);
        this.resultSlot = super.simpleHandler(1);
        int index = 0;
        int y = 18;
        for (int i = 0; i < 5; i++) {
            int x = 52;
            for (int j = 0; j < 5; j++) {
                super.addSlot(new SlotItemHandler(this.craftingSlots, index, x, y));
                index++;
                x += 18;
            }
            y += 18;
        }
        super.addSlot(SlotUtils.createPlaceFilter(this.craftingSlots, 25, 21, 36, ModItemTags.TOOL_HAMMERS));
        super.addSlot(new SlotItemHandler(this.craftingSlots, 26, 21, 72 + 1));
        super.addSlot(new SlotItemHandler(this.resultSlot, 0, 156, 54));
        super.addPlayerInv(15, 127);
    }

    private static void updateCraftingPattern(WorkbenchMenu menu, Level level, Player player) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            ItemStack stack = ItemStack.EMPTY;
            Optional<WorkbenchRecipe> workbenchRecipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.WORKBENCH_RECIPE.get(), menu.craftingSlots.getInv(), level);
            if (workbenchRecipe.isPresent()) {
                WorkbenchRecipe r = workbenchRecipe.get();
                ItemStack result = r.assemble(menu.craftingSlots.getInv(), level.registryAccess());
                if (result.isItemEnabled(level.enabledFeatures())) stack = result;
            }

            menu.resultSlot.setStackInSlot(0, stack);
            menu.setRemoteSlot(27, stack);
            serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 27, stack));
        }
    }

    @Override
    public void slotsChanged(Container p_38868_) {
        this.accessor.execute((level, blockPos) -> {
            updateCraftingPattern(this, level, super.playerInventory.player);
        });
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        super.accessor.execute(((level, blockPos) -> {
            super.clearContainer(player, this.craftingSlots.getInv());
        }));
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlot.getInv() && super.canTakeItemForPickAll(stack, slot);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = super.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            if (index == 27) {
                super.accessor.execute((level, pos) -> itemStack1.getItem().onCraftedBy(itemStack1, level, player));
                if (!super.moveItemStackTo(itemStack1, 28, super.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemStack1, itemStack);
            } else if (index >= 28 && index < super.slots.size()) {
                if (itemStack1.is(ModItemTags.TOOL_HAMMERS)) {
                    if (!super.moveItemStackTo(itemStack1, 25, 26, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!super.moveItemStackTo(itemStack1, 0, 27, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!super.moveItemStackTo(itemStack1, 28, super.slots.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else slot.setChanged();

            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack1);
            if (index == 0) {
                player.drop(itemStack1, false);
            }

        }
        return itemStack;
    }
}

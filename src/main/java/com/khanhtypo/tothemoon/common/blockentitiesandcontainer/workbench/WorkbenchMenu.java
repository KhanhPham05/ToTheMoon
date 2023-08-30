package com.khanhtypo.tothemoon.common.blockentitiesandcontainer.workbench;

import com.khanhtypo.tothemoon.client.SlotUtils;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.AlwaysSavedContainer;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.AlwaysSavedResultContainer;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.RecipeContainerMenu;
import com.khanhtypo.tothemoon.data.ModItemTags;
import com.khanhtypo.tothemoon.registration.ModBlocks;
import com.khanhtypo.tothemoon.registration.ModMenuTypes;
import com.khanhtypo.tothemoon.registration.ModRecipeTypes;
import com.khanhtypo.tothemoon.serverdata.WorkbenchRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class WorkbenchMenu extends BaseMenu implements RecipeContainerMenu {
    private final AlwaysSavedContainer craftingSlots;
    private final AlwaysSavedResultContainer resultSlot;

    public WorkbenchMenu(int windowId, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenuTypes.WORKBENCH, windowId, playerInventory, access, ModBlocks.WORKBENCH.get());
        this.craftingSlots = new AlwaysSavedContainer(this, 27);
        this.resultSlot = new AlwaysSavedResultContainer(this);
        int index = 0;
        int y = 18;
        for (int i = 0; i < 5; i++) {
            int x = 52;
            for (int j = 0; j < 5; j++) {
                super.addSlot(new Slot(this.craftingSlots, index, x, y));
                index++;
                x += 18;
            }
            y += 18;
        }
        super.addSlot(SlotUtils.createPlaceFilter(this.craftingSlots, 25, 21, 36, ModItemTags.TOOL_HAMMERS));
        super.addSlot(new Slot(this.craftingSlots, 26, 21, 73));
        super.addSlot(SlotUtils.createTakeOnly(this.resultSlot, this.craftingSlots, 0, 156, 54, this::onResultSlotTaken));
        super.addPlayerInvSlots(15, 127);
        super.addContainerListeners(this.craftingSlots, this.resultSlot);
    }

    private static void updateCraftingPattern(WorkbenchMenu menu, Level level, Player player) {
        if (!level.isClientSide) {
            ServerPlayer serverPlayer = ((ServerPlayer) player);
            ItemStack stack = ItemStack.EMPTY;
            Optional<WorkbenchRecipe> workbenchRecipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.WORKBENCH_RECIPE_TYPE, menu.craftingSlots, level);
            if (workbenchRecipe.isPresent()) {
                WorkbenchRecipe r = workbenchRecipe.get();
                ItemStack result = r.assemble(menu.craftingSlots, level.registryAccess());
                if (result.isItemEnabled(level.enabledFeatures())) stack = result;
            }

            menu.resultSlot.setItem(0, stack);
            menu.setRemoteSlot(27, stack);
            serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 27, stack));
        }
    }

    @Override
    public void slotsChanged(Container p_38868_) {
        super.accessor.execute((level, blockPos) -> updateCraftingPattern(this, level, super.playerInventory.player));
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        super.accessor.execute(((level, blockPos) -> {
            super.clearContainer(player, this.craftingSlots);
        }));
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlot && super.canTakeItemForPickAll(stack, slot);
    }

    private void onResultSlotTaken(Player player, ItemStack resultStack) {
        for (int i = 0; i < this.craftingSlots.getContainerSize(); i++) {
            ItemStack slot = this.craftingSlots.getItem(i);
            if (slot.isDamageableItem()) {
                if (slot.hurt(1, player.level().getRandom(), player instanceof ServerPlayer serverPlayer ? serverPlayer : null)) {
                    int durability = slot.getMaxDamage() - slot.getDamageValue();
                    ItemStack item = durability > 0 ? slot : ItemStack.EMPTY;
                    this.craftingSlots.setItem(i, item);
                    if (item.isEmpty()) {
                        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_BREAK, 1.0f));
                    }
                }
            } else if (slot.hasCraftingRemainingItem()) {
                this.craftingSlots.setItem(i, slot.getCraftingRemainingItem());
            } else this.craftingSlots.setItem(i, this.tryShrinkOrEmpty(slot));
        }
    }

    private ItemStack tryShrinkOrEmpty(ItemStack stack) {
        int stackCount = stack.getCount();
        int shrankCount = stackCount - 1;
        if (shrankCount > 0) {
            stack.setCount(shrankCount);
        }

        return shrankCount == 0 ? ItemStack.EMPTY : stack;
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

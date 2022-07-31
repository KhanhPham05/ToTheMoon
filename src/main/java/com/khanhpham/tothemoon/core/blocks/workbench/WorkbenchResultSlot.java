package com.khanhpham.tothemoon.core.blocks.workbench;

import com.khanhpham.tothemoon.core.menus.containers.WorkbenchCraftingContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class WorkbenchResultSlot extends Slot {
    private final WorkbenchCraftingContainer craftingContainer;
    private final WorkbenchMenu menu;

    public WorkbenchResultSlot(WorkbenchMenu menu, Container resultContainer, int index, int x, int y) {
        super(resultContainer, index, x, y);
        this.craftingContainer = menu.workbenchContainer;
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(ItemStack pStack) {
        return false;
    }

    @Override
    public void onTake(Player pPlayer, ItemStack pStack) {
        this.craftingContainer.processCrafting(pPlayer, pStack);
        super.setChanged();
    }

    @Override
    public void onQuickCraft(ItemStack pOldStack, ItemStack pNewStack) {
        this.craftingContainer.processCrafting(this.menu.player, pNewStack);
        WorkbenchMenu.craftingSlotsChanged(menu);
        super.onQuickCraft(pOldStack, pNewStack);
    }
}
package com.khanhpham.tothemoon.core.menus.containers;

import com.khanhpham.tothemoon.core.blocks.workbench.WorkbenchMenu;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.LinkedList;
import java.util.List;

public class WorkbenchCraftingContainer extends SimpleContainer implements StackedContentsCompatible {
    public static final int CONTAINER_SIZE = 27;
    public final List<Slot> slots = new LinkedList<>();
    private final WorkbenchMenu workbenchMenu;

    public WorkbenchCraftingContainer(WorkbenchMenu workbenchMenu) {
        super(CONTAINER_SIZE);
        this.workbenchMenu = workbenchMenu;
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        ItemStack itemStack = super.removeItem(pIndex, pCount);
        if (notEmpty(itemStack)) this.workbenchMenu.slotsChanged(this);
        return itemStack;
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        super.setItem(pIndex, pStack);
        this.workbenchMenu.slotsChanged(this);
    }

    public void processCrafting(Player player, ItemStack takenResult) {
        if (!takenResult.isEmpty()) {
            this.workbenchMenu.slots.get(1).getItem().hurt(1, ModUtils.RANDOM, null);

            for (int i = 2; i < 28; i++) {
                this.workbenchMenu.slots.get(i).remove(takenResult.getCount());
            }

            if (getSlot(1).getDamageValue() >= getSlot(1).getMaxDamage()) {
                this.workbenchMenu.slots.get(1).set(ItemStack.EMPTY);
                player.playSound(SoundEvents.ITEM_BREAK, 1f, 1f);
            }
        }
    }

    private ItemStack getSlot(int index) {
        return this.workbenchMenu.slots.get(index).getItem();
    }

    private boolean notEmpty(ItemStack stack) {
        return !stack.isEmpty();
    }

    public boolean isRecipeMatches(Ingredient hammer, Ingredient extra, NonNullList<Ingredient> ingredients) {
        boolean hammerMatches = hammer.test(this.items.get(0));
        boolean extraMatches = extra.test(this.items.get(1));

        int index = 2;

        for (Ingredient ingredient : ingredients) {
            if (!ingredient.test(this.items.get(index))) {
                return false;
            } else {
                index++;
            }
        }

        return hammerMatches && extraMatches;
    }

    public void addCraftingGrid(WorkbenchMenu menu) {
        int index = 2;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                menu.addSlot(new Slot(this, index, 57 + j * 18, 22 + i * 18));
                index++;
            }
        }
    }

    @Override
    public void setChanged() {
        WorkbenchMenu.craftingSlotsChanged(this.workbenchMenu);
        super.setChanged();
    }
}

package com.khanhpham.tothemoon.api.slots;

import com.khanhpham.tothemoon.util.ModUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

/**
 * @see net.minecraft.inventory.container.AbstractFurnaceContainer
 */
public class EnergyFuelSlot extends Slot {
    public EnergyFuelSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return ModUtils.getBurnTime(stack) > 0;
    }
}

package com.khanhtypo.tothemoon.common.item;

import com.khanhtypo.tothemoon.registration.elements.ItemObject;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class ToolItem extends ItemObject<TieredItem> {
    public ToolItem(String name, Supplier<TieredItem> itemSupplier) {
        super(name, itemSupplier);
    }

    public Tier getTier() {
        return this.get().getTier();
    }

    public Ingredient getIngredient() {
        return this.getTier().getRepairIngredient();
    }
}

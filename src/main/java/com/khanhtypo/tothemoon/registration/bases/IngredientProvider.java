package com.khanhtypo.tothemoon.registration.bases;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public interface IngredientProvider extends ItemStackSupplier {
    default Ingredient toIngredient(int amount) {
        return Ingredient.of(this.asStack(amount));
    }

    default Ingredient toIngredient() {
        return toIngredient(1);
    }

    default InventoryChangeTrigger.TriggerInstance trigger() {
        return InventoryChangeTrigger.TriggerInstance.hasItems(this.asItem());
    }

    default ItemLike getParent() {
        return this;
    }

    ResourceLocation getId();
}

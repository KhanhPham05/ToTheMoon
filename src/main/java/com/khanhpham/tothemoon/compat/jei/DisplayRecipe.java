package com.khanhpham.tothemoon.compat.jei;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public interface DisplayRecipe<C extends Container> extends Recipe<C> {
    NonNullList<Ingredient> getIngredients();

    @Override
    default boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    };
}

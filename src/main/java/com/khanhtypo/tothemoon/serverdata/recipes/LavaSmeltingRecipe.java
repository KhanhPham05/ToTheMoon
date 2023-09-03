package com.khanhtypo.tothemoon.serverdata.recipes;

import com.khanhtypo.tothemoon.registration.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class LavaSmeltingRecipe extends BaseSingleIngredientRecipe<Container> {
    private final int smeltingTick;

    public LavaSmeltingRecipe(Ingredient ingredient, ItemStack result, int smeltingTick, ResourceLocation recipeId) {
        super(ingredient, result, recipeId, ModRecipeTypes.LAVA_SMELTING);
        this.smeltingTick = smeltingTick;
    }

    public int getSmeltingTick() {
        return smeltingTick;
    }
}

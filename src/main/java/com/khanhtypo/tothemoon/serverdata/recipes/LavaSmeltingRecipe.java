package com.khanhtypo.tothemoon.serverdata.recipes;

import com.khanhtypo.tothemoon.registration.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class LavaSmeltingRecipe extends AbstractProcessingSingleItemRecipe<LavaSmeltingRecipe> {
    public LavaSmeltingRecipe(Ingredient ingredient, ItemStack result, int smeltingTick, ResourceLocation recipeId) {
        super(ingredient, result, smeltingTick, recipeId, ModRecipeTypes.LAVA_SMELTING);
    }
}

package com.khanhtypo.tothemoon.data.recipebuilders;

import com.google.gson.JsonObject;
import com.khanhtypo.tothemoon.registration.ModRecipeTypes;
import com.khanhtypo.tothemoon.serverdata.recipes.RecipeTypeObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class AnvilSmashingRecipeBuilder extends BaseSingleIngredientRecipeBuilder{

    public AnvilSmashingRecipeBuilder(ItemStack result, Ingredient ingredient) {
        super(result, ingredient);
    }

    public AnvilSmashingRecipeBuilder(ItemLike itemLike, Ingredient ingredient) {
        super(itemLike, ingredient);
    }

    public AnvilSmashingRecipeBuilder(ItemLike itemLike, int count, Ingredient ingredient) {
        super(itemLike, count, ingredient);
    }

    @Override
    protected RecipeTypeObject<?> getRecipeType() {
        return ModRecipeTypes.ANVIL_SMASHING;
    }
}

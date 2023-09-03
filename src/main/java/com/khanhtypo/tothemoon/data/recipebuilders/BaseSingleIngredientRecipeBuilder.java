package com.khanhtypo.tothemoon.data.recipebuilders;

import com.google.gson.JsonObject;
import com.khanhtypo.tothemoon.utls.JsonUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public abstract class BaseSingleIngredientRecipeBuilder extends BaseRecipeBuilder {
    protected final Ingredient ingredient;

    public BaseSingleIngredientRecipeBuilder(ItemStack result, Ingredient ingredient) {
        super(result);
        this.ingredient = ingredient;
    }

    public BaseSingleIngredientRecipeBuilder(ItemLike itemLike, Ingredient ingredient) {
        this(itemLike, 1, ingredient);
    }

    public BaseSingleIngredientRecipeBuilder(ItemLike itemLike, int count, Ingredient ingredient) {
        this(new ItemStack(itemLike, count), ingredient);
    }

    @Override
    protected void writeToJson(JsonObject writer) {
        JsonUtils.putIngredientIfNonNull(writer, "ingredient", this.ingredient);
    }
}

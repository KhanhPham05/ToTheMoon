package com.khanhtypo.tothemoon.data.recipebuilders;

import com.google.gson.JsonObject;
import com.khanhtypo.tothemoon.registration.ModRecipeTypes;
import com.khanhtypo.tothemoon.serverdata.recipes.RecipeTypeObject;
import com.khanhtypo.tothemoon.serverdata.recipes.serializers.LavaSmeltingRecipeSerializer;
import com.khanhtypo.tothemoon.utls.JsonUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class LavaSmeltingRecipeBuilder extends BaseSingleIngredientRecipeBuilder {
    private int smeltingTick = LavaSmeltingRecipeSerializer.DEFAULT_SMELTING_TICK;

    public LavaSmeltingRecipeBuilder(ItemStack result, Ingredient ingredient) {
        super(result, ingredient);
    }

    public LavaSmeltingRecipeBuilder(ItemLike itemLike, Ingredient ingredient) {
        super(itemLike, ingredient);
    }

    public LavaSmeltingRecipeBuilder(ItemLike itemLike, int count, Ingredient ingredient) {
        super(itemLike, count, ingredient);
    }


    @Override
    protected RecipeTypeObject<?> getRecipeType() {
        return ModRecipeTypes.LAVA_SMELTING;
    }

    public LavaSmeltingRecipeBuilder setSmeltingTick(int smeltingTick) {
        this.smeltingTick = smeltingTick;
        return this;
    }

    @Override
    protected void writeToJson(JsonObject writer) {
        super.writeToJson(writer);
        JsonUtils.putIntIfNotDefault(writer, "smeltingTick", this.smeltingTick, LavaSmeltingRecipeSerializer.DEFAULT_SMELTING_TICK);
    }
}

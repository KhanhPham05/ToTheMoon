package com.khanhpham.tothemoon.core.recipes;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.compat.jei.DisplayRecipe;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class HighHeatSmeltingRecipe implements DisplayRecipe<Container> {
    public static final RecipeType<HighHeatSmeltingRecipe> RECIPE_TYPE = ModUtils.registerRecipeType(ModUtils.modLoc("high_heat_smelting"));
    private final Ingredient ingredient;
    private final ItemStack result;
    private final ResourceLocation recipeId;

    public HighHeatSmeltingRecipe(Ingredient ingredient, ItemStack result, ResourceLocation recipeId) {
        this.ingredient = ingredient;
        this.result = result;
        this.recipeId = recipeId;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return ingredient.test(pContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(Container pContainer) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BRICK_FURNACE_SMELTING;
    }

    @Override
    public RecipeType<?> getType() {
        return RECIPE_TYPE;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.ingredient);
        return list;
    }

    public static final class Serializer extends SimpleRecipeSerializer<HighHeatSmeltingRecipe> {
        @Override
        protected String getRecipeName() {
            return "high_heat_smelting";
        }

        @Override
        public HighHeatSmeltingRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.isArrayNode(json, JsonNames.INGREDIENT) ? GsonHelper.getAsJsonArray(json, JsonNames.INGREDIENT) : GsonHelper.getAsJsonObject(json, JsonNames.INGREDIENT));
            ItemStack result = stackFromJson(json);
            return new HighHeatSmeltingRecipe(ingredient, result, id);
        }

        @Override
        public HighHeatSmeltingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new HighHeatSmeltingRecipe(ingredient, result, id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, HighHeatSmeltingRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}

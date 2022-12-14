package com.khanhpham.tothemoon.core.recipes;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.compat.jei.DisplayRecipe;
import com.khanhpham.tothemoon.core.blocks.machine.crusher.MutableContainer;
import com.khanhpham.tothemoon.core.recipes.type.SingleProcessRecipeType;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class SingleProcessRecipe implements DisplayRecipe<MutableContainer> {
    private final Ingredient ingredient;
    private final ItemStack result;
    private final NonNullList<Ingredient> ingredients;
    private final ResourceLocation recipeId;

    public SingleProcessRecipe(ResourceLocation recipeId, Ingredient ingredient, ItemStack result) {
        this.ingredient = ingredient;
        this.result = result;
        this.ingredients = NonNullList.withSize(1, Ingredient.EMPTY);
        this.recipeId = recipeId;

        this.ingredients.set(0, this.ingredient);
    }

    @Override
    public final boolean matches(MutableContainer pContainer, Level pLevel) {
        return this.ingredient.test(pContainer.getItem(0));
    }

    @Override
    public final ItemStack assemble(MutableContainer pContainer) {
        return this.result.copy();
    }

    @Override
    public final ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public final NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public final ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public abstract SingleProcessRecipeType getType();

    @FunctionalInterface
    protected interface RecipeWriter {
        SingleProcessRecipe createRecipe(ResourceLocation resourceLocation, Ingredient ingredient, ItemStack result);
    }

    public static abstract class Serializer extends SimpleRecipeSerializer<SingleProcessRecipe> {

        private final RecipeWriter writer;

        protected Serializer(RecipeWriter writer) {
            this.writer = writer;
        }

        @Override
        public SingleProcessRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient ingredient = super.getShortenIngredient(pSerializedRecipe, "ingredient");
            ItemStack result = super.resultFromJson(pSerializedRecipe);
            return this.writer.createRecipe(pRecipeId, ingredient, result);
        }

        @Override
        public @Nullable SingleProcessRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return this.writer.createRecipe(pRecipeId, Ingredient.fromNetwork(pBuffer), pBuffer.readItem());
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SingleProcessRecipe pRecipe) {
            pRecipe.ingredient.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
        }
    }
}

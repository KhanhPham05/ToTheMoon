package com.khanhtypo.tothemoon.serverdata.recipes;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public abstract class BaseRecipe<T extends Container> implements Recipe<T> {
    public final RecipeTypeObject<? extends BaseRecipe<T>> recipeTypeObject;
    private final NonNullList<Ingredient> ingredients;
    public final ItemStack result;
    private final ResourceLocation recipeId;

    protected BaseRecipe(NonNullList<Ingredient> ingredients, ItemStack result, ResourceLocation recipeId, RecipeTypeObject<? extends BaseRecipe<T>> recipeTypeObject) {
        this.ingredients = ingredients;
        this.result = result;
        this.recipeId = recipeId;
        this.recipeTypeObject = recipeTypeObject;
    }

    @Override
    public boolean matches(T p_44002_, Level p_44003_) {
        for (int i = 0; i < Math.min(p_44002_.getContainerSize(), this.ingredients.size()); i++) {
            if (!this.ingredients.get(i).test(p_44002_.getItem(i))) return false;
        }

        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public ItemStack assemble(T p_44001_, @Nullable RegistryAccess p_267165_) {
        return this.copyResult();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(@Nullable RegistryAccess p_267052_) {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return this.recipeTypeObject.getSerializer();
    }

    @Override
    public RecipeType<?> getType() {
        return this.recipeTypeObject.get();
    }

    protected boolean test(int index, ItemStack toTest) {
        return this.ingredients.get(index).test(toTest);
    }

    public ItemStack copyResult() {
        return this.result.copy();
    }
}

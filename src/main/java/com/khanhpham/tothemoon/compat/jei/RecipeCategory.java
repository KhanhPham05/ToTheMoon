package com.khanhpham.tothemoon.compat.jei;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class RecipeCategory<T extends DisplayRecipe<? extends Container>> implements IRecipeCategory<T> {
    protected final IGuiHelper guiHelper;

    public RecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
    }


    public static void addOutput(IRecipeLayoutBuilder builder, Recipe<?> recipe, int x, int y) {
        addOutput(builder, recipe.getResultItem(), x, y);
    }

    public static <R extends Recipe<? extends Container>> void addInput(IRecipeLayoutBuilder builder, R recipe, int ingredientIndex, int x, int y) {
        builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(recipe.getIngredients().get(ingredientIndex));
    }

    public static void addInput(IRecipeLayoutBuilder builder, Ingredient ingredient, int x, int y) {
        builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(ingredient);
    }

    public static void addOutput(IRecipeLayoutBuilder builder, ItemStack output, int x, int y) {
        builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addItemStack(output);
    }

    public abstract ItemStack getCatalystIcon();

    @Override
    public abstract RecipeType<T> getRecipeType();
    @Override
    public final IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, this.getCatalystIcon());
    }

    public final <C extends Container, R extends Recipe<C>> List<R> getActualCraftingRecipes(RecipeManager recipeManager, net.minecraft.world.item.crafting.RecipeType<R> recipeType) {
        return recipeManager.getAllRecipesFor(recipeType);
    }

    public abstract void registerRecipes(IRecipeRegistration registration, RecipeManager manager);

    public abstract void setRecipeLayout(IRecipeLayoutBuilder builder, T recipe);

    @Override
    public final void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
        this.setRecipeLayout(builder, recipe);
    }

    public abstract void addShowRecipeZone(IGuiHandlerRegistration registration);

    public void addRecipeTransfer(IRecipeTransferRegistration registration) {
    }

    protected ResourceLocation makeLocation(String nameWithPng) {
        return ModUtils.modLoc("textures/jei/" + nameWithPng);
    }

    protected IDrawable makeBackground(String textureName, int width, int height) {
        return this.guiHelper.createDrawable(this.makeLocation(textureName.contains(".png") ? textureName : textureName.concat(".png")), 0, 0, width, height);
    }
}

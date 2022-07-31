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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings({"removal", "unchecked"})
public abstract class RecipeCategory<T extends Recipe<?>> implements IRecipeCategory<T> {
    protected final IGuiHelper guiHelper;

    public RecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
    }

    public abstract ItemStack getCatalystIcon();

    @Override
    public final ResourceLocation getUid() {
        return this.getRecipeType().getUid();
    }

    @Override
    public final Class<? extends T> getRecipeClass() {
        return this.getRecipeType().getRecipeClass();
    }

    @Override
    public abstract RecipeType<T> getRecipeType();

    @Override
    public final IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, this.getCatalystIcon());
    }

    public abstract net.minecraft.world.item.crafting.RecipeType<T> getActualRecipeType();

    @Deprecated
    public final List<T> getActualCraftingRecipes(RecipeManager recipeManager) {
        var recipes = recipeManager.getRecipes().parallelStream().filter(recipe -> recipe.getType().equals(this.getActualRecipeType())).map(recipe -> (T) recipe).toList();
        ModUtils.log("{} recipes of {} . category : {}", recipes.size(), getActualRecipeType(), getRecipeType());
        return recipes;
    }

    public abstract void registerRecipes(IRecipeRegistration registration, RecipeManager manager);
    //{
        //registration.addRecipes(this.getRecipeType(), this.getActualCraftingRecipes(manager));
    //}

    @Override
    public abstract void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses);

    protected void addInput(IRecipeLayoutBuilder builder, T recipe, int ingredientIndex, int x, int y) {
        builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(recipe.getIngredients().get(ingredientIndex));
    }

    protected void addOutput(IRecipeLayoutBuilder builder, ItemStack output, int x, int y) {
        builder.addSlot(RecipeIngredientRole.OUTPUT, x, y).addItemStack(output);
    }

    public abstract void addShowRecipeZone(IGuiHandlerRegistration registration);

    public void addRecipeTransfer(IRecipeTransferRegistration registration) {
    }

    protected ResourceLocation makeLocation(String nameWithPng) {
        return ModUtils.modLoc("textures/jei/" + nameWithPng);
    }
}

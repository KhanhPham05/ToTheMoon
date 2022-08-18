package com.khanhpham.tothemoon.compat.jei;

import com.khanhpham.tothemoon.compat.jei.recipecategories.HighHeatSmeltingCategory;
import com.khanhpham.tothemoon.compat.jei.recipecategories.WorkbenchCraftingCategory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class RecipeCategoryManager {
    private final Set<RecipeCategory<?>> recipeCategories = new HashSet<>();

    RecipeCategoryManager() {
    }

    private <T extends DisplayRecipe<?>, R extends RecipeCategory<T>> void register(IRecipeCategoryRegistration recipeRegistration, IGuiHelper helper, Class<R> categoryClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<R> constructor = categoryClass.getConstructor(IGuiHelper.class);
        R instance = constructor.newInstance(helper);
        this.recipeCategories.add(instance);
        recipeRegistration.addRecipeCategories(instance);
    }

    public void registerCatalysts(IRecipeCatalystRegistration registration) {
        for (RecipeCategory<?> category : recipeCategories) {
            registration.addRecipeCatalyst(category.getCatalystIcon(), category.getRecipeType());
        }
    }

    public void registerRecipes(IRecipeRegistration registration, RecipeManager manager) {
        for (RecipeCategory<?> recipeCategory : recipeCategories) {
            recipeCategory.registerRecipes(registration, manager);
        }
    }

    public void registerAllCategories(IRecipeCategoryRegistration registration, IGuiHelper helper) {
        //registration.addRecipeCategories(this.addCategory(new WorkbenchCraftingCategory(helper)));
        try {
            this.register(registration, helper, WorkbenchCraftingCategory.class);
            this.register(registration, helper, HighHeatSmeltingCategory.class);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void registerGuiHandler(IGuiHandlerRegistration registration) {
        for (RecipeCategory<?> recipeCategory : recipeCategories) {
            recipeCategory.addShowRecipeZone(registration);
        }
    }

    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        for (RecipeCategory<?> recipeCategory : this.recipeCategories) {
            recipeCategory.addRecipeTransfer(registration);
        }
    }
}

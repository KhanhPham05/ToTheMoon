package com.khanhpham.tothemoon.compat.jei;

import com.khanhpham.tothemoon.compat.jei.recipecategories.WorkbenchCraftingCategory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.HashSet;
import java.util.Set;

public class RecipeCategoryManager {
    private final Set<RecipeCategory<?>> recipeCategories = new HashSet<>();

    RecipeCategoryManager() {
    }

    private <T extends Recipe<?>> RecipeCategory<T> addCategory(RecipeCategory<T> recipeCategory) {
        this.recipeCategories.add(recipeCategory);
        return recipeCategory;
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
        registration.addRecipeCategories(this.addCategory(new WorkbenchCraftingCategory(helper)));
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

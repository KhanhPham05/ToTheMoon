package com.khanhpham.tothemoon.compat.jei.recipecategories;

import com.khanhpham.tothemoon.compat.jei.RecipeCategory;
import com.khanhpham.tothemoon.core.blocks.workbench.WorkbenchMenu;
import com.khanhpham.tothemoon.core.blocks.workbench.WorkbenchScreen;
import com.khanhpham.tothemoon.core.recipes.WorkbenchCraftingRecipe;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

public class WorkbenchCraftingCategory extends RecipeCategory<WorkbenchCraftingRecipe> {
    public static final ResourceLocation CATEGORY_ID = ModUtils.modLoc("workbench_crafting_category");
    public static final RecipeType<WorkbenchCraftingRecipe> RECIPE_CATEGORY_TYPE = new RecipeType<>(CATEGORY_ID, WorkbenchCraftingRecipe.class);
    public static final ItemStack ICON = new ItemStack(ModBlocks.WORKBENCH.get());

    public WorkbenchCraftingCategory(IGuiHelper helper) {
        super(helper);
        ModUtils.log("Registering Workbench Crafting Recipe");
    }

    @Override
    public ItemStack getCatalystIcon() {
        return ICON;
    }

    @Override
    public RecipeType<WorkbenchCraftingRecipe> getRecipeType() {
        return RECIPE_CATEGORY_TYPE;
    }

    @Override
    public Component getTitle() {
        return ModLanguage.JEI_WORKBENCH_CRAFTING;
    }

    @Override
    public IDrawable getBackground() {
        return super.makeBackground("workbench_crafting.png", 159, 98);
    }

    @Override
    public void setRecipeLayout(IRecipeLayoutBuilder builder, WorkbenchCraftingRecipe recipe) {
        super.addInput(builder, recipe, 0, 2, 23);
        super.addInput(builder, recipe, 1, 2, 60);

        int index = 2;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                super.addInput(builder, recipe, index, 32 + j * 18 + 1, 5 + i * 18 + 1);
                index++;
            }
        }

        super.addOutput(builder, recipe.getResultItem(), 137, 41);
    }

    @Override
    public void addShowRecipeZone(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(WorkbenchScreen.class, 155, 83, 20, 18, RECIPE_CATEGORY_TYPE);
    }

    @Override
    public void addRecipeTransfer(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(WorkbenchMenu.class, RECIPE_CATEGORY_TYPE, 1, 27, 28, 36);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration, RecipeManager manager) {
        registration.addRecipes(RECIPE_CATEGORY_TYPE, manager.getAllRecipesFor(WorkbenchCraftingRecipe.RECIPE_TYPE));
    }
}

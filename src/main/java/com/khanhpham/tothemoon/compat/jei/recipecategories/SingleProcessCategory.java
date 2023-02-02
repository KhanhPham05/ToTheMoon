package com.khanhpham.tothemoon.compat.jei.recipecategories;

import com.khanhpham.tothemoon.compat.jei.RecipeCategory;
import com.khanhpham.tothemoon.core.processes.single.SingleProcessMenuScreen;
import com.khanhpham.tothemoon.core.recipes.SingleProcessRecipe;
import com.khanhpham.tothemoon.core.recipes.type.SingleProcessRecipeType;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

public class SingleProcessCategory extends RecipeCategory<SingleProcessRecipe> {
    private final SingleProcessRecipeType recipeType;
    private final RecipeType<SingleProcessRecipe> displayRecipeType;
    private final IDrawable bg;
    private final IDrawableAnimated drawableAnimated;

    public SingleProcessCategory(IGuiHelper guiHelper, SingleProcessRecipeType recipeType) {
        super(guiHelper);
        ModUtils.log("Registering JEI category for [{}]", recipeType.location.toString());
        this.recipeType = recipeType;
        this.displayRecipeType = new RecipeType<>(recipeType.location, SingleProcessRecipe.class);
        this.bg = super.makeBackground("single_process", 88, 26);
        this.drawableAnimated = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(super.makeLocation("single_process.png"), 0, 26, 32, 9), 100, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void draw(SingleProcessRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        drawableAnimated.draw(stack, 24, 8);
    }

    @Override
    public ItemStack getCatalystIcon() {
        return this.recipeType.getCatalystIcon();
    }

    @Override
    public RecipeType<SingleProcessRecipe> getRecipeType() {
        return this.displayRecipeType;
    }

    @Override
    public Component getTitle() {
        return this.recipeType.getDisplayName();
    }

    @Override
    public IDrawable getBackground() {
        return this.bg;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration, RecipeManager manager) {
        registration.addRecipes(this.getRecipeType(), super.getActualCraftingRecipes(manager, this.recipeType));
    }

    @Override
    public void setRecipeLayout(IRecipeLayoutBuilder builder, SingleProcessRecipe recipe) {
        addInput(builder, recipe, 0, 1, 5);
        addOutput(builder, recipe, 67, 5);
    }

    @Override
    public void addShowRecipeZone(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SingleProcessMenuScreen.class, 0, 133, 22, 22, this.getRecipeType());
    }
}

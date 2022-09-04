package com.khanhpham.tothemoon.compat.jei.recipecategories;

import com.khanhpham.tothemoon.compat.jei.RecipeCategory;
import com.khanhpham.tothemoon.core.abstracts.BaseMenuScreen;
import com.khanhpham.tothemoon.core.blocks.machines.oreprocessor.OreProcessorScreen;
import com.khanhpham.tothemoon.core.recipes.OreProcessingRecipe;
import com.khanhpham.tothemoon.core.recipes.elements.ChancedResult;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.init.ModBlocks;
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
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

public class OreProcessingCategory extends RecipeCategory<OreProcessingRecipe> {
    public static final RecipeType<OreProcessingRecipe> RECIPE_TYPE = new RecipeType<>(ModUtils.modLoc("ore_processing"), OreProcessingRecipe.class);
    private final IDrawableAnimated processBar;

    public OreProcessingCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.processBar = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(super.makeLocation("ore_processing.png"), 33, 26, 32, 9), 50, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ItemStack getCatalystIcon() {
        return new ItemStack(ModBlocks.ORE_PROCESSOR.get());
    }

    @Override
    public RecipeType<OreProcessingRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return ModLanguage.ORE_PROCESSING;
    }

    @Override
    public IDrawable getBackground() {
        return super.makeBackground("ore_processing", 139, 26);
    }

    @Override
    public void draw(OreProcessingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        if (recipe.getExtraResult() != ChancedResult.NO_EXTRA) {
            Minecraft.getInstance().font.draw(stack, recipe.getExtraResult().getChance() + "%", 123, 13, BaseMenuScreen.blackFontColor);
        }
        this.processBar.draw(stack, 33, 8);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration, RecipeManager manager) {
        registration.addRecipes(RECIPE_TYPE, manager.getAllRecipesFor(OreProcessingRecipe.RECIPE_TYPE));
    }

    @Override
    public void setRecipeLayout(IRecipeLayoutBuilder builder, OreProcessingRecipe recipe) {
        addInput(builder, recipe, 0, 5, 5);
        addOutput(builder, recipe, 77, 5);
        if (recipe.getExtraResult() != ChancedResult.NO_EXTRA)
            addOutput(builder, recipe.getExtraResult().getRenderableItem(), 103, 9);
    }

    @Override
    public void addShowRecipeZone(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(OreProcessorScreen.class, 72, 36, 32, 9, RECIPE_TYPE);
    }
}

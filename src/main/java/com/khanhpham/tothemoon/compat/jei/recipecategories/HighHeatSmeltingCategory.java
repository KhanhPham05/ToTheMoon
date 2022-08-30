package com.khanhpham.tothemoon.compat.jei.recipecategories;

import com.khanhpham.tothemoon.compat.jei.RecipeCategory;
import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceControllerMenu;
import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceControllerScreen;
import com.khanhpham.tothemoon.core.recipes.HighHeatSmeltingRecipe;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;

public class HighHeatSmeltingCategory extends RecipeCategory<HighHeatSmeltingRecipe> {
    public static final ItemStack ICON = new ItemStack(ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER.get());
    public static final ResourceLocation CATEGORY_ID = ModUtils.modLoc("high_heat_smelting_category");
    public static final RecipeType<HighHeatSmeltingRecipe> RECIPE_TYPE = new RecipeType<>(CATEGORY_ID, HighHeatSmeltingRecipe.class);

    public HighHeatSmeltingCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        ModUtils.log("Registering High Heat Smelting");
    }

    @Override
    public ItemStack getCatalystIcon() {
        return ICON;
    }

    @Override
    public RecipeType<HighHeatSmeltingRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return ModLanguage.JEI_HIGH_HEAT_SMELTING;
    }

    @Override
    public IDrawable getBackground() {
        return super.makeBackground("nether_brick_furnace", 165, 77);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration, RecipeManager manager) {
        registration.addRecipes(RECIPE_TYPE, super.getActualCraftingRecipes(manager, HighHeatSmeltingRecipe.RECIPE_TYPE));
    }

    @Override
    public void setRecipeLayout(IRecipeLayoutBuilder builder, HighHeatSmeltingRecipe recipe) {
        addInput(builder, recipe, 0, 26, 31);
        addInput(builder, Ingredient.of(Items.BLAZE_POWDER), 1, 31);
        addOutput(builder, recipe, 116, 31);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 159, 1).setFluidRenderer(1000, false, 5, 75).addIngredient(ForgeTypes.FLUID_STACK, new FluidStack(Fluids.LAVA, FluidAttributes.BUCKET_VOLUME));
    }

    @Override
    public void addShowRecipeZone(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(NetherBrickFurnaceControllerScreen.class, 84, 43, 50, 15, RECIPE_TYPE);
    }

    @Override
    public void addRecipeTransfer(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(NetherBrickFurnaceControllerMenu.class, RECIPE_TYPE, 0,2,4, 36);
    }
}

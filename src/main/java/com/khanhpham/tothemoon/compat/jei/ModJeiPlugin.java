package com.khanhpham.tothemoon.compat.jei;

import com.khanhpham.tothemoon.core.recipes.AlloySmeltingRecipe;
import com.khanhpham.tothemoon.core.recipes.metalpressing.MetalPressingRecipe;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@JeiPlugin
@SuppressWarnings({"removal", "unused"})
public class ModJeiPlugin implements IModPlugin {
    public static final ResourceLocation PLUGIN_ID = ModUtils.modLoc("jei_compat_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(MetalPressRecipeCategory.ICON, MetalPressRecipeCategory.CATEGORY_ID);
        registration.addRecipeCatalyst(AlloySmelterRecipeCategory.ICON, AlloySmelterRecipeCategory.CATEGORY_ID);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(MetalPressRecipeCategory.RECIPE_TYPE, manager.getRecipes().parallelStream().filter(recipe -> recipe.getType().equals(MetalPressingRecipe.RECIPE_TYPE)).map(r -> (MetalPressingRecipe) r).toList());
        registration.addRecipes(AlloySmelterRecipeCategory.RECIPE_TYPE, manager.getRecipes().parallelStream().filter(recipe -> recipe.getType().equals(AlloySmeltingRecipe.RECIPE_TYPE)).map(r -> (AlloySmeltingRecipe) r).toList());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        ModUtils.log("Registering TTM JEI Recipes");
        registration.addRecipeCategories(new MetalPressRecipeCategory(helper));
        registration.addRecipeCategories(new AlloySmelterRecipeCategory(helper));
    }

    public static final class AlloySmelterRecipeCategory implements IRecipeCategory<AlloySmeltingRecipe> {

        public static final ItemStack ICON = new ItemStack(ModBlocks.ALLOY_SMELTER.get());
        public static final ResourceLocation TEXTURE = ModUtils.modLoc("textures/jei/alloy_smelter.png");
        public static final ResourceLocation CATEGORY_ID = ModUtils.modLoc("alloy_smelting_category");
        private static final RecipeType<AlloySmeltingRecipe> RECIPE_TYPE = new RecipeType<>(CATEGORY_ID, AlloySmeltingRecipe.class);
        private final IDrawable background;
        private final IDrawableAnimated processArrow;
        private final IDrawable icon;

        public AlloySmelterRecipeCategory(IGuiHelper helper) {
            this.background = helper.createDrawable(TEXTURE, 0, 0, 114, 69);

            IDrawableStatic staticArrow = helper.createDrawable(TEXTURE, 149, 0, 35, 21);
            this.processArrow = helper.createAnimatedDrawable(staticArrow, 100, IDrawableAnimated.StartDirection.LEFT, false);
            this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ICON);
        }

        @Override
        public void draw(AlloySmeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
            this.processArrow.draw(stack, 36, 23);
        }

        @Override
        public Component getTitle() {
            return ModLanguage.ALLOY_SMELTER_RECIPE_CATEGORY;
        }

        @Override
        public RecipeType<AlloySmeltingRecipe> getRecipeType() {
            return RECIPE_TYPE;
        }

        @Override
        public IDrawable getBackground() {
            return background;
        }

        @Override
        public IDrawable getIcon() {
            return icon;
        }

        @Override
        public ResourceLocation getUid() {
            return CATEGORY_ID;
        }

        @Override
        public Class<? extends AlloySmeltingRecipe> getRecipeClass() {
            return AlloySmeltingRecipe.class;
        }

        @Override
        public void setRecipe(IRecipeLayoutBuilder builder, AlloySmeltingRecipe recipe, IFocusGroup focuses) {
            builder.addSlot(RecipeIngredientRole.INPUT, 17, 12).addItemStacks(recipe.baseIngredient.getIngredientStacks());
            builder.addSlot(RecipeIngredientRole.INPUT, 17, 40).addItemStacks(recipe.secondaryIngredient.getIngredientStacks());
            builder.addSlot(RecipeIngredientRole.OUTPUT, 81, 28).addItemStack(recipe.result);
        }
    }

    public static final class MetalPressRecipeCategory implements IRecipeCategory<MetalPressingRecipe> {
        public static final ResourceLocation CATEGORY_ID = ModUtils.modLoc("metal_press");
        public static final RecipeType<MetalPressingRecipe> RECIPE_TYPE = new RecipeType<>(CATEGORY_ID, MetalPressingRecipe.class);
        public static final ResourceLocation TEXTURE = ModUtils.modLoc("textures/jei/metal_press.png");
        private static final ItemStack ICON = new ItemStack(ModBlocks.METAL_PRESS.get().asItem());
        private final IDrawable background;
        private final IDrawableAnimated processArrow;
        private final IDrawable icon;


        public MetalPressRecipeCategory(IGuiHelper helper) {
            this.background = helper.createDrawable(TEXTURE, 0, 0, 114, 69);
            IDrawableStatic staticArrow = helper.createDrawable(TEXTURE, 136, 0, 22, 16);
            this.processArrow = helper.createAnimatedDrawable(staticArrow, 100, IDrawableAnimated.StartDirection.LEFT, false);
            this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ICON);
        }

        @Override
        public void draw(MetalPressingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
            processArrow.draw(stack, 43, 25);
        }

        @Override
        public Component getTitle() {
            return ModLanguage.METAL_PRESS_RECIPE_CATEGORY;
        }

        @Override
        public IDrawable getBackground() {
            return this.background;
        }

        @Override
        public IDrawable getIcon() {
            return icon;
        }


        @Override
        public RecipeType<MetalPressingRecipe> getRecipeType() {
            return RECIPE_TYPE;
        }

        @Override
        public ResourceLocation getUid() {
            return CATEGORY_ID;
        }

        @Override
        public Class<? extends MetalPressingRecipe> getRecipeClass() {
            return MetalPressingRecipe.class;
        }

        @Override
        public void setRecipe(IRecipeLayoutBuilder builder, MetalPressingRecipe recipe, IFocusGroup focuses) {
            builder.addSlot(RecipeIngredientRole.INPUT, 17, 40).addIngredients(recipe.getIngredients().get(0));
            builder.addSlot(RecipeIngredientRole.INPUT, 17, 12).addIngredients(recipe.getIngredients().get(1));
            builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 27).addIngredients(recipe.getIngredients().get(2));
        }
    }
}

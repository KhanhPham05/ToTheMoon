package com.khanhpham.tothemoon.compat.jei;

import com.khanhpham.tothemoon.core.blocks.machines.alloysmelter.AlloySmelterMenu;
import com.khanhpham.tothemoon.core.blocks.machines.alloysmelter.AlloySmelterMenuScreen;
import com.khanhpham.tothemoon.core.blocks.machines.metalpress.MetalPressMenu;
import com.khanhpham.tothemoon.core.blocks.machines.metalpress.MetalPressMenuScreen;
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
import mezz.jei.api.registration.*;
import net.minecraft.ChatFormatting;
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
    public static final RecipeCategoryManager MANAGER = new RecipeCategoryManager();

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        MANAGER.registerCatalysts(registration);
        registration.addRecipeCatalyst(MetalPressRecipeCategory.ICON, MetalPressRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(AlloySmelterRecipeCategory.ICON, AlloySmelterRecipeCategory.RECIPE_TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        MANAGER.registerRecipes(registration, manager);
        registration.addRecipes(MetalPressRecipeCategory.RECIPE_TYPE, manager.getRecipes().parallelStream().filter(recipe -> recipe.getType().equals(MetalPressingRecipe.RECIPE_TYPE)).map(r -> (MetalPressingRecipe) r).toList());
        registration.addRecipes(AlloySmelterRecipeCategory.RECIPE_TYPE, manager.getRecipes().parallelStream().filter(recipe -> recipe.getType().equals(AlloySmeltingRecipe.RECIPE_TYPE)).map(r -> (AlloySmeltingRecipe) r).toList());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        ModUtils.log("Registering TTM JEI Recipes");
        MANAGER.registerAllCategories(registration, helper);
        registration.addRecipeCategories(new MetalPressRecipeCategory(helper), new AlloySmelterRecipeCategory(helper));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        MANAGER.registerGuiHandler(registration);
        registration.addRecipeClickArea(AlloySmelterMenuScreen.class, 61, 31, 35, 20, AlloySmelterRecipeCategory.RECIPE_TYPE);
        registration.addRecipeClickArea(MetalPressMenuScreen.class, 71, 33, 22, 15, MetalPressRecipeCategory.RECIPE_TYPE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        MANAGER.registerRecipeTransferHandlers(registration);
        registration.addRecipeTransferHandler(AlloySmelterMenu.class, AlloySmelterRecipeCategory.RECIPE_TYPE, 0, 2, 3, 36);

        //FIXME
        registration.addRecipeTransferHandler(MetalPressMenu.class, MetalPressRecipeCategory.RECIPE_TYPE, 0, 2, 3, 36);
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
            ModUtils.log("Registering Alloy Smelter Recipe");
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
            return ModLanguage.JEI_ALLOY_SMELTING;
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
            ModUtils.log("Registering Metal Press Recipe");
            this.background = helper.createDrawable(TEXTURE, 0, 0, 114, 69);
            IDrawableStatic staticArrow = helper.createDrawable(TEXTURE, 136, 0, 22, 16);
            this.processArrow = helper.createAnimatedDrawable(staticArrow, 100, IDrawableAnimated.StartDirection.LEFT, false);
            this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ICON);
        }

        @Override
        public void draw(MetalPressingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack pose, double mouseX, double mouseY) {
            processArrow.draw(pose, 43, 25);

            if (recipe.isConsumeMold()) {
                pose.pushPose();
                pose.scale(.9f, .9f, .9f);
                //noinspection ConstantConditions
                Minecraft.getInstance().font.draw(pose, ModLanguage.JEI_METAL_PRESS_CONSUME_MOLD, 0, 58, ChatFormatting.RED.getColor());
                pose.popPose();
            }
        }

        @Override
        public Component getTitle() {
            return ModLanguage.JEI_METAL_PRESS;
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

        //FIXME
        @Override
        public void setRecipe(IRecipeLayoutBuilder builder, MetalPressingRecipe recipe, IFocusGroup focuses) {
            RecipeCategory.addInput(builder, recipe, 0, 17, 12);
            RecipeCategory.addInput(builder, recipe, 1, 17, 40);
            RecipeCategory.addOutput(builder, recipe, 79, 26);
        }
    }
}

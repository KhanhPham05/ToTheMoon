package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.recipes.AlloySmeltingRecipe;
import com.khanhpham.tothemoon.core.recipes.HighHeatSmelting;
import com.khanhpham.tothemoon.core.recipes.TagTranslatingRecipe;
import com.khanhpham.tothemoon.core.recipes.WorkbenchCraftingRecipe;
import com.khanhpham.tothemoon.core.recipes.metalpressing.MetalPressingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModRecipes {

    public static final RecipeSerializer<AlloySmeltingRecipe> ALLOY_SMELTING_RECIPE_SERIALIZER = new AlloySmeltingRecipe.Serializer();
    public static final RecipeSerializer<MetalPressingRecipe> METAL_PRESSING_RECIPE_SERIALIZER = new MetalPressingRecipe.Serializer();
    public static final RecipeSerializer<HighHeatSmelting> BRICK_FURNACE_SMELTING = new HighHeatSmelting.Serializer();
    public static final RecipeSerializer<TagTranslatingRecipe> TAG_TRANSLATING = new TagTranslatingRecipe.Serializer();
    public static final RecipeSerializer<WorkbenchCraftingRecipe> WORKBENCH_CRAFTING = new WorkbenchCraftingRecipe.Serializer();

    public ModRecipes() {
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<RecipeSerializer<?>> event) {
        register(event, AlloySmeltingRecipe.RECIPE_TYPE, ALLOY_SMELTING_RECIPE_SERIALIZER);
        register(event, MetalPressingRecipe.RECIPE_TYPE, METAL_PRESSING_RECIPE_SERIALIZER);
        register(event, HighHeatSmelting.RECIPE_TYPE, BRICK_FURNACE_SMELTING);
        register(event, TagTranslatingRecipe.RECIPE_TYPE, TAG_TRANSLATING);
        register(event, WorkbenchCraftingRecipe.RECIPE_TYPE, WORKBENCH_CRAFTING);
    }

    private static <T extends Recipe<?>> void register(RegistryEvent.Register<RecipeSerializer<?>> event, RecipeType<T> recipeType, RecipeSerializer<T> serializer) {
        System.out.println("Registering Recipes");
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(recipeType.toString()), recipeType);
        event.getRegistry().register(serializer);
    }
}

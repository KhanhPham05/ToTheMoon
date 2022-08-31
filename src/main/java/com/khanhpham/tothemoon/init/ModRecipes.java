package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.recipes.*;
import com.khanhpham.tothemoon.core.recipes.metalpressing.MetalPressingRecipe;
import com.khanhpham.tothemoon.utils.DirectRegistry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModRecipes {
    public static final DirectRegistry<RecipeSerializer<?>> ALL_SERIALIZERS = new DirectRegistry<>();
    public static final DirectRegistry<RecipeType<?>> ALL_RECIPE_TYPES = new DirectRegistry<>();
    public static final RecipeSerializer<AlloySmeltingRecipe> ALLOY_SMELTING_RECIPE_SERIALIZER = new AlloySmeltingRecipe.Serializer();
    public static final RecipeSerializer<MetalPressingRecipe> METAL_PRESSING_RECIPE_SERIALIZER = new MetalPressingRecipe.Serializer();
    public static final RecipeSerializer<HighHeatSmeltingRecipe> BRICK_FURNACE_SMELTING = new HighHeatSmeltingRecipe.Serializer();
    public static final RecipeSerializer<TagTranslatingRecipe> TAG_TRANSLATING = new TagTranslatingRecipe.Serializer();
    public static final RecipeSerializer<WorkbenchCraftingRecipe> WORKBENCH_CRAFTING = new WorkbenchCraftingRecipe.Serializer();
    public static final RecipeSerializer<OreProcessingRecipe> ORE_PROCESSING = new OreProcessingRecipe.Serializer();

    public ModRecipes() {
    }

    @SubscribeEvent
    public static void registerRecipes(RegisterEvent event) {
        ALL_SERIALIZERS.registerAll(event, ForgeRegistries.Keys.RECIPE_SERIALIZERS);
        ALL_RECIPE_TYPES.registerAll(event, ForgeRegistries.Keys.RECIPE_TYPES);
    }
}

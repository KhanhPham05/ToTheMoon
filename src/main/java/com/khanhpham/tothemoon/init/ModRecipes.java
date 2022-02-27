package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.utils.recipes.AlloySmeltingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModRecipes {

    public static final RecipeType<AlloySmeltingRecipe> ALLOY_SMELTING = new RecipeType<>() {
        @Override
        public String toString() {
            return AlloySmeltingRecipe.RECIPE_ID.toString();
        }
    };

    public ModRecipes() {
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<RecipeSerializer<?>> event) {
        reg(event, ALLOY_SMELTING, new AlloySmeltingRecipe.Serializer());
    }

    private static <T extends Recipe<?>> void reg(RegistryEvent.Register<RecipeSerializer<?>> event, RecipeType<T> recipeType, RecipeSerializer<T> serializer) {
        System.out.println("Registering Recipes");
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(recipeType.toString()), recipeType);
        event.getRegistry().register(serializer);
    }
}

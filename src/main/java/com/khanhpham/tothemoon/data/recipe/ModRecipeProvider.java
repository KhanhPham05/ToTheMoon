package com.khanhpham.tothemoon.data.recipe;

import com.khanhpham.tothemoon.ModUtils;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements RecipeGeneratorHelper {

    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }


    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        stairBlocks(consumer, ModBlocks.MOON_ROCK_STAIRS, ModBlocks.MOON_ROCK_BRICK_STAIR);
        slabBlocks(consumer, ModBlocks.MOON_ROCK_SLAB, ModBlocks.MOON_ROCK_BRICK_SLAB);
        craftingShaped(ModBlocks.REDSTONE_MACHINE_FRAME, 1).pattern("RSR").pattern("S S").pattern("RSR").unlockedBy("collect_steel_or_redstone", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(Items.REDSTONE, ModItems.STEEL_ROD).build())).define('R', Items.REDSTONE).define('S', ModItems.STEEL_ROD).save(consumer, ModUtils.modLoc("crafting_redstone_machine_frame"));
        smelting(consumer, ModItems.STEEL_DUST, ModItems.STEEL_INGOT, 1);
        blasting(consumer, ModItems.STEEL_DUST, ModItems.STEEL_INGOT, 1);
        craftingShaped(ModItems.STEEL_ROD, 4).pattern("I").pattern("I").define('I', ModItems.STEEL_INGOT).unlockedBy("collect_steel_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.STEEL_INGOT)).save(consumer, ModUtils.modLoc("crafting_steel_rod"));
    }
}

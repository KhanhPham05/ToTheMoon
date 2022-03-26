package com.khanhpham.tothemoon.data.recipe;

import com.khanhpham.tothemoon.utils.ModUtils;
import com.khanhpham.tothemoon.utils.blocks.MineableSlabBlocks;
import com.khanhpham.tothemoon.utils.blocks.MineableStairBlock;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public interface RecipeGeneratorHelper {
    default void stairBlock(Consumer<FinishedRecipe> consumer, Block resultBlock) {
        if (resultBlock instanceof MineableStairBlock stairBlock) {
            craftingShaped(stairBlock, 4).pattern("A  ").pattern("AA ").pattern("AAA").define('A', stairBlock.parentBlock()).unlockedBy("collect_" + stairBlock.parentBlock().getRegistryName().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(stairBlock.parentBlock())).save(consumer, ModUtils.modLoc("crafting_" + stairBlock.getRegistryName().getPath()));
            stonecutting(consumer, stairBlock.parentBlock(), stairBlock, 1);
        }
    }

    default void stairBlocks(Consumer<FinishedRecipe> consumer, Block... resultBlock) {
        for (Block block : resultBlock) {
            this.stairBlock(consumer, block);
        }
    }

    default void slabBlock(Consumer<FinishedRecipe> consumer, Block resultBlock) {
        if (resultBlock instanceof MineableSlabBlocks slabBlock) {
            craftingShaped(slabBlock, 6).pattern("AAA").define('A', slabBlock.parentBlock()).unlockedBy("collect_" + slabBlock.parentBlock().getRegistryName().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(slabBlock.parentBlock())).save(consumer, ModUtils.modLoc("crafting_" + slabBlock.getRegistryName().getPath()));
            stonecutting(consumer, slabBlock, slabBlock.parentBlock(), 2);
        }
    }

    default void slabBlocks(Consumer<FinishedRecipe> consumer, Block... resultBlocks) {
        for (Block resultBlock : resultBlocks) {
            this.slabBlock(consumer, resultBlock);
        }
    }

    default ShapedRecipeBuilder craftingShaped(ItemLike result, int amount) {
        return ShapedRecipeBuilder.shaped(result, amount);
    }

    default void stonecutting(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike from, int amount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(result), from, amount).unlockedBy("collect_" + from.asItem().getRegistryName().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(from)).save(consumer, ModUtils.modLoc("stonecutting_" + result.asItem().getRegistryName().getPath()));
    }

    default void smelting(Consumer<FinishedRecipe> consumer, ItemLike from, ItemLike result, float experience) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(from), result, experience, 200).unlockedBy("collect_" + from.asItem().getRegistryName().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(from)).save(consumer, ModUtils.modLoc("smelting_" + result.asItem().getRegistryName().getPath()));
    }

    default void blasting(Consumer<FinishedRecipe> consumer, ItemLike from, ItemLike result, float experience) {
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(from), result, experience, 100).unlockedBy("collect_" + from.asItem().getRegistryName().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(from)).save(consumer, ModUtils.modLoc("blasting_" + result.asItem().getRegistryName().getPath()));
    }
}

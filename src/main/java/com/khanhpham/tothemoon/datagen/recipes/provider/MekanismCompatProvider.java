package com.khanhpham.tothemoon.datagen.recipes.provider;

import com.khanhpham.tothemoon.datagen.recipes.builders.mekanism.MetallurgicInfusingBuilder;
import com.khanhpham.tothemoon.datagen.tags.ModItemTags;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

record MekanismCompatProvider(Consumer<FinishedRecipe> consumer) {
    public void run() {
        MetallurgicInfusingBuilder.of(consumer, ModBlocks.GILDED_METEORITE_BRICKS.get()).from(Ingredient.of(ModBlocks.METEORITE_BRICKS.get()), MetallurgicInfusingBuilder.InfuseType.GOLD, 100);
        MetallurgicInfusingBuilder.of(consumer, ModItems.REDSTONE_METAL_MATERIAL.getDust()).from(Ingredient.of(ModItemTags.DUSTS_IRON), MetallurgicInfusingBuilder.InfuseType.REDSTONE, 30);
        MetallurgicInfusingBuilder.of(consumer, ModItems.REDSTONE_STEEL_MATERIALS.getIngot()).from(Ingredient.of(ModItemTags.INGOTS_STEEL), MetallurgicInfusingBuilder.InfuseType.REDSTONE, 30);
        MetallurgicInfusingBuilder.of(consumer, ModItems.REDSTONE_STEEL_MATERIALS.getDust()).from(Ingredient.of(ModItemTags.DUSTS_STEEL), MetallurgicInfusingBuilder.InfuseType.REDSTONE, 30);
    }
}
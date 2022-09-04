package com.khanhpham.tothemoon.datagen.recipes.provider;

import com.khanhpham.tothemoon.datagen.recipes.builders.mekanism.MetallurgicInfusingBuilder;
import com.khanhpham.tothemoon.datagen.tags.ModItemTags;
import com.khanhpham.tothemoon.init.ModItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

record MekanismCompatProvider(Consumer<FinishedRecipe> consumer) {
    public void run() {
        MetallurgicInfusingBuilder.of(consumer, ModItems.REDSTONE_METAL_DUST.get()).from(Ingredient.of(ModItemTags.DUSTS_IRON), MetallurgicInfusingBuilder.InfuseType.REDSTONE, 30);
        MetallurgicInfusingBuilder.of(consumer, ModItems.REDSTONE_STEEL_ALLOY.get()).from(Ingredient.of(ModItemTags.INGOTS_STEEL), MetallurgicInfusingBuilder.InfuseType.REDSTONE, 30);
        MetallurgicInfusingBuilder.of(consumer, ModItems.REDSTONE_STEEL_ALLOY_DUST.get()).from(Ingredient.of(ModItemTags.DUSTS_STEEL), MetallurgicInfusingBuilder.InfuseType.REDSTONE, 30);
    }
}
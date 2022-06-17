package com.khanhpham.tothemoon.core.recipes;

import com.khanhpham.tothemoon.Names;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class SimpleRecipeSerializer<T extends Recipe<?>> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
    public SimpleRecipeSerializer() {
        setRegistryName(Names.MOD_ID, getRecipeName());
    }

    protected abstract String getRecipeName();
}

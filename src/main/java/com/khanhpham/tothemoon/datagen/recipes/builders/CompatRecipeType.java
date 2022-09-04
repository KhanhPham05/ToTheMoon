package com.khanhpham.tothemoon.datagen.recipes.builders;

import net.minecraft.resources.ResourceLocation;

public enum CompatRecipeType {
    METALLURGIC_INFUSING("mekanism:metallurgic_infusing");

    private final ResourceLocation recipeTypeId;

    CompatRecipeType(String recipeTypeId) {
        this.recipeTypeId = ResourceLocation.tryParse(recipeTypeId);
    }

    public ResourceLocation getRecipeTypeId() {
        return recipeTypeId;
    }
}

package com.khanhtypo.tothemoon.serverdata.recipes.serializers;

import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.registration.elements.SimpleObjectSupplier;
import com.khanhtypo.tothemoon.serverdata.recipes.BaseRecipe;
import com.khanhtypo.tothemoon.serverdata.recipes.RecipeTypeObject;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Supplier;

@Deprecated
public class RSerializerObject<T extends BaseRecipe<?>> extends SimpleObjectSupplier<RecipeSerializer<?>> {
    public RSerializerObject(RecipeTypeObject<T> recipeType, Supplier<? extends RecipeSerializer<?>> builder) {
        super(ModRegistries.SERIALIZERS, recipeType.getId().getPath() + "_serializer", builder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public RecipeSerializer<T> get() {
        return (RecipeSerializer<T>) super.get();
    }
}

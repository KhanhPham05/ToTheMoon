package com.khanhtypo.tothemoon.serverdata.recipes;

import com.khanhtypo.tothemoon.utls.ModUtils;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.registration.elements.SimpleObjectSupplier;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class RecipeTypeObject<T extends BaseRecipe<? extends Container>> extends SimpleObjectSupplier<RecipeType<?>> {
    private final RegistryObject<RecipeSerializer<T>> serializer;
    private final Class<T> recipeClass;

    public RecipeTypeObject(String name, Class<T> recipeClass, Supplier<RecipeSerializer<T>> serializer) {
        super(ModRegistries.RECIPE_TYPE, name, RecipeType.simple(ModUtils.location(name)));
        this.recipeClass = recipeClass;
        this.serializer = ModRegistries.SERIALIZERS.register(name, serializer);
    }

    public Class<T> getRecipeClass() {
        return recipeClass;
    }

    public RecipeSerializer<T> getSerializer() {
        return this.serializer.get();
    }

    @Override
    public RecipeType<T> get() {
        return (RecipeType<T>) super.obj;
    }
}

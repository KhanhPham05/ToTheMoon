package com.khanhtypo.tothemoon.registration.bases;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public interface ObjectSupplier<T> extends Supplier<T> {
    static <A extends ItemLike> ObjectSupplier<A> existedItem(A existedObject, ResourceLocation objectId) {
        return new ObjectSupplier<>() {
            @Override
            public ResourceLocation getId() {
                return objectId;
            }

            @Override
            public A get() {
                return existedObject;
            }
        };
    }

    static <A extends ItemLike> ObjectSupplier<A> existedItem(A existedObject) {
        return existedItem(existedObject, ForgeRegistries.ITEMS.getKey(existedObject.asItem()));
    }

    ResourceLocation getId();
}

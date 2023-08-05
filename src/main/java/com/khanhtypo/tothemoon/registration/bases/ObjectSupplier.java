package com.khanhtypo.tothemoon.registration.bases;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public interface ObjectSupplier<T> extends Supplier<T> {
    static <A> ObjectSupplier<A> preExisted(A existedObject, ResourceLocation objectId) {
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

    static <A> ObjectSupplier<A> preExisted(A preExisted, IForgeRegistry<A> registry) {
        return preExisted(preExisted, registry.getKey(preExisted));
    }

    ResourceLocation getId();
}

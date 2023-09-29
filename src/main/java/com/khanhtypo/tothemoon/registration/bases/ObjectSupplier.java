package com.khanhtypo.tothemoon.registration.bases;

import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.registration.elements.SimpleObjectSupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public interface ObjectSupplier<T> extends Supplier<T> {
    static <A> ObjectSupplier<A> preExisted(ResourceKey<Registry<A>> registry, A value) {
        return new ObjectSupplier<>() {
            @Override
            public ResourceLocation getId() {
                return ModRegistries.getNameOrThrow(registry, value);
            }

            @Override
            public A get() {
                return value;
            }
        };
    }

    static <A> ObjectSupplier<A> simple(DeferredRegister<A> deferredRegister, String name, Supplier<A> supplier) {
        return new SimpleObjectSupplier<>(deferredRegister, name, supplier);
    }

    ResourceLocation getId();
}

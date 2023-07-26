package com.khanhtypo.tothemoon.registration.elements;

import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public abstract class BaseObjectSupplier<T> implements ObjectSupplier<T> {
    protected final RegistryObject<? extends T> holder;
    protected T obj;

    public BaseObjectSupplier(DeferredRegister<T> registry, String name, T obj) {
        this(registry, name, () -> obj);
        this.obj = obj;
    }

    public BaseObjectSupplier(DeferredRegister<T> registry, String name, Supplier<? extends T> builder) {
        this(registry.register(name, builder));
    }

    public BaseObjectSupplier(RegistryObject<? extends T> holder) {
        this.holder = holder;
        this.obj = null;
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return this.holder.getId();
    }

    @Override
    public T get() {
        return this.holder.get();
    }
}

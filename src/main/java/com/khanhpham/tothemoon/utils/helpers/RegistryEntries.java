package com.khanhpham.tothemoon.utils.helpers;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

@SuppressWarnings({"deprecation", "unused"})
public class RegistryEntries<T> {
    public static final RegistryEntries<Block> BLOCK = new RegistryEntries<>(Registry.BLOCK);
    public static final RegistryEntries<Item> ITEM = new RegistryEntries<>(Registry.ITEM);
    public static final RegistryEntries<Fluid> FLUID = new RegistryEntries<>(Registry.FLUID);
    public static final RegistryEntries<SoundEvent> SOUND = new RegistryEntries<>(Registry.SOUND_EVENT);
    private final Registry<T> registry;

    private RegistryEntries(Registry<T> registry) {
        this.registry = registry;
    }

    public static <T> ResourceLocation getKeyFrom(T object) {
        if (object instanceof Block block) {
            return BLOCK.getKey(block);
        } else if (object instanceof Item item) {
            return ITEM.getKey(item);
        } else if (object instanceof Fluid fluid) {
            return FLUID.getKey(fluid);
        } else if (object instanceof SoundEvent sound) {
            return SOUND.getKey(sound);
        }

        throw new IllegalStateException();
    }

    public ResourceLocation getKey(T object) {
        return this.registry.getKey(object);
    }

    public String getPath(T object) {
        return this.getKey(object).getPath();
    }

    public String getNameSpace(T object) {
        return this.getKey(object).getNamespace();
    }

    public T getFromKey(ResourceLocation key) {
        return this.registry.getOptional(key).orElseThrow(() -> new IllegalStateException("Can not find registry for " + key.toString()));
    }
}

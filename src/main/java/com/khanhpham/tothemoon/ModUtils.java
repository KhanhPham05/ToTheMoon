package com.khanhpham.tothemoon;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModUtils {
    public static <T extends ForgeRegistryEntry<T>> DeferredRegister<T> createRegistry(IForgeRegistry<T> reg) {
        return DeferredRegister.create(reg, Names.MOD_ID);
    }

    public static ResourceLocation modLoc(String loc) {
        return new ResourceLocation(Names.MOD_ID, loc);
    }
}

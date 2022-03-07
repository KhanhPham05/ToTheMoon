package com.khanhpham.tothemoon;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Optional;

public class ModUtils {
    @Deprecated(forRemoval = true)
    public static <T extends ForgeRegistryEntry<T>> DeferredRegister<T> createRegistry(IForgeRegistry<T> reg) {
        return DeferredRegister.create(reg, Names.MOD_ID);
    }

    public static ResourceLocation modLoc(String loc) {
        return new ResourceLocation(Names.MOD_ID, loc);
    }

    public static TranslatableComponent translate(String key, Object... param) {
        return new TranslatableComponent(key, param);
    }

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(String name) {
        return new RecipeType<>() {
            @Override
            public String toString() {
                return Names.MOD_ID + ':' + name;
            }
        };
    }
}

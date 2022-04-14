package com.khanhpham.tothemoon.utils;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Supplier;

public class ModUtils {
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

    public static boolean isResultSlotAvailableForRecipe(ItemStack givenStack, ItemStack recipeStack) {
        if (givenStack.isEmpty()) {
            return true;
        }
        return givenStack.is(recipeStack.getItem()) & givenStack.getCount() + recipeStack.getCount() <= 64;
    }

    public static <T extends IForgeRegistryEntry<T>> String convertRlToPath(@Nonnull T entry) {
        return Objects.requireNonNull(entry.getRegistryName()).getPath();
    }

    public static void info(String message, Object... arguments) {
        ToTheMoon.LOG.info(message, arguments);
    }

    @SuppressWarnings("deprecation")
    public static String getNameFromItem(Item item) {
        return Registry.ITEM.getKey(item).toString();
    }

    public static <T extends IForgeRegistryEntry<T>> String getNameFromObject(T object) {
        return Objects.requireNonNull(object.getRegistryName()).getPath();
    }
}

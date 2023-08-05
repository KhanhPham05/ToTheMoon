package com.khanhtypo.tothemoon;

import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;

public class ModUtils {
    private ModUtils() {
    }

    public static <T> DeferredRegister<T> createRegistry(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, ToTheMoon.MODID);
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(ToTheMoon.MODID, path);
    }

    public static <A, B extends Comparable<B>> TreeSet<A> sortedSet(Function<A, B> compareGetter) {
        return new TreeSet<>(Comparator.comparing(compareGetter));
    }

    public static <A extends ObjectSupplier<?>> TreeSet<A> resourceSortedSet() {
        return sortedSet(ObjectSupplier::getId);
    }

    public static <A, B> TreeMap<A, B> resourceSortedMap(Function<A, ResourceLocation> compareGetter) {
        return new TreeMap<>(Comparator.comparing(compareGetter));
    }

    public static <A extends ObjectSupplier<?>, B> TreeMap<A, B> resourceSortedMap() {
        return resourceSortedMap(ObjectSupplier::getId);
    }

    public static <A> String toArrayString(Collection<A> collection) {
        return toArrayString(collection, A::toString);
    }

    public static <A, B> String toArrayString(Collection<A> collection, @Nullable Function<A, B> transformer) {
        if (collection.isEmpty()) return "";
        final StringBuilder builder = new StringBuilder().append('[');
        Object[] array = transformer == null ? collection.toArray() : collection.stream().map(transformer).toArray();
        builder.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            builder.append(", ").append(array[i].toString());
        }
        return builder.append(']').toString();
    }

    public static boolean canBurn(ItemStack itemStack) {
        return ForgeHooks.getBurnTime(itemStack, null) > 0;
    }
}

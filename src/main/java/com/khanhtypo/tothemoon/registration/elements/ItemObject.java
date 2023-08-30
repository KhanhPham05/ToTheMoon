package com.khanhtypo.tothemoon.registration.elements;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.common.TabInstance;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.registration.bases.IngredientProvider;
import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ItemObject<T extends Item> implements ObjectSupplier<T>, IngredientProvider {
    private static final Set<ItemObject<? extends Item>> ITEM_SET = ModUtils.sortedSet(ItemObject::getId);
    private final RegistryObject<T> registryObject;

    public ItemObject(String name, Supplier<T> itemSupplier) {
        this(name, itemSupplier, ToTheMoon.DEFAULT_ITEM_TAB);
    }

    public ItemObject(String name, Supplier<T> itemSupplier, TabInstance tabInstance) {
        this(ModRegistries.ITEMS.register(name, itemSupplier), tabInstance);
    }

    protected ItemObject(RegistryObject<T> registryObject, TabInstance tabInstance) {
        this.registryObject = registryObject;
        tabInstance.addItem(this);
        ITEM_SET.add(this);
    }

    public static Stream<ItemObject<? extends Item>> getItems() {
        return ItemObject.ITEM_SET.stream();
    }

    public static Stream<ItemObject<? extends Item>> getItems(Predicate<ItemObject<? extends Item>> predicate) {
        return getItems().filter(predicate);
    }

    public static <T extends Item> ItemObject<T> of(String name, Supplier<T> itemSupplier) {
        return new ItemObject<>(name, itemSupplier, ToTheMoon.DEFAULT_ITEM_TAB);
    }

    @Override
    public T get() {
        return registryObject.get();
    }

    @Override
    @Nonnull
    public Item asItem() {
        return this.get().asItem();
    }

    public static <T extends Item> ItemObject<T> register(String name, Supplier<T> register) {
        return new ItemObject<>(name, register);
    }

    @Override
    @Nonnull
    public ResourceLocation getId() {
        return this.registryObject.getId();
    }

}

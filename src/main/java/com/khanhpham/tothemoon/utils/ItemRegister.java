package com.khanhpham.tothemoon.utils;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ItemRegister {
    private final Set<Item> items = new HashSet<>();

    public ItemRegister() {
    }

    public Item register(String name) {
        return register(name, new Item(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    public <T extends Item> Item register(String name, T item) {
        System.out.println("Registering " + name);
        items.add(item.setRegistryName(Names.MOD_ID, name));
        return item;
    }

    public void registerAll(IForgeRegistry<Item> reg) {
        items.forEach(reg::register);
    }

    public void forEachItem(Consumer<Item> item) {
        this.items.forEach(item);
    }
}

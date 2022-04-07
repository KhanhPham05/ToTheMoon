package com.khanhpham.tothemoon.utils.registration;

import com.khanhpham.tothemoon.utils.ModUtils;
import com.khanhpham.tothemoon.ToTheMoon;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemRegister {
    public final List<Item> items = new ArrayList<>();

    public ItemRegister() {
    }

    public Item register(String name) {
        return register(name, new Item(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    public <T extends Item> Item register(String name, T item) {
        System.out.println("Registering " + name);
        items.add(item.setRegistryName(ModUtils.modLoc(name)));
        return item;
    }

    public void registerAll(IForgeRegistry<Item> reg) {
        items.forEach(item -> {
            reg.register(item);
            System.out.println("Registered " + item.getRegistryName());
        });
    }

    public void forEachItem(Consumer<Item> item) {
        this.items.forEach(item);
    }
}

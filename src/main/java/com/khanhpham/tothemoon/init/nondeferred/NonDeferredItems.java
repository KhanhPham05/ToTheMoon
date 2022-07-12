package com.khanhpham.tothemoon.init.nondeferred;

import com.khanhpham.tothemoon.core.blocks.HasCustomBlockItem;
import com.khanhpham.tothemoon.init.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

public class NonDeferredItems {
    public static final Set<Item> REGISTERED_ITEMS = new HashSet<>();

    static {
        NonDeferredBlocks.REGISTERED_BLOCKS.forEach(block -> {
            if (block instanceof HasCustomBlockItem customBlockItem) {
                REGISTERED_ITEMS.add(customBlockItem.getItem(block.getRegistryName()));
            } else REGISTERED_ITEMS.add(new BlockItem(block, ModItems.GENERAL_PROPERTIES).setRegistryName(block.getRegistryName()));
        });
    }

    public static void registerAll(IForgeRegistry<Item> registry) {
        REGISTERED_ITEMS.forEach(registry::register);
    }
}

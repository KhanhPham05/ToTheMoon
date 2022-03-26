package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.utils.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.utils.registration.ItemRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import java.util.Objects;

//Just want to add it here so my IDEA don't give me some stupid warnings
@SuppressWarnings("unused")
public class ModItems {
    public static final ItemRegister ITEMS = new ItemRegister();

    //special ingredient
    public static Item REDSTONE_STEEL_ALLOY;
    public static Item REDSTONE_STEEL_ALLOY_GEAR;
    public static Item REDSTONE_STEEL_ALLOY_PLATE;
    public static Item REDSTONE_STEEL_ALLOY_DUST;

    public static Item REDSTONE_METAL;
    public static Item REDSTONE_METAL_PLATE;
    public static Item REDSTONE_METAL_GEAR;

    //common crafting ingredient
    public static Item URANIUM_INGOT;
    public static Item URANIUM_DUST;
    public static Item URANIUM_PLATE;
    public static Item URANIUM_GEAR;

    public static Item COPPER_PLATE;
    public static Item COPPER_DUST;
    public static Item COPPER_GEAR;

    public static Item STEEL_PLATE;
    public static Item STEEL_INGOT;
    public static Item STEEL_DUST;
    public static Item STEEL_ROD;
    public static Item STEEL_GEAR;

    public static Item IRON_PLATE;
    public static Item IRON_DUST;
    public static Item IRON_GEAR;

    public static Item GOLD_PLATE;
    public static Item GOLD_DUST;
    public static Item GOLD_GEAR;

    //METAL PRESS MOLDS
    public static Item BLANK_MOLD;
    public static Item GEAR_MOLD;
    public static Item PLATE_MOLD;


    private ModItems() {
    }

    public static Item register(String name) {
        return ITEMS.register(name, new Item(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    private static void registerBlockItem(Block block) {
        Item.Properties properties = new Item.Properties().tab(ToTheMoon.TAB);

        if (!(block instanceof BaseEntityBlock<?>))
            ITEMS.register(getRegistryName(block).getPath(), new BlockItem(block, properties));
        else
            ITEMS.register(getRegistryName(block).getPath(), new BlockItem(block, properties.stacksTo(1)));
    }

    public static void init(IForgeRegistry<Item> registry) {
        ModBlocks.BLOCK_REGISTER.getRegisteredBlocks().forEach(ModItems::registerBlockItem);
        ITEMS.registerAll(registry);
    }

    @Nonnull
    private static ResourceLocation getRegistryName(Block block) {
        return Objects.requireNonNull(block.getRegistryName());
    }
}

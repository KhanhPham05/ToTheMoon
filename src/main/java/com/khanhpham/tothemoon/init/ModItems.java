package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.utils.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.utils.registration.ItemRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Objects;

//Just want to add it here so my IDEA don't give me some stupid warnings
@SuppressWarnings("unused")
public class ModItems {
    public static final ItemRegister ITEMS = new ItemRegister();

    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Names.MOD_ID);
    //special ingredient
    public static RegistryObject<Item> REDSTONE_STEEL_ALLOY;
    public static RegistryObject<Item> REDSTONE_STEEL_ALLOY_GEAR;
    public static RegistryObject<Item> REDSTONE_STEEL_ALLOY_PLATE;
    public static RegistryObject<Item> REDSTONE_STEEL_ALLOY_DUST;
    public static RegistryObject<Item> REDSTONE_STEEL_ALLOY_ROD;

    public static RegistryObject<Item> REDSTONE_METAL;
    public static RegistryObject<Item> REDSTONE_METAL_PLATE;
    public static RegistryObject<Item> REDSTONE_METAL_GEAR;
    public static RegistryObject<Item> REDSTONE_METAL_ROD;

    //common crafting ingredient
    public static RegistryObject<Item> URANIUM_INGOT;
    public static RegistryObject<Item> URANIUM_DUST;
    public static RegistryObject<Item> URANIUM_PLATE;
    public static RegistryObject<Item> URANIUM_GEAR;
    public static RegistryObject<Item> URANIUM_ROD;

    public static RegistryObject<Item> COPPER_PLATE;
    public static RegistryObject<Item> COPPER_DUST;
    public static RegistryObject<Item> COPPER_GEAR;
    public static RegistryObject<Item> COPPER_ROD;

    public static RegistryObject<Item> STEEL_PLATE;
    public static RegistryObject<Item> STEEL_INGOT;
    public static RegistryObject<Item> STEEL_DUST;
    public static RegistryObject<Item> STEEL_ROD;
    public static RegistryObject<Item> STEEL_GEAR;

    public static RegistryObject<Item> IRON_PLATE;
    public static RegistryObject<Item> IRON_DUST;
    public static RegistryObject<Item> IRON_GEAR;
    public static RegistryObject<Item> IRON_ROD;

    public static RegistryObject<Item> GOLD_PLATE;
    public static RegistryObject<Item> GOLD_DUST;
    public static RegistryObject<Item> GOLD_GEAR;
    public static RegistryObject<Item> GOLD_ROD;

    //METAL PRESS MOLDS
    public static RegistryObject<Item> BLANK_MOLD;
    public static RegistryObject<Item> GEAR_MOLD;
    public static RegistryObject<Item> PLATE_MOLD;
    public static RegistryObject<Item> ROD_MOLD;

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


    @Nonnull
    public static ResourceLocation getRegistryName(Block block) {
        return Objects.requireNonNull(block.getRegistryName());
    }

    public static void registerItems() {
        Class<ModItems> modItemsClass = ModItems.class;
        for (Field field : modItemsClass.getDeclaredFields()) {
            if (field.getType().equals(RegistryObject.class)) {
                try {
                    field.set(RegistryObject.class, ITEM_DEFERRED_REGISTER.register(field.getName().toLowerCase(Locale.ROOT), () -> new Item(new Item.Properties().tab(ToTheMoon.TAB))));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void init() {
    }
}

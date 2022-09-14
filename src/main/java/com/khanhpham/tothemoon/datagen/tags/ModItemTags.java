package com.khanhpham.tothemoon.datagen.tags;

import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static net.minecraftforge.common.Tags.Items.*;

@SuppressWarnings("unused")
public class ModItemTags {
    public static final AppendableItemTagKey GENERAL_PRESS_MOLDS = createAppendable(ModUtils.modLoc("metal_press_molds"));
    public static final TagKey<Item> PLATE_MOLD = GENERAL_PRESS_MOLDS.append("plate", ModItems.PLATE_MOLD);
    public static final TagKey<Item> ROD_MOLD = GENERAL_PRESS_MOLDS.append("rod", ModItems.ROD_MOLD);
    public static final TagKey<Item> GEAR_MOLD = GENERAL_PRESS_MOLDS.append("gear", ModItems.GEAR_MOLD);
    public static final TagKey<Item> BLANK_MOLD = GENERAL_PRESS_MOLDS.append("blank", ModItems.BLANK_MOLD);

    public static final TagKey<Item> INGOTS_STEEL = forge("ingots/steel");
    public static final TagKey<Item> INGOTS_URANIUM = forge("ingots/uranium");
    public static final TagKey<Item> INGOTS_REDSTONE_METAL = forge("ingots/redstone_metal");
    public static final TagKey<Item> INGOTS_REDSTONE_STEEL_ALLOY = forge("ingots/redstone_steel_alloy");


    public static final TagKey<Item> GENERAL_PLATES = forge("plates");
    public static final TagKey<Item> PLATES_STEEL = append(GENERAL_PLATES, "steel");
    public static final TagKey<Item> PLATES_URANIUM = append(GENERAL_PLATES, "uranium");
    public static final TagKey<Item> PLATES_REDSTONE_METAL = append(GENERAL_PLATES, "redstone_metal");
    public static final TagKey<Item> PLATES_REDSTONE_STEEL_ALLOY = append(GENERAL_PLATES, "redstone_steel_alloy");

    public static final TagKey<Item> PLATES_IRON = append(GENERAL_PLATES, "iron");
    public static final TagKey<Item> PLATES_COPPER = append(GENERAL_PLATES, "copper");
    public static final TagKey<Item> PLATES_GOLD = append(GENERAL_PLATES, "gold");

    public static final TagKey<Item> GENERAL_GEARS = ItemTags.create(new ResourceLocation("forge", "gears"));
    public static final TagKey<Item> GEARS_STEEL = append(GENERAL_GEARS, "steel");
    public static final TagKey<Item> GEARS_COPPER = append(GENERAL_GEARS, "copper");
    public static final TagKey<Item> GEARS_GOLD = append(GENERAL_GEARS, "gold");
    public static final TagKey<Item> GEARS_REDSTONE_STEEL_ALLOY = append(GENERAL_GEARS, "redstone_steel");
    public static final TagKey<Item> GEARS_REDSTONE_METAL = append(GENERAL_GEARS, "redstone_metal");
    public static final TagKey<Item> GEARS_IRON = append(GENERAL_GEARS, "iron");

    public static final TagKey<Item> GENERAL_RODS = ItemTags.create(new ResourceLocation("forge", "rods"));
    public static final TagKey<Item> RODS_COPPER = append(GENERAL_RODS, "copper");
    public static final TagKey<Item> RODS_IRON = append(GENERAL_RODS, "iron");
    public static final TagKey<Item> RODS_GOLD = append(GENERAL_RODS, "gold");
    public static final TagKey<Item> RODS_URANIUM = append(GENERAL_RODS, "uranium");
    public static final TagKey<Item> RODS_STEEL = append(GENERAL_RODS, "steel");
    public static final TagKey<Item> RODS_REDSTONE_METAL = append(GENERAL_RODS, "redstone_metal");
    public static final TagKey<Item> RODS_REDSTONE_STEEL = append(GENERAL_RODS, "redstone_steel");

    public static final AppendableItemTagKey GENERAL_DUSTS = createAppendable(DUSTS.location());
    public static final TagKey<Item> DUSTS_COAL = GENERAL_DUSTS.append("coal", ModItems.COAL_DUST);
    public static final TagKey<Item> DUSTS_HEATED_COAL = GENERAL_DUSTS.append("heated_coal", ModItems.HEATED_COAL_DUST);
    public static final TagKey<Item> DUSTS_IRON = GENERAL_DUSTS.append("iron");
    public static final TagKey<Item> DUSTS_GOLD = GENERAL_DUSTS.append("gold");
    public static final TagKey<Item> DUSTS_REDSTONE_STEEL_ALLOY = GENERAL_DUSTS.append("redstone_steel_alloy");
    public static final TagKey<Item> DUSTS_STEEL = GENERAL_DUSTS.append("steel");
    public static final TagKey<Item> DUSTS_URANIUM = GENERAL_DUSTS.append("uranium");
    public static final TagKey<Item> DUSTS_REDSTONE_METAL = GENERAL_DUSTS.append("redstone_metal");
    public static final TagKey<Item> DUSTS_COPPER = GENERAL_DUSTS.append("copper");

    public static final TagKey<Item> GENERAL_HAMMERS = mod("hammers");
    public static final TagKey<Item> MOON_ROCKS = mod("moon_rocks");
    public static final TagKey<Item> GENERAL_SHEETMETALS = forge("sheetmetals");
    public static final TagKey<Item> SHEETMETAL_COPPER = append(GENERAL_SHEETMETALS, "copper");
    public static final TagKey<Item> SHEETMETAL_STEEL = append(GENERAL_SHEETMETALS, "steel");
    public static final TagKey<Item> SHEETMETAL_GOLD = append(GENERAL_SHEETMETALS, "gold");
    public static final TagKey<Item> SHEETMETAL_IRON = append(GENERAL_SHEETMETALS, "iron");

    public static final TagKey<Item> URANIUM_RAW_MATERIAL = append(RAW_MATERIALS, "uranium");

    public static final TagKey<Item> GENERAL_STORAGE_BLOCKS = STORAGE_BLOCKS;
    public static final TagKey<Item> STEEL_STORAGE_BLOCK = append(GENERAL_STORAGE_BLOCKS, "steel");
    public static final TagKey<Item> REDSTONE_METAL_STORAGE_BLOCK = append(GENERAL_STORAGE_BLOCKS, "redstone_metal");
    public static final TagKey<Item> REDSTONE_STEEL_STORAGE_BLOCK = append(GENERAL_STORAGE_BLOCKS, "redstone_steel");
    public static final TagKey<Item> URANIUM_STORAGE_BLOCK = append(GENERAL_STORAGE_BLOCKS, "uranium");
    public static final TagKey<Item> RAW_URANIUM_STORAGE_BLOCK = append(GENERAL_STORAGE_BLOCKS, "raw_uranium");
    public static final TagKey<Item> PURIFIED_QUARTZ_STORAGE_BLOCK = append(GENERAL_STORAGE_BLOCKS, "purified_quartz");

    public static final TagKey<Item> GEMS_PURIFIED_QUARTZ = append(GEMS, "purified_quartz");

    public static final TagKey<Item> GLASS_DARK = append(GLASS, "dark");

    public static final TagKey<Item> ORES_IN_MOON_ROCK = forge("ores_in_ground/moon_rock");
    public static final TagKey<Item> ORES_IN_MOON_DUST = forge("ores_in_ground/moon_dust");

    //Already available in Forge Tags
    //ORE_BEARING_GROUND_STONE
    //ORE_RATES_DENSE / SINGULAR
    //ORE_IN_GROUND

    public static final TagKey<Item> WIRES = forge("wires");
    public static final TagKey<Item> WIRES_COPPER = append(WIRES, "copper");
    public static final TagKey<Item> WIRES_GOLD = append(WIRES, "gold");
    public static final TagKey<Item> WIRES_IRON = append(WIRES, "iron");
    public static final TagKey<Item> WIRES_REDSTONE_METAL = append(WIRES, "redstone_metal");
    public static final TagKey<Item> WIRES_REDSTONE_STEEL_ALLOY = append(WIRES, "redstone_steel_alloy");
    public static final TagKey<Item> WIRES_STEEL = append(WIRES, "steel");
    public static final TagKey<Item> WIRES_URANIUM = append(WIRES, "uranium");

    public static final TagKey<Item> TREATED_WOOD = forge("treated_wood");
    public static final TagKey<Item> ORES_URANIUM = forge("ores/uranium");


    private static TagKey<Item> mod(final String name) {
        return ItemTags.create(ModUtils.modLoc(name));
    }

    private static TagKey<Item> append(final TagKey<Item> prefix, final String suffix) {
        return ItemTags.create(ModUtils.append(prefix.location(), '/' + suffix));
    }

    private static TagKey<Item> forge(final String name) {
        return ItemTags.create(new ResourceLocation("forge", name));
    }

    private static AppendableItemTagKey createAppendable(ResourceLocation rl) {
        return new AppendableItemTagKey(ItemTags.create(rl));
    }

}

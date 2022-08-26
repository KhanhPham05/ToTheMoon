package com.khanhpham.tothemoon.datagen.tags;

import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import static net.minecraftforge.common.Tags.Items.*;

@SuppressWarnings("unused")
public class ModItemTags {
    public static final AppendableItemTagKey GENERAL_PRESS_MOLDS = createAppendable(modLoc("metal_press_molds"));
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

    public static final AppendableItemTagKey GENERAL_GEARS = new AppendableItemTagKey(ItemTags.create(new ResourceLocation("forge", "gears")));
    public static final TagKey<Item> GEARS_STEEL = GENERAL_GEARS.append("steel", ModItems.STEEL_GEAR);
    public static final TagKey<Item> GEARS_COPPER = GENERAL_GEARS.append("copper", ModItems.COPPER_GEAR);
    public static final TagKey<Item> GEARS_GOLD = GENERAL_GEARS.append("gold", ModItems.GOLD_GEAR);
    public static final TagKey<Item> GEARS_REDSTONE_STEEL_ALLOY = GENERAL_GEARS.append("redstone_steel", ModItems.REDSTONE_STEEL_ALLOY_GEAR);
    public static final TagKey<Item> GEARS_REDSTONE_METAL = GENERAL_GEARS.append("redstone_metal", ModItems.REDSTONE_METAL_GEAR);
    public static final TagKey<Item> GEARS_IRON = GENERAL_GEARS.append("iron", ModItems.IRON_GEAR);

    public static final AppendableItemTagKey GENERAL_RODS = new AppendableItemTagKey(RODS);
    public static final TagKey<Item> RODS_COPPER = GENERAL_RODS.append("copper", ModItems.COPPER_ROD);
    public static final TagKey<Item> RODS_IRON = GENERAL_RODS.append("iron", ModItems.IRON_ROD);
    public static final TagKey<Item> RODS_GOLD = GENERAL_RODS.append("gold", ModItems.GOLD_ROD);
    public static final TagKey<Item> RODS_URANIUM = GENERAL_RODS.append("uranium", ModItems.URANIUM_ROD);
    public static final TagKey<Item> RODS_STEEL = GENERAL_RODS.append("steel", ModItems.STEEL_ROD);
    public static final TagKey<Item> RODS_REDSTONE_METAL = GENERAL_RODS.append("redstone_metal", ModItems.REDSTONE_METAL_ROD);
    public static final TagKey<Item> RODS_REDSTONE_STEEL = GENERAL_RODS.append("redstone_steel", ModItems.REDSTONE_STEEL_ALLOY_ROD);

    public static final AppendableItemTagKey.OneWayProcessable GENERAL_DUSTS = new AppendableItemTagKey.OneWayProcessable(DUSTS);
    public static final TagKey<Item> DUSTS_COAL = GENERAL_DUSTS.append("coal", ModItems.COAL_DUST);
    public static final TagKey<Item> DUSTS_HEATED_COAL = GENERAL_DUSTS.append("heated_coal", ModItems.HEATED_COAL_DUST);
    public static final TagKey<Item> DUSTS_GOLD = GENERAL_DUSTS.append("gold", ModItems.GOLD_DUST, () -> Items.GOLD_INGOT);
    public static final TagKey<Item> DUSTS_REDSTONE_STEEL_ALLOY = GENERAL_DUSTS.append("redstone_steel_metal", ModItems.REDSTONE_STEEL_ALLOY_DUST, ModItems.REDSTONE_STEEL_ALLOY);
    public static final TagKey<Item> DUSTS_URANIUM = GENERAL_DUSTS.append("uranium", ModItems.URANIUM_DUST, ModItems.URANIUM_INGOT);
    public static final TagKey<Item> DUSTS_STEEL = GENERAL_DUSTS.append("steel", ModItems.STEEL_DUST, ModItems.STEEL_INGOT);
    public static final TagKey<Item> DUSTS_REDSTONE_METAL = GENERAL_DUSTS.append("redstone_metal", ModItems.REDSTONE_METAL_DUST, ModItems.REDSTONE_METAL);
    public static final TagKey<Item> DUSTS_IRON = GENERAL_DUSTS.append("iron", ModItems.IRON_DUST, () -> Items.IRON_INGOT);
    public static final TagKey<Item> DUSTS_COPPER = GENERAL_DUSTS.append("copper", ModItems.COPPER_DUST, () -> Items.COPPER_INGOT);

    public static final TagKey<Item> GENERAL_TTM_HAMMERS = mod("hammers");
    public static final TagKey<Item> MOON_ROCKS = mod("moon_rocks");
    public static final AppendableItemTagKey GENERAL_SHEETMETALS = new AppendableItemTagKey(forge("sheetmetals"));
    public static final TagKey<Item> SHEETMETAL_COPPER = GENERAL_SHEETMETALS.append("copper", ModBlocks.COPPER_SHEET_BLOCK.get());
    public static final TagKey<Item> SHEETMETAL_STEEL = GENERAL_SHEETMETALS.append("steel", ModBlocks.STEEL_SHEET_BLOCK.get());
    public static final TagKey<Item> SHEETMETAL_GOLD = GENERAL_SHEETMETALS.append("gold", ModBlocks.GOLD_SHEET_BLOCK.get());
    public static final TagKey<Item> SHEETMETAL_IRON = GENERAL_SHEETMETALS.append("iron", ModBlocks.IRON_SHEET_BLOCK.get());

    public static final TagKey<Item> URANIUM_RAW_MATERIAL = append(RAW_MATERIALS, "uranium");

    public static final AppendableItemTagKey GENERAL_STORAGE_BLOCKS = new AppendableItemTagKey(STORAGE_BLOCKS);
    public static final TagKey<Item> STEEL_STORAGE_BLOCK = GENERAL_STORAGE_BLOCKS.append("steel", ModBlocks.STEEL_BLOCK.get());
    public static final TagKey<Item> REDSTONE_METAL_STORAGE_BLOCK = GENERAL_STORAGE_BLOCKS.append("redstone_metal", ModBlocks.REDSTONE_METAL_BLOCK.get());
    public static final TagKey<Item> REDSTONE_STEEL_STORAGE_BLOCK = GENERAL_STORAGE_BLOCKS.append("redstone_steel", ModBlocks.REDSTONE_STEEL_ALLOY_BLOCK.get());
    public static final TagKey<Item> URANIUM_STORAGE_BLOCK = GENERAL_STORAGE_BLOCKS.append("uranium", ModBlocks.URANIUM_BLOCK.get());
    public static final TagKey<Item> RAW_URANIUM_STORAGE_BLOCK = GENERAL_STORAGE_BLOCKS.append("raw_uranium", ModBlocks.RAW_URANIUM_BLOCK.get());
    public static final TagKey<Item> PURIFIED_QUARTZ_STORAGE_BLOCK = GENERAL_STORAGE_BLOCKS.append("purified_quartz", ModBlocks.PURIFIED_QUARTZ_BLOCK.get());

    public static final TagKey<Item> GEMS_PURIFIED_QUARTZ = append(GEMS, "purified_quartz");

    public static final TagKey<Item> GLASS_DARK = append(GLASS, "dark");

    public static final TagKey<Item> ORES_IN_MOON_ROCK = forge("ores_in_ground/moon_rock");
    public static final TagKey<Item> ORES_IN_MOON_DUST = forge("ores_in_ground/moon_dust");

    //Already available in Forge Tags
    //ORE_BEARING_GROUND_STONE
    //ORE_RATES_DENSE / SINGULAR
    //ORE_IN_GROUND

    public static final TagKey<Item> WIRES = forge("wire");
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

    private static ResourceLocation modLoc(String name) {
        return ModUtils.modLoc(name);
    }

    private static AppendableItemTagKey createAppendable(ResourceLocation rl) {
        return new AppendableItemTagKey(ItemTags.create(rl));
    }

}

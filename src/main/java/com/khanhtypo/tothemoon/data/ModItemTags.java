package com.khanhtypo.tothemoon.data;

import com.khanhtypo.tothemoon.common.tag.ItemTagFamilies;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.khanhtypo.tothemoon.common.tag.ItemTagFamilies.*;
import static com.khanhtypo.tothemoon.registration.ModItems.*;

@SuppressWarnings("unused")
public class ModItemTags {
    public static final TagKey<Item> MACHINE_UPGRADES = ItemTags.create(ModUtils.location("machine_upgrades"));
    public static final TagKey<Item> MACHINE_UPGRADES_GENERATOR = ItemTags.create(MACHINE_UPGRADES.location().withSuffix("/power_generators"));
    public static final TagKey<Item> TOOL_HAMMERS = ItemTagFamilies.HAMMERS.getRootTag();
    public static final TagKey<Item> RAW_ORE_URANIUM = RAW_ORES.createChild("uranium", RAW_URANIUM_CHUNK);
    public static final TagKey<Item> RAW_ORE_ZIRCONIUM = RAW_ORES.createChild("zirconium", RAW_ZIRCONIUM);

    public static final TagKey<Item> DUST_AMETHYST = DUSTS.createChild("amethyst", AMETHYST_DUST);
    public static final TagKey<Item> GEAR_AMETHYST = GEARS.createChild("amethyst", AMETHYST_GEAR);
    public static final TagKey<Item> PLATE_AMETHYST = PLATES.createChild("amethyst", AMETHYST_PLATE);
    public static final TagKey<Item> ROD_AMETHYST = RODS.createChild("amethyst", AMETHYST_ROD);

    public static final TagKey<Item> DUST_COPPER = DUSTS.createChild("copper", COPPER_DUST);
    public static final TagKey<Item> GEAR_COPPER = GEARS.createChild("copper", COPPER_GEAR);
    public static final TagKey<Item> NUGGET_COPPER = NUGGETS.createChild("copper", COPPER_NUGGET);
    public static final TagKey<Item> PLATE_COPPER = PLATES.createChild("copper", COPPER_PLATE);
    public static final TagKey<Item> ROD_COPPER = RODS.createChild("copper", COPPER_ROD);
    public static final TagKey<Item> WIRE_COPPER = WIRES.createChild("copper", COPPER_WIRE);

    public static final TagKey<Item> DUST_DIAMOND = DUSTS.createChild("diamond", DIAMOND_DUST);
    public static final TagKey<Item> GEAR_DIAMOND = GEARS.createChild("diamond", DIAMOND_GEAR);
    public static final TagKey<Item> HAMMER_DIAMOND = HAMMERS.createChild("diamond", DIAMOND_HAMMER);
    public static final TagKey<Item> PLATE_DIAMOND = PLATES.createChild("diamond", DIAMOND_PLATE);
    public static final TagKey<Item> ROD_DIAMOND = RODS.createChild("diamond", DIAMOND_ROD);

    public static final TagKey<Item> DUST_EMERALD = DUSTS.createChild("emerald", EMERALD_DUST);
    public static final TagKey<Item> GEAR_EMERALD = GEARS.createChild("emerald", EMERALD_GEAR);
    public static final TagKey<Item> PLATE_EMERALD = PLATES.createChild("emerald", EMERALD_PLATE);
    public static final TagKey<Item> ROD_EMERALD = RODS.createChild("emerald", EMERALD_ROD);

    public static final TagKey<Item> DUST_GOLD = DUSTS.createChild("gold", GOLD_DUST);
    public static final TagKey<Item> GEAR_GOLD = GEARS.createChild("gold", GOLD_GEAR);
    public static final TagKey<Item> PLATE_GOLD = PLATES.createChild("gold", GOLD_PLATE);
    public static final TagKey<Item> ROD_GOLD = RODS.createChild("gold", GOLD_ROD);
    public static final TagKey<Item> WIRE_GOLD = WIRES.createChild("gold", GOLD_WIRE);

    public static final TagKey<Item> DUST_IRON = DUSTS.createChild("iron", IRON_DUST);
    public static final TagKey<Item> GEAR_IRON = GEARS.createChild("iron", IRON_GEAR);
    public static final TagKey<Item> PLATE_IRON = PLATES.createChild("iron", IRON_PLATE);
    public static final TagKey<Item> ROD_IRON = RODS.createChild("iron", IRON_ROD);
    public static final TagKey<Item> WIRE_IRON = WIRES.createChild("iron", IRON_WIRE);

    public static final TagKey<Item> DUST_LAPIS = DUSTS.createChild("lapis", LAPIS_DUST);
    public static final TagKey<Item> GEAR_LAPIS = GEARS.createChild("lapis", LAPIS_GEAR);
    public static final TagKey<Item> PLATE_LAPIS = PLATES.createChild("lapis", LAPIS_PLATE);
    public static final TagKey<Item> ROD_LAPIS = RODS.createChild("lapis", LAPIS_ROD);

    public static final TagKey<Item> DUST_NETHERITE = DUSTS.createChild("netherite", NETHERITE_DUST);
    public static final TagKey<Item> GEAR_NETHERITE = GEARS.createChild("netherite", NETHERITE_GEAR);
    public static final TagKey<Item> HAMMER_NETHERITE = HAMMERS.createChild("netherite", NETHERITE_HAMMER);
    public static final TagKey<Item> PLATE_NETHERITE = PLATES.createChild("netherite", NETHERITE_PLATE);
    public static final TagKey<Item> ROD_NETHERITE = RODS.createChild("netherite", NETHERITE_ROD);

    public static final TagKey<Item> GEM_PURIFIED_QUARTZ = GEMS.createChild("purified_quartz", PURIFIED_QUARTZ);
    public static final TagKey<Item> DUST_QUARTZ = DUSTS.createChild("quartz", QUARTZ_DUST);
    public static final TagKey<Item> GEAR_QUARTZ = GEARS.createChild("quartz", QUARTZ_GEAR);
    public static final TagKey<Item> PLATE_QUARTZ = PLATES.createChild("quartz", QUARTZ_PLATE);
    public static final TagKey<Item> ROD_QUARTZ = RODS.createChild("quartz", QUARTZ_ROD);

    public static final TagKey<Item> DUST_REDSTONE_METAL = DUSTS.createChild("redstone_metal", REDSTONE_METAL_DUST);
    public static final TagKey<Item> GEAR_REDSTONE_METAL = GEARS.createChild("redstone_metal", REDSTONE_METAL_GEAR);
    public static final TagKey<Item> INGOT_REDSTONE_METAL = INGOTS.createChild("redstone_metal", REDSTONE_METAL_INGOT);
    public static final TagKey<Item> PLATE_REDSTONE_METAL = PLATES.createChild("redstone_metal", REDSTONE_METAL_PLATE);
    public static final TagKey<Item> ROD_REDSTONE_METAL = RODS.createChild("redstone_metal", REDSTONE_METAL_ROD);
    public static final TagKey<Item> WIRE_REDSTONE_METAL = WIRES.createChild("redstone_metal", REDSTONE_METAL_WIRE);

    public static final TagKey<Item> DUST_REDSTONE_STEEL = DUSTS.createChild("redstone_steel", REDSTONE_STEEL_DUST);
    public static final TagKey<Item> GEAR_REDSTONE_STEEL = GEARS.createChild("redstone_steel", REDSTONE_STEEL_GEAR);
    public static final TagKey<Item> INGOT_REDSTONE_STEEL = INGOTS.createChild("redstone_steel", REDSTONE_STEEL_INGOT);
    public static final TagKey<Item> PLATE_REDSTONE_STEEL = PLATES.createChild("redstone_steel", REDSTONE_STEEL_PLATE);
    public static final TagKey<Item> ROD_REDSTONE_STEEL = RODS.createChild("redstone_steel", REDSTONE_STEEL_ROD);
    public static final TagKey<Item> WIRE_REDSTONE_STEEL = WIRES.createChild("redstone_steel", REDSTONE_STEEL_WIRE);
    public static final TagKey<Item> BOOT_REDSTONE_STEEL = BOOTS.createChild("redstone_steel", REDSTONE_STEEL_BOOTS);
    public static final TagKey<Item> CHESTPLATE_REDSTONE_STEEL = CHESTPLATES.createChild("redstone_steel", REDSTONE_STEEL_CHESTPLATE);
    public static final TagKey<Item> HELMET_REDSTONE_STEEL = HELMETS.createChild("redstone_steel", REDSTONE_STEEL_HELMET);
    public static final TagKey<Item> LEGGING_REDSTONE_STEEL = LEGGINGS.createChild("redstone_steel", REDSTONE_STEEL_LEGGINGS);


    public static final TagKey<Item> INGOT_STEEL = INGOTS.createChild("steel", STEEL_INGOT);
    public static final TagKey<Item> DUST_STEEL = DUSTS.createChild("steel", STEEL_DUST);
    public static final TagKey<Item> GEAR_STEEL = GEARS.createChild("steel", STEEL_GEAR);
    public static final TagKey<Item> PLATE_STEEL = PLATES.createChild("steel", STEEL_PLATE);
    public static final TagKey<Item> ROD_STEEL = RODS.createChild("rod", STEEL_ROD);
    public static final TagKey<Item> WIRE_STEEL = WIRES.createChild("steel", STEEL_WIRE);
    public static final TagKey<Item> HAMMER_STEEL = HAMMERS.createChild("steel", STEEL_HAMMER);
    public static final TagKey<Item> SWORD_STEEL = SWORDS.createChild("steel", STEEL_SWORD);
    public static final TagKey<Item> SHOVEL_STEEL = SHOVELS.createChild("steel", STEEL_SHOVEL);
    public static final TagKey<Item> PICKAXE_STEEL = PICKAXES.createChild("steel", STEEL_PICKAXE);
    public static final TagKey<Item> AXE_STEEL = AXES.createChild("steel", STEEL_AXE);
    public static final TagKey<Item> HOE_STEEL = HOES.createChild("steel", STEEL_HOE);
    public static final TagKey<Item> BOOT_STEEL = BOOTS.createChild("steel", STEEL_BOOTS);
    public static final TagKey<Item> LEGGINGS_STEEL = LEGGINGS.createChild("steel", STEEL_LEGGINGS);
    public static final TagKey<Item> CHESTPLATE_STEEL = CHESTPLATES.createChild("steel", STEEL_CHESTPLATE);
    public static final TagKey<Item> HELMET_STEEL = HELMETS.createChild("steel", STEEL_HELMET);

    public static final TagKey<Item> INGOT_URANIUM = INGOTS.createChild("uranium", URANIUM_INGOT);
    public static final TagKey<Item> AXE_URANIUM = AXES.createChild("uranium", URANIUM_AXE);
    public static final TagKey<Item> HOE_URANIUM = HOES.createChild("uranium", URANIUM_HOE);
    public static final TagKey<Item> PICKAXE_URANIUM = PICKAXES.createChild("uranium", URANIUM_PICKAXE);
    public static final TagKey<Item> SHOVEL_URANIUM = SHOVELS.createChild("uranium", URANIUM_SHOVEL);
    public static final TagKey<Item> SWORD_URANIUM = SWORDS.createChild("uranium", URANIUM_SWORD);
    public static final TagKey<Item> BOOTS_URANIUM = BOOTS.createChild("uranium", URANIUM_BOOTS);
    public static final TagKey<Item> CHESTPLATE_URANIUM = CHESTPLATES.createChild("uranium", URANIUM_CHESTPLATE);
    public static final TagKey<Item> HELMET_URANIUM = HELMETS.createChild("uranium", URANIUM_HELMET);
    public static final TagKey<Item> LEGGINGS_URANIUM = LEGGINGS.createChild("uranium", URANIUM_LEGGINGS);
    public static final TagKey<Item> DUST_URANIUM = DUSTS.createChild("uranium", URANIUM_DUST);
    public static final TagKey<Item> GEAR_URANIUM = GEARS.createChild("uranium", URANIUM_GEAR);
    public static final TagKey<Item> PLATE_URANIUM = PLATES.createChild("uranium", URANIUM_PLATE);
    public static final TagKey<Item> ROD_URANIUM = RODS.createChild("uranium", URANIUM_ROD);
    public static final TagKey<Item> WIRE_URANIUM = WIRES.createChild("uranium", URANIUM_WIRE);

    public static final TagKey<Item> INGOT_ZIRCONIUM_ALLOY = INGOTS.createChild("zirconium_alloy", ZIRCONIUM_ALLOY);
    public static final TagKey<Item> INGOT_ZIRCONIUM = INGOTS.createChild("zirconium", ZIRCONIUM_INGOT);

    public static final TagKey<Item> MOLD_BLANK = MOLDS.createChild("blank", BLANK_PRESS_MOLD);
    public static final TagKey<Item> MOLD_PLATE = MOLDS.createChild("plate", PLATE_MOLD);
    public static final TagKey<Item> MOLD_GEAR = MOLDS.createChild("gear", GEAR_MOLD);
    public static final TagKey<Item> MOLD_ROD = MOLDS.createChild("rod", ROD_MOLD);
    public static final TagKey<Item> HAMMER_WOODEN = HAMMERS.createChild("wooden", WOODEN_HAMMER);
    public static final TagKey<Item> DUSTS_COAL = DUSTS.createChild("coal", COAL_DUST);

    public static void staticInit() {
    }
}
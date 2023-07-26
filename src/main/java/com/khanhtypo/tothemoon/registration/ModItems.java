package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.common.item.BasicArmorItem;
import com.khanhtypo.tothemoon.common.item.ModArmorMaterials;
import com.khanhtypo.tothemoon.common.item.ModToolTiers;
import com.khanhtypo.tothemoon.common.item.ToolItem;
import com.khanhtypo.tothemoon.common.item.hammer.HammerItem;
import com.khanhtypo.tothemoon.common.item.hammer.HammerLevel;
import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import com.khanhtypo.tothemoon.registration.elements.BasicItem;
import net.minecraft.world.item.*;

public class ModItems {
    public static final BasicItem CPU_CHIP = new BasicItem("cpu_chip");
    public static final BasicItem AMETHYST_DUST = new BasicItem("amethyst_dust");
    public static final BasicItem AMETHYST_GEAR = new BasicItem("amethyst_gear");
    public static final BasicItem AMETHYST_PLATE = new BasicItem("amethyst_plate");
    public static final BasicItem AMETHYST_ROD = new BasicItem("amethyst_rod");

    public static final BasicItem COPPER_DUST = new BasicItem("copper_dust");
    public static final BasicItem COPPER_GEAR = new BasicItem("copper_gear");
    public static final BasicItem COPPER_NUGGET = new BasicItem("copper_nugget");
    public static final BasicItem COPPER_PLATE = new BasicItem("copper_plate");
    public static final BasicItem COPPER_ROD = new BasicItem("copper_rod");
    public static final BasicItem COPPER_WIRE = new BasicItem("copper_wire");

    public static final BasicItem DIAMOND_DUST = new BasicItem("diamond_dust");
    public static final BasicItem DIAMOND_GEAR = new BasicItem("diamond_gear");
    public static final ObjectSupplier<HammerItem> DIAMOND_HAMMER = HammerLevel.DIAMOND.getOrRegisterItem();
    public static final BasicItem DIAMOND_PLATE = new BasicItem("diamond_plate");
    public static final BasicItem DIAMOND_ROD = new BasicItem("diamond_rod");

    public static final BasicItem EMERALD_DUST = new BasicItem("emerald_dust");
    public static final BasicItem EMERALD_GEAR = new BasicItem("emerald_gear");
    public static final BasicItem EMERALD_PLATE = new BasicItem("emerald_plate");
    public static final BasicItem EMERALD_ROD = new BasicItem("emerald_rod");

    public static final BasicItem GOLD_DUST = new BasicItem("gold_dust");
    public static final BasicItem GOLD_GEAR = new BasicItem("gold_gear");
    public static final BasicItem GOLD_PLATE = new BasicItem("gold_plate");
    public static final BasicItem GOLD_ROD = new BasicItem("gold_rod");
    public static final BasicItem GOLD_WIRE = new BasicItem("gold_wire");

    public static final BasicItem IRON_DUST = new BasicItem("iron_dust");
    public static final BasicItem IRON_GEAR = new BasicItem("iron_gear");
    public static final BasicItem IRON_PLATE = new BasicItem("iron_plate");
    public static final BasicItem IRON_ROD = new BasicItem("iron_rod");
    public static final BasicItem IRON_WIRE = new BasicItem("iron_wire");

    public static final BasicItem LAPIS_DUST = new BasicItem("lapis_dust");
    public static final BasicItem LAPIS_GEAR = new BasicItem("lapis_gear");
    public static final BasicItem LAPIS_PLATE = new BasicItem("lapis_plate");
    public static final BasicItem LAPIS_ROD = new BasicItem("lapis_rod");

    public static final BasicItem NETHERITE_DUST = new BasicItem("netherite_dust");
    public static final BasicItem NETHERITE_GEAR = new BasicItem("netherite_gear");
    public static final ObjectSupplier<HammerItem> NETHERITE_HAMMER = HammerLevel.NETHERITE.getOrRegisterItem();
    public static final BasicItem NETHERITE_PLATE = new BasicItem("netherite_plate");
    public static final BasicItem NETHERITE_ROD = new BasicItem("netherite_rod");

    public static final BasicItem PURIFIED_QUARTZ = new BasicItem("purified_quartz");
    public static final BasicItem QUARTZ_DUST = new BasicItem("quartz_dust");
    public static final BasicItem QUARTZ_GEAR = new BasicItem("quartz_gear");
    public static final BasicItem QUARTZ_PLATE = new BasicItem("quartz_plate");
    public static final BasicItem QUARTZ_ROD = new BasicItem("quartz_rod");

    public static final BasicItem RAW_URANIUM_CHUNK = new BasicItem("raw_uranium_chunk");
    public static final BasicItem RAW_ZIRCONIUM = new BasicItem("raw_zirconium");

    public static final BasicItem REDSTONE_METAL_DUST = new BasicItem("redstone_metal_dust");
    public static final BasicItem REDSTONE_METAL_GEAR = new BasicItem("redstone_metal_gear");
    public static final BasicItem REDSTONE_METAL_INGOT = new BasicItem("redstone_metal_ingot");
    public static final BasicItem REDSTONE_METAL_PLATE = new BasicItem("redstone_metal_plate");
    public static final BasicItem REDSTONE_METAL_ROD = new BasicItem("redstone_metal_rod");
    public static final BasicItem REDSTONE_METAL_WIRE = new BasicItem("redstone_metal_wire");

    public static final BasicArmorItem REDSTONE_STEEL_BOOTS = new BasicArmorItem(ModArmorMaterials.REDSTONE_STEEL, ArmorItem.Type.BOOTS);
    public static final BasicArmorItem REDSTONE_STEEL_CHESTPLATE = new BasicArmorItem(ModArmorMaterials.REDSTONE_STEEL, ArmorItem.Type.CHESTPLATE);
    public static final BasicArmorItem REDSTONE_STEEL_HELMET = new BasicArmorItem(ModArmorMaterials.REDSTONE_STEEL, ArmorItem.Type.HELMET);
    public static final BasicArmorItem REDSTONE_STEEL_LEGGINGS = new BasicArmorItem(ModArmorMaterials.REDSTONE_STEEL, ArmorItem.Type.LEGGINGS);
    public static final BasicItem REDSTONE_STEEL_DUST = new BasicItem("redstone_steel_alloy_dust");
    public static final BasicItem REDSTONE_STEEL_GEAR = new BasicItem("redstone_steel_alloy_gear");
    public static final BasicItem REDSTONE_STEEL_INGOT = new BasicItem("redstone_steel_alloy_ingot");
    public static final BasicItem REDSTONE_STEEL_PLATE = new BasicItem("redstone_steel_alloy_plate");
    public static final BasicItem REDSTONE_STEEL_ROD = new BasicItem("redstone_steel_alloy_rod");
    public static final BasicItem REDSTONE_STEEL_WIRE = new BasicItem("redstone_steel_alloy_wire");

    public static final BasicItem STEEL_INGOT = new BasicItem("steel_ingot");
    public static final BasicItem STEEL_DUST = new BasicItem("steel_dust");
    public static final BasicItem STEEL_GEAR = new BasicItem("steel_gear");
    public static final BasicItem STEEL_PLATE = new BasicItem("steel_plate");
    public static final BasicItem STEEL_ROD = new BasicItem("steel_rod");
    public static final BasicItem STEEL_WIRE = new BasicItem("steel_wire");
    public static final ObjectSupplier<HammerItem> STEEL_HAMMER = HammerLevel.STEEL.getOrRegisterItem();
    public static final ToolItem STEEL_SWORD = new ToolItem("steel_sword", () -> new SwordItem(ModToolTiers.STEEL, 3, -2.4f, new Item.Properties()));
    public static final ToolItem STEEL_SHOVEL = new ToolItem("steel_shovel", () -> new ShovelItem(ModToolTiers.STEEL,3, 1.5f, new Item.Properties()));
    public static final ToolItem STEEL_PICKAXE = new ToolItem("steel_pickaxe", () -> new PickaxeItem(ModToolTiers.STEEL, 1, -2.8f, new Item.Properties()));
    public static final ToolItem STEEL_AXE = new ToolItem("steel_axe", () -> new AxeItem(ModToolTiers.STEEL, 5.5f, -3.0f, new Item.Properties()));
    public static final ToolItem STEEL_HOE = new ToolItem("steel_hoe", () -> new HoeItem(ModToolTiers.STEEL, -3, 0, new Item.Properties()));
    public static final BasicArmorItem STEEL_BOOTS = new BasicArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.BOOTS);
    public static final BasicArmorItem STEEL_LEGGINGS = new BasicArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.LEGGINGS);
    public static final BasicArmorItem STEEL_CHESTPLATE = new BasicArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.CHESTPLATE);
    public static final BasicArmorItem STEEL_HELMET = new BasicArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.HELMET);

    public static final BasicItem URANIUM_ARMOR_PLATE = new BasicItem("uranium_armor_plate");
    public static final BasicItem URANIUM_INGOT = new BasicItem("uranium_ingot");
    public static final ToolItem URANIUM_AXE = new ToolItem("uranium_axe", () -> new AxeItem(ModToolTiers.URANIUM, 5, -3, new Item.Properties()));
    public static final ToolItem URANIUM_HOE = new ToolItem("uranium_hoe", () -> new HoeItem(ModToolTiers.URANIUM, -2, -1, new Item.Properties()));
    public static final ToolItem URANIUM_PICKAXE = new ToolItem("uranium_pickaxe", () -> new PickaxeItem(ModToolTiers.URANIUM, 1, -2.8f, new Item.Properties()));
    public static final ToolItem URANIUM_SHOVEL = new ToolItem("uranium_shovel", () -> new ShovelItem(ModToolTiers.URANIUM, 1.5f, -3, new Item.Properties()));
    public static final ToolItem URANIUM_SWORD = new ToolItem("uranium_sword", () -> new SwordItem(ModToolTiers.URANIUM, -2,4f, new Item.Properties()));
    public static final BasicArmorItem URANIUM_BOOTS = new BasicArmorItem(ModArmorMaterials.URANIUM, ArmorItem.Type.BOOTS);
    public static final BasicArmorItem URANIUM_CHESTPLATE = new BasicArmorItem(ModArmorMaterials.URANIUM, ArmorItem.Type.CHESTPLATE);
    public static final BasicArmorItem URANIUM_HELMET = new BasicArmorItem(ModArmorMaterials.URANIUM, ArmorItem.Type.HELMET);
    public static final BasicArmorItem URANIUM_LEGGINGS = new BasicArmorItem(ModArmorMaterials.URANIUM, ArmorItem.Type.LEGGINGS);
    public static final BasicItem URANIUM_DUST = new BasicItem("uranium_dust");
    public static final BasicItem URANIUM_GEAR = new BasicItem("uranium_gear");
    public static final BasicItem URANIUM_PLATE = new BasicItem("uranium_plate");
    public static final BasicItem URANIUM_ROD = new BasicItem("uranium_rod");
    public static final BasicItem URANIUM_WIRE = new BasicItem("uranium_wire");

    public static final BasicItem ZIRCONIUM_PURE = new BasicItem("pure_zirconium");
    public static final BasicItem ZIRCONIUM_ALLOY = new BasicItem("zirconium_alloy");
    public static final BasicItem ZIRCONIUM_INGOT = new BasicItem("zirconium_ingot");

    public static final BasicItem BLANK_PRESS_MOLD = new BasicItem("blank_mold");
    public static final BasicItem GEAR_MOLD = new BasicItem("gear_mold");
    public static final BasicItem PLATE_MOLD = new BasicItem("plate_mold");
    public static final BasicItem ROD_MOLD = new BasicItem("rod_mold");
    public static final BasicItem PROCESSOR_CHIP = new BasicItem("processor_chip");
    public static final BasicItem CIRCUIT_BOARD = new BasicItem("circuit_board");
    public static final BasicItem COAL_DUST = new BasicItem("coal_dust");
    public static final BasicItem HEATED_COAL_DUST = new BasicItem("heated_coal_dust");
    public static final ObjectSupplier<HammerItem> WOODEN_HAMMER = HammerLevel.WOODEN.getOrRegisterItem();
    //TODO
    public static final BasicItem GUIDE_BOOK = new BasicItem("guide_book");

    static void staticInit() {}
}

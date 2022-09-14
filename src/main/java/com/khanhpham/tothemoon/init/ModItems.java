package com.khanhpham.tothemoon.init;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.items.*;
import com.khanhpham.tothemoon.core.items.tool.ModArmorMaterial;
import com.khanhpham.tothemoon.core.items.tool.ModToolTiers;
import com.khanhpham.tothemoon.utils.helpers.RegistryEntries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

//Just want to add it here so my IDEA don't give me some stupid warnings
@SuppressWarnings({"unused", "deprecation"})
public class ModItems {
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, ToTheMoon.MOD_ID);
    public static final Item.Properties GENERAL_PROPERTIES = new Item.Properties().tab(ToTheMoon.TAB);
    public static final Table<ModToolTiers.ToolType, Tier, RegistryObject<? extends TieredItem>> ALL_TOOLS = HashBasedTable.create();
    @Deprecated
    public static final HashBasedTable<EquipmentSlot, ModArmorMaterial, RegistryObject<ModArmorItem>> ALL_ARMORS = HashBasedTable.create();

    //METAL PRESS MOLDS
    public static final RegistryObject<Item> BLANK_MOLD = register("blank_mold");
    public static final RegistryObject<Item> GEAR_MOLD = register("gear_mold");
    public static final RegistryObject<Item> PLATE_MOLD = register("plate_mold");
    public static final RegistryObject<Item> ROD_MOLD = register("rod_mold");
    //OTHERS
    public static final RegistryObject<Item> HEATED_COAL_DUST = register("heated_coal_dust");
    public static final RegistryObject<Item> PURIFIED_QUARTZ = register("purified_quartz");
    public static final RegistryObject<Item> COAL_DUST = register("coal_dust");
    public static final RegistryObject<Item> RAW_URANIUM_ORE = register("raw_uranium_ore");
    public static final RegistryObject<Item> CIRCUIT_BOARD = register("circuit_board");
    public static final RegistryObject<Item> CPU_CHIP = register("processor_chip");
    public static final RegistryObject<Item> URANIUM_ARMOR_PLATING = register("uranium_armor_plating");
    public static final CraftingMaterial URANIUM_MATERIAL = new CraftingMaterial(ITEM_DEFERRED_REGISTER, "uranium").setIngot().storageBlock(ModBlocks.URANIUM_BLOCK).setRawMaterial(ModBlocks.RAW_URANIUM_BLOCK).setWire().setArmor(ModArmorMaterial.URANIUM, URANIUM_ARMOR_PLATING);
    public static final RegistryObject<HammerItem> WOODEN_HAMMER = registerHammer("wooden_hammer", 8);
    public static final RegistryObject<HammerItem> STEEL_HAMMER = registerHammer("steel_hammer", 16);
    public static final RegistryObject<HammerItem> DIAMOND_HAMMER = registerHammer("diamond_hammer", 32);
    public static final RegistryObject<HammerItem> NETHERITE_HAMMER = register("netherite_hammer", () -> new HammerItem(new Item.Properties().tab(ToTheMoon.TAB).durability(256).fireResistant()) {
        @Override
        @Nonnull
        public Rarity getRarity(@Nonnull ItemStack pStack) {
            return Rarity.UNCOMMON;
        }
    });
    //public static final RegistryObject<Item> STEEL_PLATE = register("steel_plate");
    //public static final RegistryObject<Item> STEEL_INGOT = register("steel_ingot");
    //public static final RegistryObject<Item> STEEL_DUST = register("steel_dust");
    //public static final RegistryObject<HandheldItem> STEEL_ROD = registerHandheld("steel_rod");
    //public static final RegistryObject<GearItem> STEEL_GEAR = registerGear("steel_gear");
    //public static final RegistryObject<HandheldItem> STEEL_WIRE = registerHandheld("steel_rod");
    public static final CraftingMaterial STEEL_MATERIAL = new CraftingMaterial(ITEM_DEFERRED_REGISTER, "steel").setIngot().setSheetmetal(ModBlocks.STEEL_SHEET_BLOCK).setWire().storageBlock(ModBlocks.STEEL_BLOCK).setArmor(ModArmorMaterial.STEEL);
    //public static final RegistryObject<Item> REDSTONE_STEEL_ALLOY = register("redstone_steel_alloy");
    //public static final RegistryObject<GearItem> REDSTONE_STEEL_ALLOY_GEAR = registerGear("redstone_steel_alloy_gear");
    //public static final RegistryObject<Item> REDSTONE_STEEL_ALLOY_PLATE = register("redstone_steel_alloy_plate");
    //public static final RegistryObject<Item> REDSTONE_STEEL_ALLOY_DUST = register("redstone_steel_alloy_dust");
    //public static final RegistryObject<HandheldItem> REDSTONE_STEEL_ALLOY_ROD = registerHandheld("redstone_steel_alloy_rod");
    //public static final RegistryObject<HandheldItem> REDSTONE_STEEL_ALLOY_WIRE = registerHandheld("redstone_steel_alloy_wire");
    public static final CraftingMaterial REDSTONE_STEEL_MATERIALS = new CraftingMaterial(ITEM_DEFERRED_REGISTER, "redstone_steel_alloy").setIngot().setWire().setSheetmetal(ModBlocks.REDSTONE_STEEL_ALLOY_SHEET_BLOCK).storageBlock(ModBlocks.REDSTONE_STEEL_ALLOY_BLOCK).setArmor(ModArmorMaterial.REDSTONE_STEEL);
    //public static final RegistryObject<Item> REDSTONE_METAL = register("redstone_metal");
    //public static final RegistryObject<Item> REDSTONE_METAL_PLATE = register("redstone_metal_plate");
    //public static final RegistryObject<GearItem> REDSTONE_METAL_GEAR = registerGear("redstone_metal_gear");
    //public static final RegistryObject<HandheldItem> REDSTONE_METAL_ROD = registerHandheld("redstone_metal_rod");
    //public static final RegistryObject<HandheldItem> REDSTONE_METAL_WIRE = registerHandheld("redstone_metal_wire");
    //public static final RegistryObject<Item> REDSTONE_METAL_DUST = register("redstone_metal_dust");
    public static final CraftingMaterial REDSTONE_METAL_MATERIAL = new CraftingMaterial(ITEM_DEFERRED_REGISTER, "redstone_metal").setIngot().setWire().storageBlock(ModBlocks.REDSTONE_METAL_BLOCK);
    //public static final RegistryObject<Item> URANIUM_INGOT = register("uranium_ingot");
    //public static final RegistryObject<Item> URANIUM_DUST = register("uranium_dust");
    //public static final RegistryObject<Item> URANIUM_PLATE = register("uranium_plate");
    //public static final RegistryObject<GearItem> URANIUM_GEAR = registerGear("uranium_gear");
    //public static final RegistryObject<HandheldItem> URANIUM_ROD = registerHandheld("uranium_rod");
    //public static final RegistryObject<HandheldItem> URANIUM_WIRE = registerHandheld("uranium_wire");
    public static final CraftingMaterial COPPER_MATERIAL = new CraftingMaterial(ITEM_DEFERRED_REGISTER, "copper").setVanillaIngot(Items.COPPER_INGOT).setSheetmetal(ModBlocks.COPPER_SHEET_BLOCK).setWire();
    //public static final RegistryObject<Item> COPPER_PLATE = register("copper_plate");
    //public static final RegistryObject<Item> COPPER_DUST = register("copper_dust");
    //public static final RegistryObject<GearItem> COPPER_GEAR = registerGear("copper_gear");
    //public static final RegistryObject<HandheldItem> COPPER_ROD = registerHandheld("copper_rod");
    //public static final RegistryObject<HandheldItem> COPPER_WIRE = registerHandheld("copper_wire");

    public static final CraftingMaterial IRON_MATERIAL = new CraftingMaterial(ITEM_DEFERRED_REGISTER, "iron").setVanillaIngot(Items.IRON_INGOT).setSheetmetal(ModBlocks.IRON_SHEET_BLOCK).setWire();
    //public static final RegistryObject<Item> IRON_PLATE = register("iron_plate");
    //public static final RegistryObject<Item> IRON_DUST = register("iron_dust");
    //public static final RegistryObject<GearItem> IRON_GEAR = registerGear("iron_gear");
    //public static final RegistryObject<HandheldItem> IRON_ROD = registerHandheld("iron_rod");
    //public static final RegistryObject<HandheldItem> IRON_WIRE = registerHandheld("iron_wire");

    public static final CraftingMaterial GOLD_MATERIAL = new CraftingMaterial(ITEM_DEFERRED_REGISTER, "gold").setVanillaIngot(Items.GOLD_INGOT).setSheetmetal(ModBlocks.GOLD_SHEET_BLOCK).setWire();
    //public static final RegistryObject<Item> GOLD_PLATE = register("gold_plate");
    //public static final RegistryObject<Item> GOLD_DUST = register("gold_dust");
    //public static final RegistryObject<GearItem> GOLD_GEAR = registerGear("gold_gear");
    //public static final RegistryObject<HandheldItem> GOLD_ROD = registerHandheld("gold_rod");
    //public static final RegistryObject<HandheldItem> GOLD_WIRE = registerHandheld("gold_wire");

    public static final CraftingMaterial AMETHYST_MATERIAL = new CraftingMaterial(ITEM_DEFERRED_REGISTER, "amethyst").setGem(Items.AMETHYST_SHARD);
    public static final CraftingMaterial DIAMOND_MATERIAL = new CraftingMaterial(ITEM_DEFERRED_REGISTER, "diamond").setGem(Items.DIAMOND).setOres(Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE);
    public static final CraftingMaterial EMERALD_MATERIAL = new CraftingMaterial(ITEM_DEFERRED_REGISTER, "emerald").setGem(Items.EMERALD).setOres(Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE);
    public static final CraftingMaterial LAPIS_MATERIAL = new CraftingMaterial(ITEM_DEFERRED_REGISTER, "lapis").setGem(Items.LAPIS_LAZULI).setOres(Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE);
    public static final CraftingMaterial NETHERITE_MATERIAL = new CraftingMaterial(ITEM_DEFERRED_REGISTER, "netherite");
    public static final CraftingMaterial QUARTZ_MATERIAL = new CraftingMaterial(ITEM_DEFERRED_REGISTER, "quartz").setGem(Items.QUARTZ);

    static {
        for (ModToolTiers.ToolType toolType : ModToolTiers.ToolType.values()) {
            for (ModToolTiers.Tier materialType : new ModToolTiers.Tier[]{ModToolTiers.STEEL, ModToolTiers.URANIUM}) {
                String name = materialType.name().toLowerCase() + "_" + toolType.toString().toLowerCase();
                ALL_TOOLS.put(toolType, materialType.tier(), register(name, () -> toolType.toItem(materialType.tier(), new Item.Properties().tab(ToTheMoon.TAB).stacksTo(1))));
            }
        }

        for (int i = 1; i < 3; i++) {
            int level = i;
            register("speed_upgrade_tier_" + i, () -> new UpgradeItem.SpeedUpgrade(level));
            register("energy_upgrade_tier_" + i, () -> new UpgradeItem.EnergyUpgrade(level));
        }
    }

    private ModItems() {
    }

    public static Item.Properties defaultProperties() {
        return new Item.Properties().tab(ToTheMoon.TAB);
    }

    public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return ITEM_DEFERRED_REGISTER.register(name, supplier);
    }

    private static RegistryObject<HandheldItem> registerHandheld(String name) {
        return register(name, () -> new HammerItem(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    private static RegistryObject<GearItem> registerGear(String name) {
        return register(name, () -> new GearItem(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    public static RegistryObject<HammerItem> registerHammer(String name, int durability) {
        return register(name, () -> new HammerItem(durability));
    }

    public static RegistryObject<Item> register(String name) {
        return register(name, () -> new Item(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    public static void start() {
    }

    @Nonnull
    public static ResourceLocation getRegistryName(Block block) {
        return RegistryEntries.getKeyFrom(block);
    }
}

package com.khanhpham.tothemoon.init;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;
import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.items.GearItem;
import com.khanhpham.tothemoon.core.items.HammerItem;
import com.khanhpham.tothemoon.core.items.HandheldItem;
import com.khanhpham.tothemoon.core.items.UpgradeItem;
import com.khanhpham.tothemoon.core.items.tool.ModArmorMaterial;
import com.khanhpham.tothemoon.core.items.tool.ModToolTiers;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

//Just want to add it here so my IDEA don't give me some stupid warnings
@SuppressWarnings("unused")
public class ModItems {
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Names.MOD_ID);
    //METAL PRESS MOLDS
    public static final RegistryObject<Item> BLANK_MOLD = mold("blank");
    public static final RegistryObject<Item> GEAR_MOLD = mold("gear");
    public static final RegistryObject<Item> PLATE_MOLD = mold("plate");
    public static final RegistryObject<Item> ROD_MOLD = mold("rod");
    //OTHERS
    public static final RegistryObject<Item> PURIFIED_QUARTZ = item("purified_quartz");
    public static final RegistryObject<Item> COAL_DUST = item("coal_dust");
    public static final RegistryObject<Item> RAW_URANIUM_ORE = item("raw_uranium_ore");
    public static final RegistryObject<Item> CIRCUIT_BOARD = item("circuit_board");
    public static final RegistryObject<Item> CPU_CHIP = item("processor_chip");
    public static final RegistryObject<HammerItem> WOODEN_HAMMER = registerHammer("wooden_hammer", 16);
    public static final RegistryObject<HammerItem> STEEL_HAMMER = registerHammer("steel_hammer", 64);
    public static final RegistryObject<HammerItem> DIAMOND_HAMMER = registerHammer("diamond_hammer", 128);
    public static final RegistryObject<HammerItem> NETHERITE_HAMMER = register("netherite_hammer", () -> new HammerItem(new Item.Properties().tab(ToTheMoon.TAB).defaultDurability(256).fireResistant().setNoRepair()) {
        @Override
        @Nonnull
        public Rarity getRarity(@Nonnull ItemStack pStack) {
            return Rarity.UNCOMMON;
        }
    });
    public static final Item.Properties GENERAL_PROPERTIES = new Item.Properties().tab(ToTheMoon.TAB);
    /*public static final RegistryObject<PickaxeItem> STEEL_PICKAXE = register("steel_pickaxe", () -> new PickaxeItem(ModToolTiers.STEEL, 1, -2.8f, GENERAL_PROPERTIES));
    public static final RegistryObject<HoeItem> STEEL_HOE = register("steel_hoe", () -> new HoeItem(ModToolTiers.STEEL, -2, -0.8f, GENERAL_PROPERTIES));
    public static final RegistryObject<AxeItem> STEEL_AXE = register("steel_axe", () -> new AxeItem(ModToolTiers.STEEL, 6.0f, -2.8f, GENERAL_PROPERTIES));
    public static final RegistryObject<SwordItem> STEEL_SWORD = register("steel_sword", () -> new SwordItem(ModToolTiers.STEEL, 3, -2.0f, GENERAL_PROPERTIES));
    public static final RegistryObject<ShovelItem> STEEL_SHOVEL = register("steel_shovel", () -> new ShovelItem(ModToolTiers.STEEL, 1.5f, -2.8f, GENERAL_PROPERTIES));
    */
    private static final String S = "steel";
    public static final RegistryObject<Item> STEEL_PLATE = plate(S);
    public static final RegistryObject<Item> STEEL_INGOT = item(S + "_ingot");
    public static final RegistryObject<Item> STEEL_DUST = dust(S);
    public static final RegistryObject<HandheldItem> STEEL_ROD = rod(S);
    public static final RegistryObject<GearItem> STEEL_GEAR = gear(S);
    public static final RegistryObject<HandheldItem> STEEL_WIRE = wire(S);
    private static final String RSA = "redstone_steel_alloy";
    //special ingredient
    public static final RegistryObject<Item> REDSTONE_STEEL_ALLOY = item(RSA);
    public static final RegistryObject<GearItem> REDSTONE_STEEL_ALLOY_GEAR = gear(RSA);
    public static final RegistryObject<Item> REDSTONE_STEEL_ALLOY_PLATE = plate(RSA);
    public static final RegistryObject<Item> REDSTONE_STEEL_ALLOY_DUST = dust(RSA);
    public static final RegistryObject<HandheldItem> REDSTONE_STEEL_ALLOY_ROD = rod(RSA);
    public static final RegistryObject<HandheldItem> REDSTONE_STEEL_ALLOY_WIRE = wire(RSA);
    private static final String RM = "redstone_metal";
    public static final RegistryObject<Item> REDSTONE_METAL = item(RM);
    public static final RegistryObject<Item> REDSTONE_METAL_PLATE = plate(RM);
    public static final RegistryObject<GearItem> REDSTONE_METAL_GEAR = gear(RM);
    public static final RegistryObject<HandheldItem> REDSTONE_METAL_ROD = rod(RM);
    public static final RegistryObject<HandheldItem> REDSTONE_METAL_WIRE = wire(RM);
    public static final RegistryObject<Item> REDSTONE_METAL_DUST = dust(RM);
    private static final String UR = "uranium";
    //common crafting ingredient
    public static final RegistryObject<Item> URANIUM_INGOT = item(UR + "_ingot");
    public static final RegistryObject<Item> URANIUM_DUST = dust(UR);
    public static final RegistryObject<Item> URANIUM_PLATE = plate(UR);
    public static final RegistryObject<GearItem> URANIUM_GEAR = gear(UR);
    public static final RegistryObject<HandheldItem> URANIUM_ROD = rod(UR);
    public static final RegistryObject<HandheldItem> URANIUM_WIRE = wire(UR);
    private static final String C = "copper";
    public static final RegistryObject<Item> COPPER_PLATE = plate(C);
    public static final RegistryObject<Item> COPPER_DUST = dust(C);
    public static final RegistryObject<GearItem> COPPER_GEAR = gear(C);
    public static final RegistryObject<HandheldItem> COPPER_ROD = rod(C);
    public static final RegistryObject<HandheldItem> COPPER_WIRE = wire(C);
    private static final String I = "iron";
    public static final RegistryObject<Item> IRON_PLATE = plate(I);
    public static final RegistryObject<Item> IRON_DUST = dust(I);
    public static final RegistryObject<GearItem> IRON_GEAR = gear(I);
    public static final RegistryObject<HandheldItem> IRON_ROD = rod(I);
    public static final RegistryObject<HandheldItem> IRON_WIRE = wire(I);
    private static final String G = "gold";
    public static final RegistryObject<Item> GOLD_PLATE = plate(G);
    public static final RegistryObject<Item> GOLD_DUST = dust(G);
    public static final RegistryObject<GearItem> GOLD_GEAR = gear(G);
    public static final RegistryObject<HandheldItem> GOLD_ROD = rod(G);
    public static final RegistryObject<HandheldItem> GOLD_WIRE = wire(G);

    public static final Table<ModToolTiers.ToolType, ForgeTier, RegistryObject<? extends TieredItem>> ALL_TOOLS = HashBasedTable.create();
    public static final ImmutableList<RegistryObject<ArmorItem>> ARMORS;

    static {
        for (ModToolTiers.ToolType toolType : ModToolTiers.ToolType.values()) {
            for (ModToolTiers.Tier materialType : new ModToolTiers.Tier[]{ModToolTiers.STEEL, ModToolTiers.URANIUM}) {
                String name = materialType.name().toLowerCase() + "_" + toolType.toString().toLowerCase();
                ALL_TOOLS.put(toolType, materialType.tier(), register(name, () -> toolType.toItem(materialType.tier(), GENERAL_PROPERTIES)));
            }
        }

        Map<String, EquipmentSlot> map = ImmutableMap.of("helmet", EquipmentSlot.HEAD, "chestplate", EquipmentSlot.CHEST, "leggings", EquipmentSlot.LEGS, "boots", EquipmentSlot.FEET);
        ArrayList<RegistryObject<ArmorItem>> armors = new ArrayList<>();
        for (String armor : map.keySet()) {
            for (ModArmorMaterial armorMaterial : ModArmorMaterial.values()) {
                armors.add(register(armorMaterial.toString().toLowerCase() + '_' + armor, () -> new ArmorItem(armorMaterial, Objects.requireNonNull(map.get(armor)), GENERAL_PROPERTIES)));
            }
        }
        ARMORS = ImmutableList.copyOf(armors);

        for (int i = 1; i < 3; i++) {
            int level = i;
            register("speed_upgrade_tier_" + i, () -> new UpgradeItem.SpeedUpgrade(level));
            register("energy_upgrade_tier_" + i, () -> new UpgradeItem.EnergyUpgrade(level));
        }
    }

    private ModItems() {
    }

    private static int itemCount = 0;

    public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> supplier) {
        itemCount++;
        ModUtils.info("Registering [{}] - {}", name, supplier);
        return ITEM_DEFERRED_REGISTER.register(name, supplier);
    }

    public static RegistryObject<HammerItem> registerHammer(String name, int durability) {
        return register(name, () -> new HammerItem(new Item.Properties().tab(ToTheMoon.TAB).defaultDurability(durability).setNoRepair()));
    }

    public static RegistryObject<Item> item(String name) {
        return register(name, () -> new Item(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    public static RegistryObject<Item> mold(String moldType) {
        return register(moldType + "_mold", () -> new Item(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    public static RegistryObject<GearItem> gear(String gearType) {
        return register(gearType + "_gear", () -> new GearItem(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    public static RegistryObject<Item> plate(String plateType) {
        return register(plateType + "_plate", () -> new Item(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    public static RegistryObject<HandheldItem> rod(String rodType) {
        return register(rodType + "_rod", () -> new HandheldItem(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    public static RegistryObject<Item> dust(String dustType) {
        return register(dustType + "_dust", () -> new Item(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    public static RegistryObject<HandheldItem> wire(String wireType) {
        return register(wireType + "_wire", () -> new HandheldItem(new Item.Properties().tab(ToTheMoon.TAB)));

    }

    public static void start() {
    }

    @Nonnull
    public static ResourceLocation getRegistryName(Block block) {
        return Objects.requireNonNull(block.getRegistryName());
    }
}

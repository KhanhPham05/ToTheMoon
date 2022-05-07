package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.items.GearItem;
import com.khanhpham.tothemoon.core.items.HandheldItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Supplier;

//Just want to add it here so my IDEA don't give me some stupid warnings
@SuppressWarnings("unused")
public class ModItems {
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Names.MOD_ID);
    private static final String S = "steel";
    private static final String RSA = "redstone_steel_alloy";
    private static final String RM = "redstone_metal";
    private static final String UR = "uranium";
    private static final String C = "copper";
    private static final String I = "iron";
    private static final String G = "gold";


    //special ingredient
    public static final RegistryObject<Item> REDSTONE_STEEL_ALLOY = item(RSA);
    public static final RegistryObject<GearItem> REDSTONE_STEEL_ALLOY_GEAR = gear(RSA);
    public static final RegistryObject<Item> REDSTONE_STEEL_ALLOY_PLATE = plate(RSA);
    public static final RegistryObject<Item> REDSTONE_STEEL_ALLOY_DUST = dust(RSA);
    public static final RegistryObject<HandheldItem> REDSTONE_STEEL_ALLOY_ROD = rod(RSA);
    public static final RegistryObject<HandheldItem> REDSTONE_STEEL_ALLOY_WIRE = wire(RSA);

    public static final RegistryObject<Item> REDSTONE_METAL = item(RM);
    public static final RegistryObject<Item> REDSTONE_METAL_PLATE = plate(RM);
    public static final RegistryObject<GearItem> REDSTONE_METAL_GEAR = gear(RM);
    public static final RegistryObject<HandheldItem> REDSTONE_METAL_ROD = rod(RM);

    public static final RegistryObject<HandheldItem> REDSTONE_METAL_WIRE = wire(RM);
    public static final RegistryObject<Item> REDSTONE_METAL_DUSTS = dust(RM);

    //common crafting ingredient
    public static final RegistryObject<Item> URANIUM_INGOT = item(UR + "_ingot");
    public static final RegistryObject<Item> URANIUM_DUST = dust(UR);
    public static final RegistryObject<Item> URANIUM_PLATE = plate(UR);
    public static final RegistryObject<GearItem> URANIUM_GEAR = gear(UR);
    public static final RegistryObject<HandheldItem> URANIUM_ROD = rod(UR);
    public static final RegistryObject<HandheldItem> URANIUM_WIRE = wire(UR);

    public static final RegistryObject<Item> COPPER_PLATE = plate(C);
    public static final RegistryObject<Item> COPPER_DUST = dust(C);
    public static final RegistryObject<GearItem> COPPER_GEAR = gear(C);
    public static final RegistryObject<HandheldItem> COPPER_ROD = rod(C);
    public static final RegistryObject<HandheldItem> COPPER_WIRE = wire(C);

    public static final RegistryObject<Item> STEEL_PLATE = plate(S);
    public static final RegistryObject<Item> STEEL_INGOT = item(S + "_ingot");
    public static final RegistryObject<Item> STEEL_DUST = dust(S);
    public static final RegistryObject<HandheldItem> STEEL_ROD = rod(S);
    public static final RegistryObject<GearItem> STEEL_GEAR = gear(S);
    public static final RegistryObject<HandheldItem> STEEL_WIRE = wire(S);

    public static final RegistryObject<Item> IRON_PLATE = plate(I);
    public static final RegistryObject<Item> IRON_DUST = dust(I);
    public static final RegistryObject<GearItem> IRON_GEAR = gear(I);
    public static final RegistryObject<HandheldItem> IRON_ROD = rod(I);
    public static final RegistryObject<HandheldItem> IRON_WIRE = wire(I);

    public static final RegistryObject<Item> GOLD_PLATE = plate(G);
    public static final RegistryObject<Item> GOLD_DUST = dust(G);
    public static final RegistryObject<GearItem> GOLD_GEAR = gear(G);
    public static final RegistryObject<HandheldItem> GOLD_ROD = rod(G);
    public static final RegistryObject<HandheldItem> GOLD_WIRE = wire(G);

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

    public static final RegistryObject<HandheldItem> WOODEN_HAMMER = registerHammer("wooden_hammer", 16);
    public static final RegistryObject<HandheldItem> STEEL_HAMMER = registerHammer("steel_hammer", 64);
    public static final RegistryObject<HandheldItem> DIAMOND_HAMMER = registerHammer("diamond_hammer", 128);
    public static final RegistryObject<HandheldItem> NETHERITE_HAMMER = ITEM_DEFERRED_REGISTER.register("netherite_hammer", () -> new HandheldItem(new Item.Properties().tab(ToTheMoon.TAB).defaultDurability(256).fireResistant().setNoRepair()) {
        @Override
        @Nonnull
        public Rarity getRarity(@Nonnull ItemStack pStack) {
            return Rarity.UNCOMMON;
        }
    });

    private ModItems() {
    }

    public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return ITEM_DEFERRED_REGISTER.register(name, supplier);
    }

    public static RegistryObject<HandheldItem> registerHammer(String name, int durability) {
        return ITEM_DEFERRED_REGISTER.register(name, () -> new HandheldItem(new Item.Properties().tab(ToTheMoon.TAB).defaultDurability(durability).setNoRepair()));
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

    public static void start() {}

    @Nonnull
    public static ResourceLocation getRegistryName(Block block) {
        return Objects.requireNonNull(block.getRegistryName());
    }

    public static void init() {
    }
}

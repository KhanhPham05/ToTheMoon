package com.khanhpham.tothemoon.registries;

import com.khanhpham.tothemoon.api.registration.RegisterItem;
import com.khanhpham.tothemoon.util.Identity;
import com.khanhpham.tothemoon.util.RegistryTypes;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.function.Supplier;

import static com.khanhpham.tothemoon.ToTheMoon.TTM_TAB;

/**
 * @see java.util.function.BiConsumer
 */
public class ModItems {
    public static final RegisterItem<Item> COPPER_INGOT;
    public static final RegisterItem<Item> COPPER_NUGGET;
    public static final RegisterItem<Item> COPPER_DUST;
    public static final RegisterItem<Item> COPPER_SHEET;

    public static final RegisterItem<Item> REDSTONE_STEEL_INGOT;
    public static final RegisterItem<Item> REDSTONE_STEEL_NUGGETS;
    public static final RegisterItem<Item> REDSTONE_STEEL_DUST;
    public static final RegisterItem<Item> REDSTONE_STEEL_SHEET;

    public static final RegisterItem<Item> STEEL_INGOT;
    public static final RegisterItem<Item> STEEL_NUGGET;
    public static final RegisterItem<Item> STEEL_DUST;
    public static final RegisterItem<Item> STEEL_SHEET;

    public static final RegisterItem<Item> STEEL_ENERGY_COIL;
    public static final RegisterItem<Item> REDSTONE_POWERED_ENERGY_COIL;
    public static final RegisterItem<Item> BASIC_ENERGY_COIL;

    public static final RegisterItem<BucketItem> REDSTONE_STEEL_ALLOY_BUCKET;
    public static final RegisterItem<BucketItem> REDSTONE_ALLOY_BUCKET;

    static {
        COPPER_INGOT = register("copper_ingot");
        COPPER_NUGGET = register("copper_nugget");
        COPPER_DUST = register("copper_dust");
        COPPER_SHEET = register("copper_sheet");

        REDSTONE_STEEL_INGOT = register("redstone_steel_ingot");
        REDSTONE_STEEL_NUGGETS = register("redstone_steel_nugget");
        REDSTONE_STEEL_DUST = register("redstone_steel_dust");
        REDSTONE_STEEL_SHEET = register("redstone_steel_sheet");

        STEEL_INGOT = register("steel_ingot");
        STEEL_NUGGET = register("steel_nugget");
        STEEL_DUST = register("steel_dust");
        STEEL_SHEET = register("steel_sheet");
        STEEL_ENERGY_COIL = register("steel_energy_coil");

        REDSTONE_POWERED_ENERGY_COIL = register("redstone_powered_energy_coil");
        BASIC_ENERGY_COIL = register("basic_energy_coil");

        REDSTONE_STEEL_ALLOY_BUCKET = new RegisterItem<>(RegistryTypes.ITEMS.register("redstone_steel_alloy_bucket", () -> new BucketItem(ModFluids.MOLTEN_REDSTONE_STEEL_ALLOY, new Item.Properties().tab(TTM_TAB))));
        REDSTONE_ALLOY_BUCKET = register("redstone_alloy_bucket", () -> new BucketItem(ModFluids.MOLTEN_REDSTONE_ALLOY, new Item.Properties().tab(TTM_TAB)));
    }

    public static void init(IEventBus bus) {
        RegistryTypes.ITEMS.register(bus);
    }

    private static RegisterItem<Item> register(String id) {
        return register(id, () -> new Item(new Item.Properties().tab(TTM_TAB)));
    }

    private static <T extends Item> RegisterItem<T> register(String id, Supplier<T> itemSupplier) {
        Identity.ensureValid(id);
        return new RegisterItem<>(RegistryTypes.ITEMS.register(id, itemSupplier));
    }
}

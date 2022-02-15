package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.utils.ItemRegister;
import com.khanhpham.tothemoon.utils.blocks.TileEntityBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
    public static final ItemRegister ITEMS = new ItemRegister();

    public static final Item URANIUM_INGOT;
    public static final Item URANIUM_DUST;
    public static final Item COPPER_PLATE;
    public static final Item URANIUM_PLATE;
    public static final Item STEEL_PLATE;
    public static final Item STEEL_INGOT;
    public static final Item STEEL_DUST;
    public static final Item STEEL_ROD;
    public static final Item REDSTONE_MACHINE_FRAME;
    public static final Item IRON_PLATE;

    static {
        URANIUM_INGOT = register("uranium_ingot");
        URANIUM_DUST = register("uranium_dust");
        STEEL_DUST = register("steel_dust");
        STEEL_INGOT = register("steel_ingot");
        REDSTONE_MACHINE_FRAME = register("redstone_machine_frame");
        STEEL_ROD = register("steel_rod");
        STEEL_PLATE = register("steel_plate");
        COPPER_PLATE = register("copper_plate");
        URANIUM_PLATE = register("uranium_plate");
        IRON_PLATE = register("iron_plate");
    }

    private ModItems() {}

    private static Item register(String name) {
        return ITEMS.register(name, new Item(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    private static void registerBLockItem(Block block) {
        if (!(block instanceof TileEntityBlock<?>))
            ITEMS.register(block.getRegistryName().getPath(), new BlockItem(block, new Item.Properties().tab(ToTheMoon.TAB)));
        else
            ITEMS.register(block.getRegistryName().getPath(), new BlockItem(block, new Item.Properties().tab(ToTheMoon.TAB).stacksTo(1)));
    }

    @SubscribeEvent
    public static void init(RegistryEvent.Register<Item> event) {
        init(event.getRegistry());
    }

    public static void init(IForgeRegistry<Item> registry) {
        ModBlocks.BLOCK_REGISTER.getRegisteredBlocks().forEach(ModItems::registerBLockItem);
        ITEMS.registerAll(registry);
    }
}

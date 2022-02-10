package com.khanhpham.tothemoon.init;


import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.utils.ItemRegister;
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

    static {
        URANIUM_INGOT = register("uranium_ingot");
        URANIUM_DUST = register("uranium_dust");
    }

    public ModItems() {

    }

    private static Item register(String name) {
        return ITEMS.register(name, new Item(new Item.Properties().tab(ToTheMoon.TAB)));
    }

    private static void registerBLockItem(Block block) {
        ITEMS.register(block.getRegistryName().getPath(), new BlockItem(block, new Item.Properties().tab(ToTheMoon.TAB)));
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

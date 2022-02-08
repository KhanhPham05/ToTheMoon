package com.khanhpham.tothemoon.init;


import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.utils.ItemRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModItems {
    private ModItems() {

    }

    public static final ItemRegister REGISTRY = new ItemRegister();
    public static final Item URANIUM_INGOT;
    public static final Item URANIUM_DUST;

    static {
        URANIUM_INGOT = register("uranium_ingot");
        URANIUM_DUST = register("uranium_dust");
    }

    private static Item register(String name) {
        return REGISTRY.register(name);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> reg = event.getRegistry();
        ModBlocks.BLOCKS.getRegisteredBlocks().forEach(block -> REGISTRY.register(block.getRegistryName().getPath(), new BlockItem(block, new Item.Properties().tab(ToTheMoon.TAB))));
        REGISTRY.registerAll(reg);
    }

}

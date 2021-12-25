package com.khanhpham.ttm.init;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.testfeatures.RegistryHelper;
import com.khanhpham.ttm.utils.RegistryTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = ToTheMoonMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModItems {
    public static final RegistryHelper<Item> ITEMS = new RegistryHelper<>(ToTheMoonMain.MOD_ID);

    public static final Item TTM_ICON = register("icon");

    //--------------------------------- MOD BLOCK ITEM -----------------------------------//

    public ModItems() {
    }

    private static Item register(String name) {
        return ITEMS.register(name, new Item(new Item.Properties().tab(ToTheMoonMain.TTM_TAB)));
    }

    private static Item blockItem(Block block, ResourceLocation rl) {
        Objects.requireNonNull(rl);
        return new BlockItem(block, new Item.Properties().tab(ToTheMoonMain.TTM_TAB)).setRegistryName(rl);
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerEvent(RegistryEvent.Register<Item> event) {
        ToTheMoonMain.LOG.info("{} is fired", event.getName());
        IForgeRegistry<Item> reg = event.getRegistry();
        ModBlocks.BLOCKS.getEntries().forEach(block -> ITEMS.register(Objects.requireNonNull(block.getRegistryName()).getPath(), new BlockItem(block,new Item.Properties().tab(ToTheMoonMain.TTM_TAB))));
        ITEMS.init(reg);
    }
}

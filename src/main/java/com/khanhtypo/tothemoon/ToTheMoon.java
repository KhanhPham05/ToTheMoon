package com.khanhtypo.tothemoon;

import com.khanhtypo.tothemoon.common.TabInstance;
import com.khanhtypo.tothemoon.common.block.Workbench;
import com.khanhtypo.tothemoon.data.DataStarter;
import com.khanhtypo.tothemoon.registration.*;
import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import com.khanhtypo.tothemoon.serverdata.recipes.RecipeTypeObject;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.khanhtypo.tothemoon.ToTheMoon.MODID;

@Mod(MODID)
public class ToTheMoon {
    public static final String MODID = "tothemoon";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final IEventBus FORGE_BUS = MinecraftForge.EVENT_BUS;
    public static final IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
    public static final TabInstance DEFAULT_ITEM_TAB = new TabInstance("ttm_items", "TTM Items", () -> ModItems.CPU_CHIP);
    public static final TabInstance DEFAULT_BLOCK_TAB = new TabInstance("ttm_blocks", "TTM Blocks", () -> ModBlocks.COBBLED_METEORITE);

    public ToTheMoon() {
        FORGE_BUS.register(this);
        ModRegistries.staticInit(MOD_BUS);
        MOD_BUS.addListener(DataStarter::gatherData);
        MOD_BUS.addListener(TabInstance::registerTabs);
        MOD_BUS.addListener(this::registerEvent);
        FORGE_BUS.addListener(Workbench::onBreak);
        MOD_BUS.addListener(ModMenus.ScreenRegister::registerScreen);
        MOD_BUS.addListener(ModStats::registerStats);
    }

    public void registerEvent(RegisterEvent registerEvent) {
        registerEvent.register(ModRegistries.ITEMS.getRegistryKey(), helper -> {
            for (RegistryObject<Block> blocksEntry : ModRegistries.BLOCKS.getEntries()) {
                BlockItem blockItem = new BlockItem(blocksEntry.get(), new Item.Properties());
                helper.register(blocksEntry.getId(), blockItem);
                DEFAULT_BLOCK_TAB.addItem(ObjectSupplier.existedItem(blockItem, blocksEntry.getId()));
            }
        });
    }
}

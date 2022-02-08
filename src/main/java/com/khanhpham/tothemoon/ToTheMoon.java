package com.khanhpham.tothemoon;

import com.khanhpham.tothemoon.core.storageblock.MoonBarrelScreen;
import com.khanhpham.tothemoon.data.ModLanguageProvider;
import com.khanhpham.tothemoon.data.ModModelProvider;
import com.khanhpham.tothemoon.data.ModTagsProvider;
import com.khanhpham.tothemoon.init.ModContainerTypes;
import com.khanhpham.tothemoon.init.ModItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod(value = Names.MOD_ID)
public class ToTheMoon {

    public static final CreativeModeTab TAB = new CreativeModeTab("ttm_creative_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.URANIUM_INGOT);
        }
    };

    public ToTheMoon() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class Registration {

        public Registration() {
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModContainerTypes.STORAGE_BLOCK, MoonBarrelScreen::new);
        }


        /*@SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            ModBlocks.run();
            ModBlocks.registerAll(event.getRegistry());
        }


        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            IForgeRegistry<Item> reg = event.getRegistry();
            ModItems.init().registerAll(reg);
            ModBlocks.BLOCKS.getRegisteredBlocks().forEach(block -> reg.register(new BlockItem(block, new Item.Properties().tab(TAB)).setRegistryName(new ResourceLocation(Names.MOD_ID, Objects.requireNonNull(block.getRegistryName()).getPath()))));
        }

        @SubscribeEvent
        public static void registerBlockEntities(RegistryEvent.Register<BlockEntityType<?>> event) {
            ModTileEntityTypes.init().registerAll(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerContainerTypes(RegistryEvent.Register<MenuType<?>> event) {
            ModContainerTypes.init().registerAll(event.getRegistry());
        }*/

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            DataGenerator data = event.getGenerator();
            ExistingFileHelper fileHelper = event.getExistingFileHelper();

            ModModelProvider models = new ModModelProvider(data, fileHelper);
            data.addProvider(models.blockStates());
            data.addProvider(models.blockModels());
            data.addProvider(models.itemModels());

            ModTagsProvider tags = new ModTagsProvider(data, fileHelper);
            data.addProvider(tags.getBlockTags());

            data.addProvider(new ModLanguageProvider(data));
        }
    }
}

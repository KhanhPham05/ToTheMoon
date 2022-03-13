package com.khanhpham.tothemoon;

import com.khanhpham.tothemoon.core.alloysmelter.AlloySmelterMenuScreen;
import com.khanhpham.tothemoon.core.energygenerator.containerscreens.EnergyGeneratorMenuScreen;
import com.khanhpham.tothemoon.core.storageblock.MoonBarrelScreen;
import com.khanhpham.tothemoon.data.ModLanguageProvider;
import com.khanhpham.tothemoon.data.ModTagsProvider;
import com.khanhpham.tothemoon.data.recipe.ModRecipeProvider;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod(value = Names.MOD_ID)
public class ToTheMoon {

    public static final CreativeModeTab TAB = new CreativeModeTab("ttm_creative_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.URANIUM_INGOT);
        }
    };

    private static final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

    public ToTheMoon() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class CommonEvents {

        public CommonEvents() {
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.STORAGE_BLOCK, MoonBarrelScreen::new);
            MenuScreens.register(ModMenuTypes.ENERGY_GENERATOR_CONTAINER, EnergyGeneratorMenuScreen::new);
            MenuScreens.register(ModMenuTypes.ALLOY_SMELTER, AlloySmelterMenuScreen::new);
        }


        public static void gatherData(GatherDataEvent event) {
            DataGenerator data = event.getGenerator();
            ExistingFileHelper fileHelper = event.getExistingFileHelper();

            data.addProvider(new ModRecipeProvider(data));

            /*ModModelProvider models = new ModModelProvider(data, fileHelper);
            data.addProvider(models.blockStates());
            data.addProvider(models.blockModels());
            data.addProvider(models.itemModels());*/

            ModTagsProvider tags = new ModTagsProvider(data, fileHelper);
            data.addProvider(tags.getBlockTags());

            data.addProvider(new ModLanguageProvider(data));
        }
    }
}

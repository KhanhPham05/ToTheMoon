package com.khanhpham.tothemoon;

import com.khanhpham.tothemoon.core.alloysmelter.AlloySmelterMenuScreen;
import com.khanhpham.tothemoon.core.energygenerator.containerscreens.EnergyGeneratorMenuScreen;
import com.khanhpham.tothemoon.core.storageblock.MoonBarrelScreen;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = Names.MOD_ID)
public class ToTheMoon {

    public static final ResourceKey<Level> MOON_DIMENSION = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Names.MOD_ID, "the_moon_dimension"));

    public static final ResourceKey<Biome> THE_MOON_BIOME = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Names.MOD_ID, "the_moon_biome"));

    public static final Logger LOG = LogManager.getLogger(Names.MOD_ID);
    public static final CreativeModeTab TAB = new CreativeModeTab("ttm_creative_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.URANIUM_INGOT);
        }
    };

    /**
     * @see net.minecraft.core.particles.ParticleTypes
     */
    public ToTheMoon() {
        MinecraftForge.EVENT_BUS.register(this);
        MOON_DIMENSION.cast(Registry.DIMENSION_REGISTRY).ifPresent(LOG::info);
        THE_MOON_BIOME.cast(Registry.BIOME_REGISTRY).ifPresent(LOG::info);
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

            LOG.info(ModBlocks.MODDED_NON_SOLID_BLOCKS);
            ModBlocks.MODDED_NON_SOLID_BLOCKS.forEach(ModBlocks::cutoutRendering);
        }
    }
}

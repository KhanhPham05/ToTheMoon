package com.khanhpham.tothemoon;

import com.khanhpham.tothemoon.core.machines.alloysmelter.AlloySmelterMenuScreen;
import com.khanhpham.tothemoon.core.machines.energygenerator.containerscreens.EnergyGeneratorMenuScreen;
import com.khanhpham.tothemoon.core.machines.metalpress.MetalPressMenuScreen;
import com.khanhpham.tothemoon.core.machines.storageblock.MoonBarrelScreen;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

@Mod(value = Names.MOD_ID)
public class ToTheMoon {
    public static final Logger LOG = LogManager.getLogger(Names.MOD_ID);
    public static final CreativeModeTab TAB = new CreativeModeTab("ttm_creative_tab") {
        @Nonnull
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
    }

    @Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class CommonEvents {

        public CommonEvents() {
        }

        @SubscribeEvent
        public static void onItemRegistration(RegistryEvent.Register<Item> event) throws NoSuchFieldException, IllegalAccessException {

            Field[] fields = ModItems.class.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().equals(Item.class))
                    field.set(ModItems.class.getField(field.getName()), ModItems.register(field.getName().toLowerCase()));
            }

            ModItems.init(event.getRegistry());
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.STORAGE_BLOCK, MoonBarrelScreen::new);
            MenuScreens.register(ModMenuTypes.ENERGY_GENERATOR_CONTAINER, EnergyGeneratorMenuScreen::new);
            MenuScreens.register(ModMenuTypes.ALLOY_SMELTER, AlloySmelterMenuScreen::new);
            MenuScreens.register(ModMenuTypes.METAL_PRESS, MetalPressMenuScreen::new);

            LOG.info(ModBlocks.MODDED_NON_SOLID_BLOCKS);
            ModBlocks.MODDED_NON_SOLID_BLOCKS.forEach(ModBlocks::cutoutRendering);
        }
    }
}

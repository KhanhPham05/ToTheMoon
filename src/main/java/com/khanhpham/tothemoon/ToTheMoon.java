package com.khanhpham.tothemoon;

import com.google.common.collect.Sets;
import com.khanhpham.tothemoon.core.machines.alloysmelter.AlloySmelterMenuScreen;
import com.khanhpham.tothemoon.core.machines.energygenerator.containerscreens.EnergyGeneratorMenuScreen;
import com.khanhpham.tothemoon.core.machines.metalpress.MetalPressMenuScreen;
import com.khanhpham.tothemoon.core.machines.storageblock.MoonBarrelScreen;
import com.khanhpham.tothemoon.core.renderer.TheMoonDimensionEffect;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.ModUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Supplier;

@Mod(value = Names.MOD_ID)
public class ToTheMoon {
    public static final ResourceLocation THE_MOON_SKY_LOCATION = ModUtils.modLoc("textures/environment/the_moon_sky.png");
    public static final ResourceLocation MOON_EFFECTS = ModUtils.modLoc("moon_effects");
    public static final ResourceKey<Level> THE_MOON_DIMENSION = ResourceKey.create(Registry.DIMENSION_REGISTRY, ModUtils.modLoc("the_moon_dimension"));
    public static final Logger LOG = LogManager.getLogger(Names.MOD_ID);
    public static final CreativeModeTab TAB = new CreativeModeTab("ttm_creative_tab") {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.URANIUM_INGOT.get());
        }
    };

    public ToTheMoon() {
        MinecraftForge.EVENT_BUS.register(this);
        DimensionSpecialEffects.EFFECTS.put(MOON_EFFECTS, new TheMoonDimensionEffect());
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        initClasses();
        ModBlocks.BLOCK_DEFERRED_REGISTER.register(bus);
        ModItems.ITEM_DEFERRED_REGISTER.register(bus);
        ModBlockEntityTypes.BE_DEFERRED_REGISTER.register(bus);
    }

    private static void initClasses() {
        ModBlocks.init();
        ModItems.registerItems();
    }


    @Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModEvents {
        private static final HashSet<Block> registeredBlocks = Sets.newHashSet();

        public ModEvents() {
        }

        @SubscribeEvent
        public static void onItemRegistration(RegistryEvent.Register<Item> event) {
            ModBlocks.BLOCK_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).forEach(block -> event.getRegistry().register(new BlockItem(block, new Item.Properties().tab(TAB)).setRegistryName(block.getRegistryName())));
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.STORAGE_BLOCK, MoonBarrelScreen::new);
            MenuScreens.register(ModMenuTypes.ENERGY_GENERATOR_CONTAINER, EnergyGeneratorMenuScreen::new);
            MenuScreens.register(ModMenuTypes.ALLOY_SMELTER, AlloySmelterMenuScreen::new);
            MenuScreens.register(ModMenuTypes.METAL_PRESS, MetalPressMenuScreen::new);

            ModBlocks.MODDED_NON_SOLID_BLOCKS_SUPPLIER.stream().map(RegistryObject::get).forEach(ModBlocks::cutoutMippedRendering);
        }
    }

    @Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static final class ForgeEvents {

        public ForgeEvents() {
        }

        @SubscribeEvent
        public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
            var block = event.getPlacedBlock();
            Level level = Objects.requireNonNull(event.getEntity()).level;

            if (block.getTags().anyMatch(tag -> tag.equals(Tags.Blocks.GLASS)) && level.dimension().equals(THE_MOON_DIMENSION)) {
                level.playSound(null, event.getPos(), SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.destroyBlock(event.getPos(), false);
            }
        }
    }
}

package com.khanhpham.tothemoon;

import com.khanhpham.tothemoon.core.blockentities.bettery.BatteryMenuScreen;
import com.khanhpham.tothemoon.core.blocks.machines.alloysmelter.AlloySmelterMenuScreen;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.containerscreens.EnergyGeneratorMenuScreen;
import com.khanhpham.tothemoon.core.blocks.machines.metalpress.MetalPressMenuScreen;
import com.khanhpham.tothemoon.core.blocks.machines.storageblock.MoonBarrelScreen;
import com.khanhpham.tothemoon.core.blocks.processblocks.metalpressingboard.MetalPressingPlateBlockEntity;
import com.khanhpham.tothemoon.core.renderer.TheMoonDimensionEffect;
import com.khanhpham.tothemoon.datagen.ModItemModels;
import com.khanhpham.tothemoon.datagen.ModLanguage;
import com.khanhpham.tothemoon.datagen.ModTagProvider;
import com.khanhpham.tothemoon.datagen.blocks.ModBlockModels;
import com.khanhpham.tothemoon.datagen.blocks.ModBlockStates;
import com.khanhpham.tothemoon.datagen.loottable.ModLootTables;
import com.khanhpham.tothemoon.datagen.recipes.ModRecipeProvider;
import com.khanhpham.tothemoon.datagen.sounds.ModSoundsProvider;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
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
        //   ModSoundEvents.SOUNDS.register(bus);
        ModBlockEntityTypes.BE_DEFERRED_REGISTER.register(bus);
    }

    private static void initClasses() {
        ModBlocks.init();
        ModItems.start();
    }


    @Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModEvents {

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            DataGenerator data = event.getGenerator();
            ExistingFileHelper fileHelper = event.getExistingFileHelper();

            data.addProvider(new ModSoundsProvider(data));
            data.addProvider(new ModLanguage(data));
            data.addProvider(new ModRecipeProvider(data));
            data.addProvider(new ModBlockModels(data, fileHelper));
            data.addProvider(new ModBlockStates(data, fileHelper));
            data.addProvider(new ModItemModels(data, fileHelper));
            data.addProvider(new ModLootTables(data));

            ModTagProvider tagsProviders = new ModTagProvider(data, fileHelper);
        }

        public ModEvents() {
        }

        @SubscribeEvent
        public static void onItemRegistration(RegistryEvent.Register<Item> event) {
            ModBlocks.BLOCK_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).forEach(block -> event.getRegistry().register(new BlockItem(block, new Item.Properties().tab(TAB)).setRegistryName(ModUtils.modLoc(ModUtils.getNameFromObject(block)))));
        }



        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.STORAGE_BLOCK, MoonBarrelScreen::new);
            MenuScreens.register(ModMenuTypes.ENERGY_GENERATOR_CONTAINER, EnergyGeneratorMenuScreen::new);
            MenuScreens.register(ModMenuTypes.ALLOY_SMELTER, AlloySmelterMenuScreen::new);
            MenuScreens.register(ModMenuTypes.METAL_PRESS, MetalPressMenuScreen::new);
            MenuScreens.register(ModMenuTypes.BATTERY, BatteryMenuScreen::new);

            ModBlocks.MODDED_NON_SOLID_BLOCKS_SUPPLIER.stream().map(Supplier::get).forEach(ModBlocks::cutoutMippedRendering);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ANTI_PRESSURE_GLASS.get(), RenderType.translucent());
        }

        @SubscribeEvent
        public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntityTypes.METAL_PRESSING_PLATE.get(), MetalPressingPlateBlockEntity.Renderer::new);
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

            if (level.dimension().equals(THE_MOON_DIMENSION)) {
                if (block.getTags().anyMatch(tag -> tag.equals(Tags.Blocks.GLASS))) {
                    level.destroyBlock(event.getPos(), false);
                }
                if (block.is(Blocks.WATER)) {
                    level.setBlock(event.getPos(), Blocks.PACKED_ICE.defaultBlockState(), 3);
                }
            }

            if (block.is(ModBlocks.METAL_PRESSING_PLATE.get())) {
                if (level.getBlockState(event.getPos().below()).isAir()) {
                    level.destroyBlock(event.getPos(), true, null);
                }
            }
        }
    }
}

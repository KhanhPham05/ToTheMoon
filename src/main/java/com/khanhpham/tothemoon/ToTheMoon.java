package com.khanhpham.tothemoon;

import com.khanhpham.tothemoon.advancements.ModdedTriggers;
import com.khanhpham.tothemoon.config.TTMConfigs;
import com.khanhpham.tothemoon.core.blocks.BurnableBlock;
import com.khanhpham.tothemoon.core.blocks.HasCustomBlockItem;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryMenuScreen;
import com.khanhpham.tothemoon.core.blocks.machines.alloysmelter.AlloySmelterMenuScreen;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.containerscreens.EnergyGeneratorMenuScreen;
import com.khanhpham.tothemoon.core.blocks.machines.energysmelter.EnergySmelterScreen;
import com.khanhpham.tothemoon.core.blocks.machines.metalpress.MetalPressMenuScreen;
import com.khanhpham.tothemoon.core.blocks.machines.storageblock.MoonBarrelScreen;
import com.khanhpham.tothemoon.core.blocks.processblocks.tagtranslator.TagTranslatorScreen;
import com.khanhpham.tothemoon.core.blocks.tanks.FluidTankMenuScreen;
import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceControllerScreen;
import com.khanhpham.tothemoon.core.renderer.TheMoonDimensionEffect;
import com.khanhpham.tothemoon.datagen.ModItemModels;
import com.khanhpham.tothemoon.datagen.advancement.ModAdvancementProvider;
import com.khanhpham.tothemoon.datagen.blocks.ModBlockModels;
import com.khanhpham.tothemoon.datagen.blocks.ModBlockStates;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.datagen.loottable.ModLootTables;
import com.khanhpham.tothemoon.datagen.recipes.provider.ModRecipeProvider;
import com.khanhpham.tothemoon.datagen.sounds.ModSoundsProvider;
import com.khanhpham.tothemoon.datagen.tags.ModTagProvider;
import com.khanhpham.tothemoon.debug.GetCapInfoCommand;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.init.nondeferred.NonDeferredBlockEntitiesTypes;
import com.khanhpham.tothemoon.init.nondeferred.NonDeferredBlocks;
import com.khanhpham.tothemoon.init.nondeferred.NonDeferredItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.multiblock.MultiblockManager;
import com.khanhpham.tothemoon.worldgen.OreVeins;
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
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

        initRegistration();
        ModBlocks.BLOCK_DEFERRED_REGISTER.register(bus);
        ModItems.ITEM_DEFERRED_REGISTER.register(bus);
        ModBlockEntities.BE_DEFERRED_REGISTER.register(bus);

        TTMConfigs.registerConfigs(bus, ModLoadingContext.get());
        new MultiblockManager();
    }

    private static void initRegistration() {
        ModBlocks.init();
        ModItems.start();
        ModBlockEntities.init();

    }


    @Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModEvents {

        public ModEvents() {
        }

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            DataGenerator data = event.getGenerator();
            ExistingFileHelper fileHelper = event.getExistingFileHelper();

            data.addProvider(new ModAdvancementProvider(data, fileHelper));
            data.addProvider(new ModSoundsProvider(data));
            data.addProvider(new ModLanguage(data));
            data.addProvider(new ModRecipeProvider(data));
            data.addProvider(new ModBlockModels(data, fileHelper));
            data.addProvider(new ModBlockStates(data, fileHelper));
            data.addProvider(new ModItemModels(data, fileHelper));
            data.addProvider(new ModLootTables(data));

            new ModTagProvider(data, fileHelper);
        }

        @SubscribeEvent
        public static void onItemRegistration(RegistryEvent.Register<Item> event) {
            IForgeRegistry<Item> reg = event.getRegistry();
            NonDeferredItems.registerAll(reg);

            Set<? extends Block> blocks = ModBlocks.BLOCK_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).collect(Collectors.toSet());
            for (Block block : blocks) {
                if (block instanceof BurnableBlock burnableBlock) {
                    reg.register(new BlockItem(burnableBlock, new Item.Properties().tab(ToTheMoon.TAB)) {
                        @Override
                        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                            return burnableBlock.getBurningTime();
                        }
                    }.setRegistryName(ModUtils.modLoc(ModUtils.getPath(burnableBlock))));
                } else if (block instanceof HasCustomBlockItem item) {
                    reg.register(item.getItem(block.getRegistryName()));
                } else
                    reg.register(new BlockItem(block, new Item.Properties().tab(ToTheMoon.TAB)).setRegistryName(ModUtils.modLoc(ModUtils.getPath(block))));
            }
        }

        @SubscribeEvent
        public static void onBlockRegistration(RegistryEvent.Register<Block> event) {
            NonDeferredBlocks.registerAll(event.getRegistry());
        }

        @SubscribeEvent
        public static void onBlockEntityRegistration(RegistryEvent.Register<BlockEntityType<?>> event) {
            NonDeferredBlockEntitiesTypes.registerAll(event.getRegistry());
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.STORAGE_BLOCK, MoonBarrelScreen::new);
            MenuScreens.register(ModMenuTypes.ENERGY_GENERATOR_CONTAINER, EnergyGeneratorMenuScreen::new);
            MenuScreens.register(ModMenuTypes.ALLOY_SMELTER, AlloySmelterMenuScreen::new);
            MenuScreens.register(ModMenuTypes.METAL_PRESS, MetalPressMenuScreen::new);
            MenuScreens.register(ModMenuTypes.BATTERY, BatteryMenuScreen::new);
            MenuScreens.register(ModMenuTypes.ENERGY_SMELTER, EnergySmelterScreen::new);
            MenuScreens.register(ModMenuTypes.NETHER_BRICK_FURNACE, NetherBrickFurnaceControllerScreen::new);
            MenuScreens.register(ModMenuTypes.FLUID_TANK, FluidTankMenuScreen::new);
            MenuScreens.register(ModMenuTypes.TAG_TRANSLATOR, TagTranslatorScreen::new);

            ModBlocks.MODDED_NON_SOLID_BLOCKS_SUPPLIER.stream().map(Supplier::get).forEach(ModBlocks::cutoutMippedRendering);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ANTI_PRESSURE_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.TAG_TRANSLATOR.get(), RenderType.cutout());
        }
    }

    @Mod.EventBusSubscriber(modid = Names.MOD_ID)
    public static final class ForgeEvents {

        public ForgeEvents() {
        }

        @SubscribeEvent
        public static void onCommandRegistration(RegisterCommandsEvent event) {
            GetCapInfoCommand.register(event.getDispatcher());
        }

        @SubscribeEvent
        public static void onServerStarting(ServerStartingEvent serverStartingEvent) {
            ModdedTriggers.init();
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
        }

        @SubscribeEvent
        public static void onBlockBroken(BlockEvent.BreakEvent event) {
            MultiblockManager.INSTANCE.checkAndRemoveMultiblocks(event.getPlayer().getLevel(), event.getPos());
        }

        @SubscribeEvent
        public static void onBiomeLoading(BiomeLoadingEvent event) {
            if (event.getCategory() != Biome.BiomeCategory.NETHER && event.getCategory() != Biome.BiomeCategory.THEEND) {
                for (OreVeins oreVein : OreVeins.values()) {
                    event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, oreVein.getOreFeature());
                }
            }
        }
    }
}

package com.khanhpham.tothemoon;

import com.khanhpham.tothemoon.advancements.ModdedTriggers;
import com.khanhpham.tothemoon.config.TTMConfigs;
import com.khanhpham.tothemoon.core.blocks.BurnableBlock;
import com.khanhpham.tothemoon.core.blocks.HasCustomBlockItem;
import com.khanhpham.tothemoon.core.blocks.NoBlockItem;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryMenuScreen;
import com.khanhpham.tothemoon.core.blocks.big_machines_components.IMultiblockComponent;
import com.khanhpham.tothemoon.core.blocks.machines.alloysmelter.AlloySmelterMenuScreen;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.containerscreens.EnergyGeneratorMenuScreen;
import com.khanhpham.tothemoon.core.blocks.machines.energysmelter.EnergySmelterScreen;
import com.khanhpham.tothemoon.core.blocks.machines.metalpress.MetalPressMenuScreen;
import com.khanhpham.tothemoon.core.blocks.machines.oreprocessor.OreProcessorScreen;
import com.khanhpham.tothemoon.core.blocks.machines.storageblock.MoonBarrelScreen;
import com.khanhpham.tothemoon.core.blocks.processblocks.tagtranslator.TagTranslatorScreen;
import com.khanhpham.tothemoon.core.blocks.tanks.FluidTankMenuScreen;
import com.khanhpham.tothemoon.core.blocks.workbench.WorkbenchScreen;
import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceControllerBlockEntity;
import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceControllerScreen;
import com.khanhpham.tothemoon.core.renderer.TheMoonDimensionEffect;
import com.khanhpham.tothemoon.datagen.ModItemModels;
import com.khanhpham.tothemoon.datagen.advancement.ModAdvancementProvider;
import com.khanhpham.tothemoon.datagen.blocks.ModBlockModels;
import com.khanhpham.tothemoon.datagen.blocks.ModBlockStateProvider;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.datagen.loottable.ModLootTables;
import com.khanhpham.tothemoon.datagen.recipes.provider.ModRecipeProvider;
import com.khanhpham.tothemoon.datagen.sounds.ModSoundDefinitionsProvider;
import com.khanhpham.tothemoon.datagen.tags.ModTagProvider;
import com.khanhpham.tothemoon.init.ModBlockEntities;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.helpers.RegistryEntries;
import com.khanhpham.tothemoon.utils.multiblock.MultiblockManager;
import com.khanhpham.tothemoon.worldgen.OreVeins;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mod(value = ToTheMoon.MOD_ID)
public class ToTheMoon {
    public static final String MOD_ID = "tothemoon";
    public static final ResourceLocation THE_MOON_SKY_LOCATION = ModUtils.modLoc("textures/environment/the_moon_sky.png");
    public static final ResourceLocation MOON_EFFECTS = ModUtils.modLoc("moon_effects");
    public static final ResourceKey<Level> THE_MOON_DIMENSION = ResourceKey.create(Registry.DIMENSION_REGISTRY, ModUtils.modLoc("the_moon_dimension"));
    public static final Logger LOG = LogManager.getLogger(MOD_ID);
    public static final CreativeModeTab TAB = new CreativeModeTab("ttm_creative_tab") {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.URANIUM_MATERIAL.getIngot());
        }
    };
    public static final boolean IS_JEI_LOADED = ModList.get().isLoaded("jei");

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
        ModUtils.log("Jei is {} ", IS_JEI_LOADED ? "loaded" : "not loaded");
    }

    private static void initRegistration() {
        ModBlocks.init();
        ModItems.start();
        ModBlockEntities.init();

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModEvents {
        public ModEvents() {
        }

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            DataGenerator data = event.getGenerator();
            ExistingFileHelper fileHelper = event.getExistingFileHelper();

            if (event.includeServer()) {
                ModUtils.log("Starting Server Data Generation");
                addProvider(data, new ModAdvancementProvider(data, fileHelper));
                addProvider(data, new ModRecipeProvider(data));
                addProvider(data, new ModLootTables(data));
            }

            if (event.includeClient()) {
                ModUtils.log("Starting Client Data Generation");
                addProvider(data, new ModSoundDefinitionsProvider(data, fileHelper));
                addProvider(data, new ModLanguage(data));
                addProvider(data, new ModBlockStateProvider(data, fileHelper));
                addProvider(data, new ModBlockModels(data, fileHelper));
                addProvider(data, new ModItemModels(data, fileHelper));
                new ModTagProvider(data, fileHelper);
            }
        }

        private static void addProvider(DataGenerator dataGenerator, DataProvider provider) {
            dataGenerator.addProvider(true, provider);
        }

        @SubscribeEvent
        public static void registerEvent(RegisterEvent event) {
            event.register(ForgeRegistries.Keys.ITEMS, (reg) -> {
                Set<? extends Block> blocks = ModBlocks.BLOCK_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).filter(block -> !(block instanceof NoBlockItem)).collect(Collectors.toSet());
                for (Block block : blocks) {
                    if (block instanceof BurnableBlock burnableBlock) {
                        reg.register(ModUtils.modLoc(ModUtils.getPath(burnableBlock)), new BlockItem(burnableBlock, new Item.Properties().tab(ToTheMoon.TAB)) {
                            @Override
                            public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                                return burnableBlock.getBurningTime();
                            }
                        });
                    } else if (block instanceof HasCustomBlockItem blockItem) {
                        reg.register(RegistryEntries.BLOCK.getKey(block), blockItem.getRawItem());
                    } else
                        reg.register(ModUtils.modLoc(ModUtils.getPath(block)), new BlockItem(block, new Item.Properties().tab(ToTheMoon.TAB)));
                }

                reg.register(ModUtils.modLoc("workbench_part"), new BlockItem(ModBlocks.WORKBENCH_LEFT.get(), new Item.Properties()));

            });

            event.register(Registry.PLACED_FEATURE_REGISTRY, OreVeins::registerAll);
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ModdedTriggers.init();

            MenuScreens.register(ModMenuTypes.STORAGE_BLOCK, MoonBarrelScreen::new);
            MenuScreens.register(ModMenuTypes.ENERGY_GENERATOR_CONTAINER, EnergyGeneratorMenuScreen::new);
            MenuScreens.register(ModMenuTypes.ALLOY_SMELTER, AlloySmelterMenuScreen::new);
            MenuScreens.register(ModMenuTypes.METAL_PRESS, MetalPressMenuScreen::new);
            MenuScreens.register(ModMenuTypes.BATTERY, BatteryMenuScreen::new);
            MenuScreens.register(ModMenuTypes.ENERGY_SMELTER, EnergySmelterScreen::new);
            MenuScreens.register(ModMenuTypes.NETHER_BRICK_FURNACE, NetherBrickFurnaceControllerScreen::new);
            MenuScreens.register(ModMenuTypes.FLUID_TANK, FluidTankMenuScreen::new);
            MenuScreens.register(ModMenuTypes.TAG_TRANSLATOR, TagTranslatorScreen::new);
            MenuScreens.register(ModMenuTypes.WORKBENCH_CRAFTING, WorkbenchScreen::new);
            MenuScreens.register(ModMenuTypes.ENERGY_PROCESSOR, OreProcessorScreen::new);
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static final class ForgeEvents {
        public ForgeEvents() {
        }

        @SubscribeEvent
        public static void onCommandRegistration(RegisterCommandsEvent event) {
            //GetCapInfoCommand.register(event.getDispatcher());
        }

        @SubscribeEvent
        public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
            BlockState block = event.getPlacedBlock();
            Level level = Objects.requireNonNull(event.getEntity()).level;

            if (level.dimension().equals(THE_MOON_DIMENSION)) {
                if (block.getTags().anyMatch(tag -> tag.equals(Tags.Blocks.GLASS))) {
                    level.destroyBlock(event.getPos(), false);
                }
                if (block.is(Blocks.WATER)) {
                    level.setBlock(event.getPos(), Blocks.PACKED_ICE.defaultBlockState(), 3);
                }
            }

            if (block.is(Blocks.NETHER_BRICKS)) {
                level.setBlock(event.getPos(), ModBlocks.STRUCTURE_NETHER_BRICKS.get().defaultBlockState(), 3);
            }
        }

        @SubscribeEvent
        public static void onBlockBroken(BlockEvent.BreakEvent event) {
            if (event.getState().getBlock() instanceof IMultiblockComponent)
                MultiblockManager.INSTANCE.checkAndRemoveMultiblocks(event.getPlayer().getLevel(), event.getPos());
        }
    }
}

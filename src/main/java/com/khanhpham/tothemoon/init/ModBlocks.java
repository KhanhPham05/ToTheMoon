package com.khanhpham.tothemoon.init;

import com.google.common.collect.ImmutableSet;
import com.khanhpham.tothemoon.core.blockentities.others.AlloySmelterBlockEntity;
import com.khanhpham.tothemoon.core.blocks.DecorationBlocks;
import com.khanhpham.tothemoon.core.blocks.MachineFrameBlock;
import com.khanhpham.tothemoon.core.blocks.SpecialBlock;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryBlock;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryBlockEntity;
import com.khanhpham.tothemoon.core.blocks.battery.creative.CreativeBatteryBlock;
import com.khanhpham.tothemoon.core.blocks.battery.tiered.RedstoneBatteryBlockEntity;
import com.khanhpham.tothemoon.core.blocks.battery.tiered.SteelBatteryBlockEntity;
import com.khanhpham.tothemoon.core.blocks.big_machines_components.FluidAcceptorBlock;
import com.khanhpham.tothemoon.core.blocks.machine.crusher.CrusherBlockEntity;
import com.khanhpham.tothemoon.core.blocks.machines.alloysmelter.AlloySmelterBlock;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.BaseEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.blocks.machines.energygenerator.tileentities.*;
import com.khanhpham.tothemoon.core.blocks.machines.energysmelter.EnergySmelter;
import com.khanhpham.tothemoon.core.blocks.machines.metalpress.MetalPressBlock;
import com.khanhpham.tothemoon.core.blocks.machines.oreprocessor.OreProcessorBlock;
import com.khanhpham.tothemoon.core.blocks.machines.storageblock.MoonRockBarrel;
import com.khanhpham.tothemoon.core.blocks.processblocks.tagtranslator.TagTranslatorBlock;
import com.khanhpham.tothemoon.core.blocks.tanks.FluidTankBlock;
import com.khanhpham.tothemoon.core.blocks.workbench.WorkbenchBlock;
import com.khanhpham.tothemoon.core.multiblock.block.brickfurnace.NetherBrickFurnaceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCK_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, "tothemoon");
    public static final RegistryObject<MachineFrameBlock.Copper> COPPER_MACHINE_FRAME = register("copper_machine_frame", MachineFrameBlock.Copper::new);
    public static final RegistryObject<MachineFrameBlock.Steel> STEEL_MACHINE_FRAME = register("steel_machine_frame", MachineFrameBlock.Steel::new);
    public static final RegistryObject<MachineFrameBlock.Redstone> REDSTONE_MACHINE_FRAME = register("redstone_machine_frame", MachineFrameBlock.Redstone::new);
    public static final RegistryObject<GlassBlock> ANTI_PRESSURE_GLASS = register("anti_pressure_glass", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.STONE, DyeColor.LIGHT_GRAY).strength(4.5f, 7).sound(SoundType.STONE).requiresCorrectToolForDrops().noOcclusion().isViewBlocking(ModBlocks::never)));
    public static final RegistryObject<Block> ZIRCONIUM_BLOCK = registerStorageBlock("zirconium_block");
    public static final RegistryObject<Block> ZIRCONIUM_ALLOY_BLOCK = registerStorageBlock("zirconium_alloy_block");
    public static final RegistryObject<Block> SMOOTH_METEORITE = register("smooth_meteorite", property().strength(4.0f, 5.5f));
    public static final RegistryObject<Block> RAW_ZIRCONIUM_BLOCK = register("raw_zirconium_block", property(Blocks.RAW_GOLD_BLOCK));
    public static final RegistryObject<Block> PURE_ZIRCONIUM = register("pure_zirconium_block", property(Blocks.GOLD_BLOCK));
    public static final RegistryObject<Block> POLISHED_METEORITE = register("polished_meteorite");
    public static final RegistryObject<Block> METEORITE_ZIRCONIUM_ORE = register("meteorite_zirconium_ore");
    public static final RegistryObject<Block> METEORITE_TILES = register("meteorite_tiles");
    public static final RegistryObject<Block> METEORITE_LAMP = register("meteorite_lamp");
    public static final RegistryObject<Block> METEORITE_BRICKS = register("meteorite_bricks");
    public static final DecorationBlocks METEORITE_BRICKS_DECORATION = DecorationBlocks.Builder.buildBoth(METEORITE_BRICKS);
    public static final RegistryObject<Block> METEORITE = register("meteorite");
    public static final DecorationBlocks METEORITE_DECORATION = DecorationBlocks.Builder.buildBoth(METEORITE).setBricks(METEORITE_BRICKS);
    public static final RegistryObject<Block> GILDED_METEORITE_BRICKS = register("gilded_meteorite_bricks", property(Blocks.GILDED_BLACKSTONE));
    public static final RegistryObject<Block> ERODED_METEORITE = register("eroded_meteorite");
    public static final DecorationBlocks ERODED_METEORITE_DECORATION = DecorationBlocks.Builder.buildBoth(ERODED_METEORITE);
    public static final RegistryObject<Block> COBBLED_METEORITE = register("cobbled_meteorite");
    public static final DecorationBlocks COBBLED_METEORITE_DECORATION = DecorationBlocks.Builder.buildBoth(COBBLED_METEORITE);
    public static final RegistryObject<Block> MOON_ROCK = register("moon_rock", BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE).sound(ModSoundTypes.MOON_ROCK));
    public static final RegistryObject<Block> COBBLED_MOON_ROCK = register("cobbled_moon_rock", () -> new Block(BlockBehaviour.Properties.copy(MOON_ROCK.get())));
    public static final RegistryObject<StairBlock> COBBLED_MOON_ROCK_STAIR = register("cobbled_moon_rock_stair", () -> new StairBlock(COBBLED_MOON_ROCK.get()::defaultBlockState, BlockBehaviour.Properties.copy(COBBLED_MOON_ROCK.get())));
    public static final RegistryObject<SlabBlock> COBBLED_MOON_ROCK_SLAB = register("cobbled_moon_rock_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(COBBLED_MOON_ROCK.get())));
    public static final RegistryObject<Block> POLISHED_MOON_ROCK = register("polished_moon_rock", () -> new Block(BlockBehaviour.Properties.copy(MOON_ROCK.get())));
    public static final RegistryObject<StairBlock> POLISHED_MOON_ROCK_STAIR = register("polished_moon_rock_stair", () -> new StairBlock(MOON_ROCK.get()::defaultBlockState, BlockBehaviour.Properties.copy(MOON_ROCK.get())));
    public static final RegistryObject<SlabBlock> POLISHED_MOON_ROCK_SLAB = register("polished_moon_rock_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOON_ROCK.get())));
    public static final RegistryObject<StairBlock> MOON_ROCK_STAIR = registerWaterlogged("moon_rock_stair", () -> new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE).requiresCorrectToolForDrops().sound(ModSoundTypes.MOON_ROCK)));
    public static final RegistryObject<SlabBlock> MOON_ROCK_SLAB = registerWaterlogged("moon_rock_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).sound(ModSoundTypes.MOON_ROCK)));
    public static final RegistryObject<Block> MOON_ROCK_BRICK = register("moon_rock_brick", BlockBehaviour.Properties.of(Material.STONE).strength(3f, 6f).sound(SoundType.STONE).sound(ModSoundTypes.MOON_ROCK));
    public static final RegistryObject<StairBlock> MOON_ROCK_BRICK_STAIR = registerWaterlogged("moon_rock_brick_stair", () -> new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(3.5f, 6.5f).sound(SoundType.STONE).sound(ModSoundTypes.MOON_ROCK)));
    public static final RegistryObject<SlabBlock> MOON_ROCK_BRICK_SLAB = registerWaterlogged("moon_rock_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3.5f, 6.5f).sound(SoundType.STONE).sound(ModSoundTypes.MOON_ROCK)));
    public static final RegistryObject<Block> DEEPSLATE_URANIUM_ORE = register("deepslate_uranium_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> MOON_IRON_ORE = register("moon_iron_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE).requiresCorrectToolForDrops().sound(ModSoundTypes.MOON_ROCK)));
    public static final RegistryObject<Block> MOON_GOLD_ORE = register("moon_gold_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE).requiresCorrectToolForDrops().sound(ModSoundTypes.MOON_ROCK)));
    public static final RegistryObject<Block> MOON_QUARTZ_ORE = register("moon_quartz_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE).requiresCorrectToolForDrops().sound(ModSoundTypes.MOON_ROCK)));
    public static final RegistryObject<RedStoneOreBlock> MOON_REDSTONE_ORE = register("moon_redstone_ore", () -> new RedStoneOreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE).requiresCorrectToolForDrops().sound(ModSoundTypes.MOON_ROCK)));
    public static final RegistryObject<Block> MOON_URANIUM_ORE = register("moon_uranium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE).requiresCorrectToolForDrops().sound(ModSoundTypes.MOON_ROCK)));
    public static final RegistryObject<FallingBlock> MOON_DUST = register("moon_dust", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND).strength(0.75f).sound(ModSoundTypes.MOON_DUST)));
    public static final RegistryObject<Block> REDSTONE_METAL_BLOCK = register("redstone_metal_block", BlockBehaviour.Properties.of(Material.METAL).strength(4.5f, 6).sound(SoundType.METAL));
    public static final RegistryObject<Block> REDSTONE_STEEL_ALLOY_SHEET_BLOCK = register("redstone_steel_alloy_sheet_block", () -> new Block(BlockBehaviour.Properties.copy(ModBlocks.REDSTONE_METAL_BLOCK.get())));
    public static final RegistryObject<Block> STEEL_BLOCK = register("steel_block", BlockBehaviour.Properties.of(Material.METAL).strength(4, 5).sound(SoundType.METAL));
    public static final RegistryObject<Block> REINFORCED_WOOD = register("reinforced_wood", () -> new Block(BlockBehaviour.Properties.copy(ModBlocks.STEEL_BLOCK.get())));
    public static final RegistryObject<Block> STEEL_SHEET_BLOCK = register("steel_sheet_block", () -> new Block(BlockBehaviour.Properties.copy(ModBlocks.STEEL_BLOCK.get())));
    public static final RegistryObject<Block> REDSTONE_STEEL_ALLOY_BLOCK = register("redstone_steel_alloy_block", BlockBehaviour.Properties.of(Material.METAL).strength(4.5f, 6).sound(SoundType.METAL));
    public static final RegistryObject<Block> COPPER_SHEET_BLOCK = register("copper_sheet_block", BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK));
    public static final RegistryObject<Block> GOLD_SHEET_BLOCK = register("gold_sheet_block", BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK));
    public static final RegistryObject<Block> IRON_SHEET_BLOCK = register("iron_sheet_block", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final RegistryObject<Block> PROCESSED_WOOD = register("processed_wood", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)) {
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 10;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 20;
        }
    });
    public static final RegistryObject<WorkbenchBlock> WORKBENCH = register("workbench", () -> new WorkbenchBlock(BlockBehaviour.Properties.copy(PROCESSED_WOOD.get())));
    public static final RegistryObject<WorkbenchBlock.LeftSide> WORKBENCH_LEFT = register("wb_l", () -> new WorkbenchBlock.LeftSide(BlockBehaviour.Properties.copy(WORKBENCH.get())));
    public static final RegistryObject<FluidTankBlock> FLUID_TANK = register("fluid_tank", () -> new FluidTankBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.5f, 4.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RAW_URANIUM_BLOCK = register("raw_uranium_block", BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK));
    public static final RegistryObject<Block> URANIUM_BLOCK = register("uranium_block", BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK));
    public static final RegistryObject<Block> PURIFIED_QUARTZ_BLOCK = register("purified_quartz_block", BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK));
    public static final RegistryObject<Block> SMOOTH_PURIFIED_QUARTZ_BLOCK = register("smooth_purified_quartz_block", BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK));
    public static final RegistryObject<Block> SMOOTH_BLACKSTONE = register("smooth_blackstone", BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE));
    public static final RegistryObject<FluidAcceptorBlock> BLACKSTONE_FLUID_ACCEPTOR = register("blackstone_fluid_acceptor", () -> new FluidAcceptorBlock(BlockBehaviour.Properties.copy(SMOOTH_BLACKSTONE.get())));
    public static final RegistryObject<FluidAcceptorBlock> NETHER_BRICKS_FLUID_ACCEPTOR = register("nether_bricks_fluid_acceptor", () -> new FluidAcceptorBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));
    public static final ImmutableSet<RegistryObject<? extends Block>> SOLID_BLOCKS;
    public static final RegistryObject<MoonRockBarrel> MOON_ROCK_BARREL = registerBlockEntity("moon_rock_barrel", () -> new MoonRockBarrel(BlockBehaviour.Properties.of(Material.STONE).strength(3.0f, 5.0f).sound(ModSoundTypes.METAL_MACHINE)));
    public static final RegistryObject<AlloySmelterBlock> ALLOY_SMELTER = registerBlockEntity("alloy_smelter", () -> new AlloySmelterBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).sound(ModSoundTypes.METAL_MACHINE), AlloySmelterBlockEntity::new));
    public static final RegistryObject<EnergySmelter> ENERGY_SMELTER = registerBlockEntity("energy_smelter", () -> new EnergySmelter(BlockBehaviour.Properties.copy(ALLOY_SMELTER.get())));
    public static final RegistryObject<NetherBrickFurnaceBlock> NETHER_BRICK_FURNACE_CONTROLLER = registerBlockEntity("netherbrick_furnace_controller", () -> new NetherBrickFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
    public static final RegistryObject<TagTranslatorBlock> TAG_TRANSLATOR = register("tag_translator", TagTranslatorBlock::new);
    public static final RegistryObject<OreProcessorBlock> ORE_PROCESSOR = registerBlockEntity("ore_processor", OreProcessorBlock::new);
    public static final RegistryObject<BatteryBlock<BatteryBlockEntity>> BATTERY = registerBlockEntity("battery", () -> new BatteryBlock<>(BlockBehaviour.Properties.of(Material.METAL).strength(4.0f), BatteryBlockEntity.ENERGY_CAPACITY, ModBlockEntities.BATTERY, BatteryBlockEntity::new));
    public static final RegistryObject<BatteryBlock<SteelBatteryBlockEntity>> STEEL_BATTERY = registerBlockEntity("steel_battery", () -> new BatteryBlock<>(BlockBehaviour.Properties.copy(BATTERY.get()), SteelBatteryBlockEntity.STEEL_TIER_CAPACITY, ModBlockEntities.STEEL_BATTERY, SteelBatteryBlockEntity::new));
    public static final RegistryObject<BatteryBlock<RedstoneBatteryBlockEntity>> REDSTONE_BATTERY = registerBlockEntity("redstone_battery", () -> new BatteryBlock<>(BlockBehaviour.Properties.copy(BATTERY.get()), RedstoneBatteryBlockEntity.REDSTONE_TIER_ENERGY, ModBlockEntities.REDSTONE_BATTERY, RedstoneBatteryBlockEntity::new));
    public static final RegistryObject<CreativeBatteryBlock> CREATIVE_BATTERY = register("creative_battery", () -> new CreativeBatteryBlock(BlockBehaviour.Properties.copy(BATTERY.get())));
    public static final RegistryObject<BaseEnergyGeneratorBlock<GoldEnergyGeneratorBlockEntity>> GOLD_ENERGY_GENERATOR = registerBlockEntity("gold_energy_generator", () -> new BaseEnergyGeneratorBlock<>(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).sound(ModSoundTypes.METAL_MACHINE), ModBlockEntities.GOLD_ENERGY_GENERATOR_TE, GoldEnergyGeneratorBlockEntity::new));
    public static final RegistryObject<BaseEnergyGeneratorBlock<CopperEnergyGeneratorBlockEntity>> COPPER_ENERGY_GENERATOR = registerBlockEntity("copper_energy_generator", () -> new BaseEnergyGeneratorBlock<>(BlockBehaviour.Properties.of(Material.METAL).strength(3.5f, 5).sound(ModSoundTypes.METAL_MACHINE), ModBlockEntities.COPPER_ENERGY_GENERATOR_TE, CopperEnergyGeneratorBlockEntity::new));
    public static final RegistryObject<BaseEnergyGeneratorBlock<IronEnergyGeneratorBlockEntity>> IRON_ENERGY_GENERATOR = registerBlockEntity("iron_energy_generator", () -> new BaseEnergyGeneratorBlock<>(BlockBehaviour.Properties.of(Material.METAL).strength(4, 5).sound(ModSoundTypes.METAL_MACHINE), ModBlockEntities.IRON_ENERGY_GENERATOR_TE, IronEnergyGeneratorBlockEntity::new));
    public static final RegistryObject<MetalPressBlock> METAL_PRESS = registerBlockEntity("metal_press", () -> new MetalPressBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3.5f, 4).sound(ModSoundTypes.METAL_MACHINE)));
    public static final RegistryObject<SpecialBlock<CrusherBlockEntity>> CRUSHER = register("metal_crusher", () -> new SpecialBlock<>(BlockBehaviour.Properties.of(Material.METAL).strength(4, 5).sound(ModSoundTypes.METAL_MACHINE), ModBlockEntities.CRUSHER));


    static {
        SOLID_BLOCKS = ImmutableSet.of(
                NETHER_BRICKS_FLUID_ACCEPTOR,
                BLACKSTONE_FLUID_ACCEPTOR,
                SMOOTH_BLACKSTONE,
                RAW_URANIUM_BLOCK,
                PURIFIED_QUARTZ_BLOCK,
                POLISHED_MOON_ROCK,
                SMOOTH_PURIFIED_QUARTZ_BLOCK,
                COBBLED_MOON_ROCK,
                STEEL_SHEET_BLOCK,
                REINFORCED_WOOD,
                PROCESSED_WOOD,
                IRON_SHEET_BLOCK,
                GOLD_SHEET_BLOCK,
                COPPER_SHEET_BLOCK,
                REDSTONE_METAL_BLOCK,
                REDSTONE_STEEL_ALLOY_BLOCK,
                STEEL_BLOCK,
                MOON_DUST,
                MOON_URANIUM_ORE,
                MOON_REDSTONE_ORE,
                MOON_ROCK,
                MOON_QUARTZ_ORE,
                MOON_ROCK_BRICK,
                DEEPSLATE_URANIUM_ORE,
                MOON_IRON_ORE,
                MOON_GOLD_ORE,
                URANIUM_BLOCK,
                REDSTONE_STEEL_ALLOY_SHEET_BLOCK,
                ZIRCONIUM_BLOCK, ZIRCONIUM_ALLOY_BLOCK, SMOOTH_METEORITE, RAW_ZIRCONIUM_BLOCK, PURE_ZIRCONIUM, POLISHED_METEORITE, METEORITE_ZIRCONIUM_ORE, METEORITE_TILES
                , METEORITE_LAMP, METEORITE_BRICKS, METEORITE, GILDED_METEORITE_BRICKS, ERODED_METEORITE, COBBLED_METEORITE
        );
    }

    private static RegistryObject<Block> register(String name) {
        return register(name, property());
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    public static void init() {
    }

    private static <T extends Block> RegistryObject<T> registerWaterlogged(String name, Supplier<T> o) {
        return BLOCK_DEFERRED_REGISTER.register(name, o);
    }

    private static <T extends Block & EntityBlock> RegistryObject<T> registerBlockEntity(String name, Supplier<T> block) {
        return BLOCK_DEFERRED_REGISTER.register(name, block);
    }

    private static RegistryObject<Block> register(String name, BlockBehaviour.Properties properties) {
        return register(name, () -> new Block(properties.requiresCorrectToolForDrops()));
    }    public static final RegistryObject<BaseEnergyGeneratorBlock<DiamondEnergyGeneratorBlockEntity>> DIAMOND_ENERGY_GENERATOR = registerBlockEntity("diamond_energy_generator", () -> new BaseEnergyGeneratorBlock<>(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).sound(ModSoundTypes.METAL_MACHINE), ModBlockEntities.DIAMOND_ENERGY_GENERATOR_TE, DiamondEnergyGeneratorBlockEntity::new));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return BLOCK_DEFERRED_REGISTER.register(name, supplier);
    }

    private static BlockBehaviour.Properties property() {
        return property(Blocks.STONE);
    }

    private static BlockBehaviour.Properties property(Material material) {
        return BlockBehaviour.Properties.of(material).requiresCorrectToolForDrops();
    }

    private static BlockBehaviour.Properties property(Block copyOf) {
        return BlockBehaviour.Properties.copy(copyOf).requiresCorrectToolForDrops();
    }

    private static RegistryObject<Block> registerStorageBlock(String materialName) {
        return register(materialName, () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    }    public static final RegistryObject<BaseEnergyGeneratorBlock<NetheriteEnergyGeneratorBlockEntity>> NETHERITE_ENERGY_GENERATOR = registerBlockEntity("netherite_energy_generator", () -> new BaseEnergyGeneratorBlock<>(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK), ModBlockEntities.NETHERITE_GENERATOR, NetheriteEnergyGeneratorBlockEntity::new));


}
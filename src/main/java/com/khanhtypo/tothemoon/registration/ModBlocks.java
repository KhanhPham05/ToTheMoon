package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.common.block.*;
import com.khanhtypo.tothemoon.common.block.machine.powergenerator.CopperPowerGeneratorBlockEntity;
import com.khanhtypo.tothemoon.registration.elements.BasicBlockObject;
import com.khanhtypo.tothemoon.registration.elements.BlockObject;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

@SuppressWarnings("unused")
public class ModBlocks {
    public static final BasicBlockObject COBBLED_METEORITE = new BasicBlockObject("cobbled_meteorite", BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.STONE));
    public static final BasicBlockObject COBBLED_METEORITE_STAIRS = BasicBlockObject.stairs(COBBLED_METEORITE);
    public static final BasicBlockObject COBBLED_METEORITE_SLAB = BasicBlockObject.slab(COBBLED_METEORITE);
    public static final BasicBlockObject COBBLED_METEORITE_WALL = BasicBlockObject.wall(COBBLED_METEORITE);
    public static final BasicBlockObject COBBLED_MOON_ROCK = new BasicBlockObject("cobbled_moon_rock", COBBLED_METEORITE);
    public static final BasicBlockObject COBBLED_MOON_ROCK_STAIRS = BasicBlockObject.stairs(COBBLED_MOON_ROCK);
    public static final BasicBlockObject COBBLED_MOON_ROCK_SLAB = BasicBlockObject.slab(COBBLED_MOON_ROCK);
    public static final BasicBlockObject COBBLED_MOON_ROCK_WALL = BasicBlockObject.wall(COBBLED_MOON_ROCK);
    public static final FrameBlockObject COPPER_MACHINE_FRAME = new FrameBlockObject("copper_machine_frame", MachineFrameShapes.COPPER, MapColor.COLOR_ORANGE);
    public static final BasicBlockObject DEEPSLATE_URANIUM_ORE = new BasicBlockObject("deepslate_uranium_ore", Blocks.DEEPSLATE_DIAMOND_ORE);
    public static final BasicBlockObject ERODED_METEORITE = new BasicBlockObject("eroded_meteorite", COBBLED_METEORITE);
    public static final BasicBlockObject GILDED_METEORITE_BRICKS = new BasicBlockObject("gilded_meteorite_bricks", COBBLED_METEORITE);
    public static final BasicBlockObject COPPER_SHEET_BLOCK = new BasicBlockObject("copper_sheet_block", Blocks.COPPER_BLOCK);
    public static final BasicBlockObject GOLD_SHEET_BLOCK = new BasicBlockObject("gold_sheet_block", Blocks.GOLD_BLOCK);
    public static final BasicBlockObject IRON_SHEET_BLOCK = new BasicBlockObject("iron_sheet_block", Blocks.IRON_BLOCK);
    public static final BasicBlockObject METEORITE = new BasicBlockObject("meteorite", COBBLED_METEORITE);
    public static final BasicBlockObject METEORITE_SLAB = BasicBlockObject.slab(METEORITE);
    public static final BasicBlockObject METEORITE_STAIR = BasicBlockObject.stairs(METEORITE);
    public static final BasicBlockObject METEORITE_WALL = BasicBlockObject.wall(METEORITE);
    public static final BasicBlockObject METEORITE_BRICKS = new BasicBlockObject("meteorite_bricks", COBBLED_METEORITE);
    public static final BasicBlockObject METEORITE_BRICKS_SLAB = BasicBlockObject.slab(METEORITE_BRICKS);
    public static final BasicBlockObject METEORITE_BRICKS_WALL = BasicBlockObject.wall(METEORITE_BRICKS);
    public static final BasicBlockObject METEORITE_BRICKS_STAIRS = BasicBlockObject.stairs(METEORITE_BRICKS);
    public static final BasicBlockObject METEORITE_TILES = new BasicBlockObject("meteorite_tiles", COBBLED_METEORITE);
    public static final BasicBlockObject METEORITE_TILES_SLAB = BasicBlockObject.slab(METEORITE_TILES);
    public static final BasicBlockObject METEORITE_TILES_WALL = BasicBlockObject.wall(METEORITE_TILES);
    public static final BasicBlockObject METEORITE_TILES_STAIRS = BasicBlockObject.stairs(METEORITE_TILES);
    public static final BasicBlockObject MOON_ROCK = new BasicBlockObject("moon_rock", COBBLED_METEORITE);
    public static final BasicBlockObject MOON_ROCK_SLAB = BasicBlockObject.slab(MOON_ROCK);
    public static final BasicBlockObject MOON_ROCK_WALL = BasicBlockObject.wall(MOON_ROCK);
    public static final BasicBlockObject MOON_ROCK_STAIRS = BasicBlockObject.stairs(MOON_ROCK);
    public static final BasicBlockObject MOON_ROCK_BRICKS = new BasicBlockObject("moon_rock_bricks", COBBLED_METEORITE);
    public static final BasicBlockObject MOON_ROCK_BRICKS_SLAB = BasicBlockObject.slab(MOON_ROCK_BRICKS);
    public static final BasicBlockObject MOON_ROCK_BRICKS_WALL = BasicBlockObject.wall(MOON_ROCK_BRICKS);
    public static final BasicBlockObject MOON_ROCK_BRICKS_STAIRS = BasicBlockObject.stairs(MOON_ROCK_BRICKS);
    public static final BasicBlockObject POLISHED_METEORITE = new BasicBlockObject("polished_meteorite", COBBLED_METEORITE);
    public static final BasicBlockObject POLISHED_METEORITE_SLAB = BasicBlockObject.slab(POLISHED_METEORITE);
    public static final BasicBlockObject POLISHED_METEORITE_WALL = BasicBlockObject.wall(POLISHED_METEORITE);
    public static final BasicBlockObject POLISHED_METEORITE_STAIRS = BasicBlockObject.stairs(POLISHED_METEORITE);
    public static final BasicBlockObject POLISHED_MOON_ROCK = new BasicBlockObject("polished_moon_rock", COBBLED_METEORITE);
    public static final BasicBlockObject POLISHED_MOON_ROCK_SLAB = BasicBlockObject.slab(POLISHED_MOON_ROCK);
    public static final BasicBlockObject POLISHED_MOON_ROCK_WALL = BasicBlockObject.wall(POLISHED_MOON_ROCK);
    public static final BasicBlockObject POLISHED_MOON_ROCK_STAIR = BasicBlockObject.stairs(POLISHED_MOON_ROCK);
    public static final BasicBlockObject SMOOTH_BLACKSTONE = new BasicBlockObject("smooth_blackstone", Blocks.BLACKSTONE);
    public static final BasicBlockObject SMOOTH_BLACKSTONE_SLAB = BasicBlockObject.slab(SMOOTH_BLACKSTONE);
    public static final BasicBlockObject SMOOTH_BLACKSTONE_WALL = BasicBlockObject.wall(SMOOTH_BLACKSTONE);
    public static final BasicBlockObject SMOOTH_BLACKSTONE_STAIRS = BasicBlockObject.stairs(SMOOTH_BLACKSTONE);
    public static final BasicBlockObject SMOOTH_METEORITE = new BasicBlockObject("smooth_meteorite", COBBLED_METEORITE);
    public static final BasicBlockObject SMOOTH_METEORITE_SLAB = BasicBlockObject.slab(SMOOTH_METEORITE);
    public static final BasicBlockObject SMOOTH_METEORITE_WALL = BasicBlockObject.wall(SMOOTH_METEORITE);
    public static final BasicBlockObject SMOOTH_METEORITE_STAIRS = BasicBlockObject.stairs(SMOOTH_METEORITE);
    public static final BasicBlockObject METEORITE_LAMP = new BasicBlockObject("meteorite_lamp", BlockBehaviour.Properties.copy(Blocks.SHROOMLIGHT).mapColor(MapColor.COLOR_PURPLE).sound(SoundType.STONE));
    public static final BasicBlockObject MOON_URANIUM_ORE = new BasicBlockObject("moon_uranium_ore", COBBLED_METEORITE);
    public static final BasicBlockObject METEORITE_ZIRCONIUM_ORE = new BasicBlockObject("meteorite_zirconium_ore", COBBLED_METEORITE);
    public static final BlockObject<FallingBlock> MOON_DUST = new BlockObject<>("moon_dust", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND).mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final BasicBlockObject MOON_GOLD_ORE = new BasicBlockObject("moon_gold_ore", Blocks.DEEPSLATE_GOLD_ORE, MapColor.COLOR_LIGHT_GRAY);
    public static final BasicBlockObject MOON_IRON_ORE = new BasicBlockObject("moon_iron_ore", Blocks.DEEPSLATE_IRON_ORE, MapColor.COLOR_LIGHT_GRAY);
    public static final BlockObject<DropExperienceBlock> MOON_QUARTZ_ORE = new BlockObject<>("moon_quartz_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_QUARTZ_ORE).mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.DEEPSLATE)));
    public static final BlockObject<RedStoneOreBlock> MOON_REDSTONE_ORE = new BlockObject<>("moon_redstone_ore", () -> new RedStoneOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_REDSTONE_ORE).mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.DEEPSLATE)));
    public static final BlockObject<BarrelBlock> MOON_ROCK_BARREL = new BlockObject<>("moon_rock_barrel", () -> new MoonBarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.STONE)));
    public static final BasicBlockObject PROCESSED_WOOD_PLANKS = new BasicBlockObject("processed_wood", Blocks.SPRUCE_PLANKS);
    public static final BasicBlockObject PURE_ZIRCONIUM_BLOCK = new BasicBlockObject("pure_zirconium_block", Blocks.IRON_BLOCK, MapColor.COLOR_PURPLE);
    public static final BasicBlockObject PURIFIED_QUARTZ_BLOCK = new BasicBlockObject("purified_quartz_block", Blocks.QUARTZ_BLOCK);
    public static final BasicBlockObject RAW_URANIUM_BLOCK = new BasicBlockObject("raw_uranium_block", Blocks.RAW_GOLD_BLOCK, MapColor.COLOR_LIGHT_GREEN);
    public static final BasicBlockObject RAW_ZIRCONIUM_BLOCK = new BasicBlockObject("raw_zirconium_block", Blocks.RAW_GOLD_BLOCK, MapColor.COLOR_PURPLE);
    public static final FrameBlockObject REDSTONE_METAL_MACHINE_FRAME = new FrameBlockObject("redstone_metal_machine_frame", MachineFrameShapes.REDSTONE, MapColor.COLOR_RED);
    public static final BasicBlockObject REDSTONE_METAL_BLOCK = new BasicBlockObject("redstone_metal_block", Blocks.REDSTONE_BLOCK);
    public static final BasicBlockObject REDSTONE_STEEL_ALLOY_BLOCK = new BasicBlockObject("redstone_steel_alloy_block", Blocks.REDSTONE_BLOCK);
    public static final BasicBlockObject REDSTONE_STEEL_ALLOY_SHEET_BLOCK = new BasicBlockObject("redstone_steel_alloy_sheet_block", Blocks.IRON_BLOCK);
    public static final BasicBlockObject REINFORCED_WOOD = new BasicBlockObject("reinforced_wood_planks", Blocks.SPRUCE_PLANKS);
    public static final BasicBlockObject SMOOTH_PURIFIED_QUARTZ_BLOCK = new BasicBlockObject("smooth_purified_quartz_block", Blocks.QUARTZ_BLOCK);
    public static final BasicBlockObject STEEL_BLOCK = new BasicBlockObject("steel_block", Blocks.IRON_BLOCK);
    public static final FrameBlockObject STEEL_MACHINE_FRAME = new FrameBlockObject("steel_machine_frame", MachineFrameShapes.STEEL, MapColor.COLOR_GRAY);
    public static final BasicBlockObject STEEL_SHEET_BLOCK = new BasicBlockObject("steel_sheet_block", Blocks.IRON_BLOCK);
    public static final BasicBlockObject URANIUM_BLOCK = new BasicBlockObject("uranium_block", Blocks.IRON_BLOCK);
    public static final BasicBlockObject ZIRCONIUM_ALLOY_BLOCK = new BasicBlockObject("zirconium_alloy_block", Blocks.IRON_BLOCK);
    public static final BasicBlockObject ZIRCONIUM_BLOCK = new BasicBlockObject("zirconium_block", Blocks.IRON_BLOCK);
    public static final BlockObject<Workbench> WORKBENCH = new BlockObject<>("workbench", Workbench::new);
    //TODO Tag translator
    public static final BlockObject<FunctionalBlock<CopperPowerGeneratorBlockEntity>> COPPER_ENERGY_GENERATOR = new BlockObject<>("copper_energy_generator", () -> new FunctionalBlock<>(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.METAL).strength(2.5f), ModBlockEntities.COPPER_ENERGY_GENERATOR));

    static void staticInit() {
    }
}

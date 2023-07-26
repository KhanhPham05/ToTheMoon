package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.common.block.FrameBlock;
import com.khanhtypo.tothemoon.common.block.InteractableBlock;
import com.khanhtypo.tothemoon.common.block.MachineFrameShapes;
import com.khanhtypo.tothemoon.common.block.Workbench;
import com.khanhtypo.tothemoon.registration.elements.BasicBlock;
import com.khanhtypo.tothemoon.registration.elements.BlockObject;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

@SuppressWarnings("unused")
public class ModBlocks {
    public static final BasicBlock COBBLED_METEORITE = new BasicBlock("cobbled_meteorite", BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.STONE));
    public static final BasicBlock COBBLED_METEORITE_STAIRS = BasicBlock.stairs(COBBLED_METEORITE);
    public static final BasicBlock COBBLED_METEORITE_SLAB = BasicBlock.slab(COBBLED_METEORITE);
    public static final BasicBlock COBBLED_METEORITE_WALL = BasicBlock.wall(COBBLED_METEORITE);
    public static final BasicBlock COBBLED_MOON_ROCK = new BasicBlock("cobbled_moon_rock", COBBLED_METEORITE);
    public static final BasicBlock COBBLED_MOON_ROCK_STAIRS = BasicBlock.stairs(COBBLED_MOON_ROCK);
    public static final BasicBlock COBBLED_MOON_ROCK_SLAB = BasicBlock.slab(COBBLED_MOON_ROCK);
    public static final BasicBlock COBBLED_MOON_ROCK_WALL = BasicBlock.wall(COBBLED_MOON_ROCK);
    public static final FrameBlock COPPER_MACHINE_FRAME = new FrameBlock("copper_machine_frame", MachineFrameShapes.COPPER, MapColor.COLOR_ORANGE);
    public static final BasicBlock DEEPSLATE_URANIUM_ORE = new BasicBlock("deepslate_uranium_ore", Blocks.DEEPSLATE_DIAMOND_ORE);
    public static final BasicBlock ERODED_METEORITE = new BasicBlock("eroded_meteorite", COBBLED_METEORITE);
    public static final BasicBlock GILDED_METEORITE_BRICKS = new BasicBlock("gilded_meteorite_bricks", COBBLED_METEORITE);
    public static final BasicBlock COPPER_SHEET_BLOCK = new BasicBlock("copper_sheet_block", Blocks.COPPER_BLOCK);
    public static final BasicBlock GOLD_SHEET_BLOCK = new BasicBlock("gold_sheet_block", Blocks.GOLD_BLOCK);
    public static final BasicBlock IRON_SHEET_BLOCK = new BasicBlock("iron_sheet_block", Blocks.IRON_BLOCK);
    public static final BasicBlock METEORITE = new BasicBlock("meteorite", COBBLED_METEORITE);
    public static final BasicBlock METEORITE_SLAB = BasicBlock.slab(METEORITE);
    public static final BasicBlock METEORITE_STAIR = BasicBlock.stairs(METEORITE);
    public static final BasicBlock METEORITE_WALL = BasicBlock.wall(METEORITE);
    public static final BasicBlock METEORITE_BRICKS = new BasicBlock("meteorite_bricks", COBBLED_METEORITE);
    public static final BasicBlock METEORITE_BRICKS_SLAB = BasicBlock.slab(METEORITE_BRICKS);
    public static final BasicBlock METEORITE_BRICKS_WALL = BasicBlock.wall(METEORITE_BRICKS);
    public static final BasicBlock METEORITE_BRICKS_STAIRS = BasicBlock.stairs(METEORITE_BRICKS);
    public static final BasicBlock METEORITE_TILES = new BasicBlock("meteorite_tiles", COBBLED_METEORITE);
    public static final BasicBlock METEORITE_TILES_SLAB = BasicBlock.slab(METEORITE_TILES);
    public static final BasicBlock METEORITE_TILES_WALL = BasicBlock.wall(METEORITE_TILES);
    public static final BasicBlock METEORITE_TILES_STAIRS = BasicBlock.stairs(METEORITE_TILES);
    public static final BasicBlock MOON_ROCK = new BasicBlock("moon_rock", COBBLED_METEORITE);
    public static final BasicBlock MOON_ROCK_SLAB = BasicBlock.slab(MOON_ROCK);
    public static final BasicBlock MOON_ROCK_WALL = BasicBlock.wall(MOON_ROCK);
    public static final BasicBlock MOON_ROCK_STAIRS = BasicBlock.stairs(MOON_ROCK);
    public static final BasicBlock MOON_ROCK_BRICKS = new BasicBlock("moon_rock_bricks", COBBLED_METEORITE);
    public static final BasicBlock MOON_ROCK_BRICKS_SLAB = BasicBlock.slab(MOON_ROCK_BRICKS);
    public static final BasicBlock MOON_ROCK_BRICKS_WALL = BasicBlock.wall(MOON_ROCK_BRICKS);
    public static final BasicBlock MOON_ROCK_BRICKS_STAIRS = BasicBlock.stairs(MOON_ROCK_BRICKS);
    public static final BasicBlock POLISHED_METEORITE = new BasicBlock("polished_meteorite", COBBLED_METEORITE);
    public static final BasicBlock POLISHED_METEORITE_SLAB = BasicBlock.slab(POLISHED_METEORITE);
    public static final BasicBlock POLISHED_METEORITE_WALL = BasicBlock.wall(POLISHED_METEORITE);
    public static final BasicBlock POLISHED_METEORITE_STAIRS = BasicBlock.stairs(POLISHED_METEORITE);
    public static final BasicBlock POLISHED_MOON_ROCK = new BasicBlock("polished_moon_rock", COBBLED_METEORITE);
    public static final BasicBlock POLISHED_MOON_ROCK_SLAB = BasicBlock.slab(POLISHED_MOON_ROCK);
    public static final BasicBlock POLISHED_MOON_ROCK_WALL = BasicBlock.wall(POLISHED_MOON_ROCK);
    public static final BasicBlock POLISHED_MOON_ROCK_STAIR = BasicBlock.stairs(POLISHED_MOON_ROCK);
    public static final BasicBlock SMOOTH_BLACKSTONE = new BasicBlock("smooth_blackstone", Blocks.BLACKSTONE);
    public static final BasicBlock SMOOTH_BLACKSTONE_SLAB = BasicBlock.slab(SMOOTH_BLACKSTONE);
    public static final BasicBlock SMOOTH_BLACKSTONE_WALL = BasicBlock.wall(SMOOTH_BLACKSTONE);
    public static final BasicBlock SMOOTH_BLACKSTONE_STAIRS = BasicBlock.stairs(SMOOTH_BLACKSTONE);
    public static final BasicBlock SMOOTH_METEORITE = new BasicBlock("smooth_meteorite", COBBLED_METEORITE);
    public static final BasicBlock SMOOTH_METEORITE_SLAB = BasicBlock.slab(SMOOTH_METEORITE);
    public static final BasicBlock SMOOTH_METEORITE_WALL = BasicBlock.wall(SMOOTH_METEORITE);
    public static final BasicBlock SMOOTH_METEORITE_STAIRS = BasicBlock.stairs(SMOOTH_METEORITE);
    public static final BasicBlock METEORITE_LAMP = new BasicBlock("meteorite_lamp", BlockBehaviour.Properties.copy(Blocks.SHROOMLIGHT).mapColor(MapColor.COLOR_PURPLE).sound(SoundType.STONE));
    public static final BasicBlock MOON_URANIUM_ORE = new BasicBlock("moon_uranium_ore", COBBLED_METEORITE);
    public static final BasicBlock METEORITE_ZIRCONIUM_ORE = new BasicBlock("meteorite_zirconium_ore", COBBLED_METEORITE);
    public static final BlockObject<FallingBlock> MOON_DUST = new BlockObject<>("moon_dust", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND).mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final BasicBlock MOON_GOLD_ORE = new BasicBlock("moon_gold_ore", Blocks.DEEPSLATE_GOLD_ORE, MapColor.COLOR_LIGHT_GRAY);
    public static final BasicBlock MOON_IRON_ORE = new BasicBlock("moon_iron_ore", Blocks.DEEPSLATE_IRON_ORE, MapColor.COLOR_LIGHT_GRAY);
    public static final BlockObject<DropExperienceBlock> MOON_QUARTZ_ORE = new BlockObject<>("moon_quartz_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_QUARTZ_ORE).mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.DEEPSLATE)));
    public static final BlockObject<RedStoneOreBlock> MOON_REDSTONE_ORE = new BlockObject<>("moon_redstone_ore", () -> new RedStoneOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_REDSTONE_ORE).mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.DEEPSLATE)));
    public static final BlockObject<BarrelBlock> MOON_ROCK_BARREL = new BlockObject<>("moon_rock_barrel", () -> new BarrelBlock(BlockBehaviour.Properties.copy(Blocks.BARREL).mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.STONE)));
    public static final BasicBlock PROCESSED_WOOD_PLANKS = new BasicBlock("processed_wood", Blocks.SPRUCE_PLANKS);
    public static final BasicBlock PURE_ZIRCONIUM_BLOCK = new BasicBlock("pure_zirconium_block", Blocks.IRON_BLOCK, MapColor.COLOR_PURPLE);
    public static final BasicBlock PURIFIED_QUARTZ_BLOCK = new BasicBlock("purified_quartz_block", Blocks.QUARTZ_BLOCK);
    public static final BasicBlock RAW_URANIUM_BLOCK = new BasicBlock("raw_uranium_block", Blocks.RAW_GOLD_BLOCK, MapColor.COLOR_LIGHT_GREEN);
    public static final BasicBlock RAW_ZIRCONIUM_BLOCK = new BasicBlock("raw_zirconium_block", Blocks.RAW_GOLD_BLOCK, MapColor.COLOR_PURPLE);
    public static final FrameBlock REDSTONE_METAL_MACHINE_FRAME = new FrameBlock("redstone_metal_machine_frame", MachineFrameShapes.REDSTONE, MapColor.COLOR_RED);
    public static final BasicBlock REDSTONE_METAL_BLOCK = new BasicBlock("redstone_metal_block", Blocks.REDSTONE_BLOCK);
    public static final BasicBlock REDSTONE_STEEL_ALLOY_BLOCK = new BasicBlock("redstone_steel_alloy_block", Blocks.REDSTONE_BLOCK);
    public static final BasicBlock REDSTONE_STEEL_ALLOY_SHEET_BLOCK = new BasicBlock("redstone_steel_alloy_sheet_block", Blocks.IRON_BLOCK);
    public static final BasicBlock REINFORCED_WOOD = new BasicBlock("reinforced_wood_planks", Blocks.SPRUCE_PLANKS);
    public static final BasicBlock SMOOTH_PURIFIED_QUARTZ_BLOCK = new BasicBlock("smooth_purified_quartz_block", Blocks.QUARTZ_BLOCK);
    public static final BasicBlock STEEL_BLOCK = new BasicBlock("steel_block", Blocks.IRON_BLOCK);
    public static final FrameBlock STEEL_MACHINE_FRAME = new FrameBlock("steel_machine_frame", MachineFrameShapes.STEEL, MapColor.COLOR_GRAY);
    public static final BasicBlock STEEL_SHEET_BLOCK = new BasicBlock("steel_sheet_block", Blocks.IRON_BLOCK);
    public static final BasicBlock URANIUM_BLOCK = new BasicBlock("uranium_block", Blocks.IRON_BLOCK);
    public static final BasicBlock ZIRCONIUM_ALLOY_BLOCK = new BasicBlock("zirconium_alloy_block", Blocks.IRON_BLOCK);
    public static final BasicBlock ZIRCONIUM_BLOCK = new BasicBlock("zirconium_block", Blocks.IRON_BLOCK);
    public static final InteractableBlock WORKBENCH = new InteractableBlock("workbench", Workbench::new);
    //TODO Tag translator

    static void staticInit() {
    }
}

package com.khanhpham.tothemoon.init;

import com.google.common.collect.ImmutableSet;
import com.khanhpham.tothemoon.core.machines.alloysmelter.AlloySmelterBlock;
import com.khanhpham.tothemoon.core.machines.alloysmelter.AlloySmelterBlockEntity;
import com.khanhpham.tothemoon.core.machines.energygenerator.blocks.CopperEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.machines.energygenerator.blocks.DiamondEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.machines.energygenerator.blocks.GoldEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.machines.energygenerator.blocks.IronEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.machines.energygenerator.tileentities.CopperEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.machines.energygenerator.tileentities.DiamondEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.machines.energygenerator.tileentities.GoldEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.machines.energygenerator.tileentities.IronEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.machines.metalpress.MetalPressBlock;
import com.khanhpham.tothemoon.core.machines.metalpress.MetalPressBlockEntity;
import com.khanhpham.tothemoon.core.machines.storageblock.MoonBarrelTileEntity;
import com.khanhpham.tothemoon.core.machines.storageblock.MoonRockBarrel;
import com.khanhpham.tothemoon.utils.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.utils.blocks.MachineFrameBlock;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.function.Supplier;

public class ModBlocks {

    @Deprecated
    public static final HashSet<Block> MODDED_NON_SOLID_BLOCKS = new HashSet<>();
    public static final DeferredRegister<Block> BLOCK_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, "tothemoon");

    public static final RegistryObject<MachineFrameBlock> COPPER_MACHINE_FRAME = register("copper_machine_frame", () -> new MachineFrameBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistryObject<MachineFrameBlock> STEEL_MACHINE_FRAME = register("steel_machine_frame", () -> new MachineFrameBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<MachineFrameBlock> REDSTONE_MACHINE_FRAME = register("redstone_machine_frame", () -> new MachineFrameBlock(BlockBehaviour.Properties.copy(Blocks.REDSTONE_BLOCK)));

    public static final ImmutableSet<RegistryObject<? extends Block>> MODDED_NON_SOLID_BLOCKS_SUPPLIER = ImmutableSet.of(COPPER_MACHINE_FRAME, STEEL_MACHINE_FRAME, REDSTONE_MACHINE_FRAME);

    public static final RegistryObject<GlassBlock> ANTI_PRESSURE_GLASS = register("anti_pressure_glass", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.STONE, DyeColor.LIGHT_GRAY).strength(4.5f, 7).sound(SoundType.STONE).requiresCorrectToolForDrops().noOcclusion().isViewBlocking(ModBlocks::never)));

    public static final RegistryObject<Block> MOON_ROCK = register("moon_rock", BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE));
    public static final RegistryObject<StairBlock> MOON_ROCK_STAIR = registerWaterlogged("moon_rock_stair", () -> new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<SlabBlock> MOON_ROCK_SLAB = registerWaterlogged("moon_rock_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> MOON_ROCK_BRICK = register("moon_rock_brick", BlockBehaviour.Properties.of(Material.STONE).strength(3f, 6f).sound(SoundType.STONE));
    public static final RegistryObject<StairBlock> MOON_ROCK_BRICK_STAIR = registerWaterlogged("moon_rock_brick_stair", () -> new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(3.5f, 6.5f).sound(SoundType.STONE)));
    public static final RegistryObject<SlabBlock> MOON_ROCK_BRICK_SLAB = registerWaterlogged("moon_rock_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3.5f, 6.5f).sound(SoundType.STONE)));

    public static final RegistryObject<OreBlock> DEEPSLATE_URANIUM_ORE = register("deepslate_uranium_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE).requiresCorrectToolForDrops()));

    public static final RegistryObject<OreBlock> MOON_IRON_ORE = register("moon_iron_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<OreBlock> MOON_GOLD_ORE = register("moon_gold_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<OreBlock> MOON_QUARTZ_ORE = register("moon_quartz_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<RedStoneOreBlock> MOON_REDSTONE_ORE = register("moon_redstone_ore", () -> new RedStoneOreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<OreBlock> MOON_URANIUM_ORE = register("moon_uranium_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3, 6).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<FallingBlock> MOON_DUST = register("moon_dust", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND).strength(0.75f).sound(SoundType.GRAVEL)));

    public static final RegistryObject<Block> REDSTONE_METAL_BLOCK = register("redstone_metal_block", BlockBehaviour.Properties.of(Material.METAL).strength(4.5f, 6).sound(SoundType.METAL));
    public static final RegistryObject<Block> STEEL_BLOCK = register("steel_block", BlockBehaviour.Properties.of(Material.METAL).strength(4, 5).sound(SoundType.METAL));
    public static final RegistryObject<Block> REDSTONE_STEEL_ALLOY_BLOCK = register("redstone_steel_alloy_block", BlockBehaviour.Properties.of(Material.METAL).strength(4.5f, 6).sound(SoundType.METAL));

    public static final ImmutableSet<RegistryObject<? extends Block>> SOLID_BLOCKS = ImmutableSet.of(REDSTONE_METAL_BLOCK, REDSTONE_STEEL_ALLOY_BLOCK, STEEL_BLOCK, ANTI_PRESSURE_GLASS, MOON_DUST, MOON_URANIUM_ORE, MOON_REDSTONE_ORE, MOON_ROCK, MOON_QUARTZ_ORE, MOON_ROCK_BRICK, DEEPSLATE_URANIUM_ORE, MOON_IRON_ORE, MOON_GOLD_ORE);


    public static final RegistryObject<MoonRockBarrel> MOON_ROCK_BARREL = registerBlockEntity("moon_rock_barrel", () -> new MoonRockBarrel(BlockBehaviour.Properties.of(Material.STONE).strength(3.0f, 5.0f).sound(SoundType.STONE), MoonBarrelTileEntity::new));
    public static final RegistryObject<CopperEnergyGeneratorBlock> COPPER_ENERGY_GENERATOR = registerBlockEntity("copper_energy_generator", () -> new CopperEnergyGeneratorBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3.5f, 5).sound(SoundType.METAL), CopperEnergyGeneratorBlockEntity::new));
    public static final RegistryObject<IronEnergyGeneratorBlock> IRON_ENERGY_GENERATOR = registerBlockEntity("iron_energy_generator", () -> new IronEnergyGeneratorBlock(BlockBehaviour.Properties.of(Material.METAL).strength(4, 5).sound(SoundType.METAL), IronEnergyGeneratorBlockEntity::new));
    public static final RegistryObject<AlloySmelterBlock> ALLOY_SMELTER = registerBlockEntity("alloy_smelter", () -> new AlloySmelterBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK), AlloySmelterBlockEntity::new));
    public static final RegistryObject<MetalPressBlock> METAL_PRESS = registerBlockEntity("metal_press", () -> new MetalPressBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3.5f, 4).sound(SoundType.METAL), MetalPressBlockEntity::new));

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }    public static final RegistryObject<DiamondEnergyGeneratorBlock> DIAMOND_ENERGY_GENERATOR = registerBlockEntity("diamond_energy_generator", () -> new DiamondEnergyGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK), DiamondEnergyGeneratorBlockEntity::new));

    public static void init() {
    }    public static final RegistryObject<GoldEnergyGeneratorBlock> GOLD_ENERGY_GENERATOR = registerBlockEntity("gold_energy_generator", () -> new GoldEnergyGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK), GoldEnergyGeneratorBlockEntity::new));

    private static <T extends Block> RegistryObject<T> registerWaterlogged(String name, Supplier<T> o) {
        return BLOCK_DEFERRED_REGISTER.register(name, o);
    }

    public static void cutoutMippedRendering(Block block) {
        ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutoutMipped());
    }

    private static <T extends BaseEntityBlock<?>> RegistryObject<T> registerBlockEntity(String name, Supplier<T> block) {
        return BLOCK_DEFERRED_REGISTER.register(name, block);
    }

    private static RegistryObject<Block> register(String name, BlockBehaviour.Properties properties) {
        return register(name, () -> new Block(properties.requiresCorrectToolForDrops()));
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return BLOCK_DEFERRED_REGISTER.register(name, supplier);
    }

    public static void translucentRendering(Block block) {
        ItemBlockRenderTypes.setRenderLayer(block, RenderType.translucentMovingBlock());
    }




}
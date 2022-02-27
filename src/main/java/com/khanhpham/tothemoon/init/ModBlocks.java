package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.alloysmelter.AlloySmelterBlock;
import com.khanhpham.tothemoon.core.alloysmelter.AlloySmelterBlockEntity;
import com.khanhpham.tothemoon.core.energygenerator.blocks.CopperEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.energygenerator.blocks.DiamondEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.energygenerator.blocks.GoldEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.energygenerator.blocks.IronEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.energygenerator.tileentities.CopperEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.energygenerator.tileentities.DiamondEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.energygenerator.tileentities.GoldEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.energygenerator.tileentities.IronEnergyGeneratorBlockEntity;
import com.khanhpham.tothemoon.core.storageblock.MoonBarrelTileEntity;
import com.khanhpham.tothemoon.core.storageblock.MoonRockBarrel;
import com.khanhpham.tothemoon.utils.registration.BlockRegister;
import com.khanhpham.tothemoon.utils.blocks.MineableBlock;
import com.khanhpham.tothemoon.utils.blocks.MineableSlabBlocks;
import com.khanhpham.tothemoon.utils.blocks.MineableStairBlock;
import com.khanhpham.tothemoon.utils.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.utils.mining.MiningTool;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {


    public static final BlockRegister BLOCK_REGISTER = new BlockRegister();

    //DECORATE / CRAFTING BLOCKS
    public static final Block MACHINE_FRAME = register("machine_frame", new MineableBlock(properties(Material.STONE, 2.5f, 3.5f, SoundType.STONE), MiningTool.NEEDS_IRON_PICKAXE));

    //REGULAR ROCKS
    public static final Block MOON_ROCK = register("moon_rock", 3.0f, 6.0f);
    public static final Block MOON_ROCK_STAIRS = registerStair(MOON_ROCK, MiningTool.NEEDS_STONE_PICKAXE);
    public static final Block MOON_ROCK_SLAB = registerSlab(MOON_ROCK, MiningTool.NEEDS_STONE_PICKAXE);

    //BRICKS
    public static final Block MOON_ROCK_BRICKS = register("moon_rock_bricks", 3.1f, 6.1f);
    public static final Block MOON_ROCK_BRICK_STAIR = registerStair(MOON_ROCK_BRICKS, MiningTool.NEEDS_STONE_PICKAXE);
    public static final Block MOON_ROCK_BRICK_SLAB = registerSlab(MOON_ROCK_BRICKS, MiningTool.NEEDS_STONE_PICKAXE);

    //TEST BLOCK
    public static final Block MOON_ROCK_BARREL = register("moon_rock_barrel", new MoonRockBarrel(properties(Material.STONE, 3.0f, 5.5f, SoundType.STONE), MoonBarrelTileEntity::new, MiningTool.NEEDS_IRON_PICKAXE));

    //Fuel Energy Generators
    public static final Block COPPER_ENERGY_GENERATOR = register("copper_energy_generator", new CopperEnergyGeneratorBlock(properties(Material.METAL, 3.5f, 5.0f, SoundType.METAL), CopperEnergyGeneratorBlockEntity::new, MiningTool.NEEDS_IRON_PICKAXE));
    public static final Block IRON_ENERGY_GENERATOR = register("iron_energy_generator", new IronEnergyGeneratorBlock(properties(Material.METAL, 4.0f,5.0f, SoundType.METAL), IronEnergyGeneratorBlockEntity::new, MiningTool.NEEDS_STONE_PICKAXE));
    public static final Block GOLD_ENERGY_GENERATOR = register("gold_energy_generator", new GoldEnergyGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK), GoldEnergyGeneratorBlockEntity::new, MiningTool.NEEDS_IRON_PICKAXE));
    public static final Block DIAMOND_ENERGY_GENERATOR = register("diamond_energy_generator", new DiamondEnergyGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK), DiamondEnergyGeneratorBlockEntity::new, MiningTool.NEEDS_IRON_PICKAXE));

    //Alloy Smelter
    public static final Block ALLOY_SMELTER = register("alloy_smelter", new AlloySmelterBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK), AlloySmelterBlockEntity::new, MiningTool.NEEDS_IRON_PICKAXE));

    public ModBlocks() {
    }

    private static Block register(String name, float hardness, float resistance) {
        return BLOCK_REGISTER.register(name, new MineableBlock(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(hardness, resistance).requiresCorrectToolForDrops(), MiningTool.NEEDS_STONE_PICKAXE));
    }

    /**
     * @see net.minecraft.world.level.block.Blocks
     */
    private static Block registerStair(Block parentBlock, MiningTool tool) {
        String name = parentBlock.getRegistryName().getPath() + "_stair";
        return BLOCK_REGISTER.register(name, new MineableStairBlock(parentBlock, tool));
    }

    private static Block registerSlab(Block parentBlock, MiningTool tool) {
        String name = parentBlock.getRegistryName().getPath() + "_slab";
        return BLOCK_REGISTER.register(name, new MineableSlabBlocks(tool, parentBlock));
    }

    private static <T extends BaseEntityBlock<?>> Block register(String name, T block) {
        return BLOCK_REGISTER.register(name, block);
    }

    private static <T extends Block> Block register(String name, T block) {
        return BLOCK_REGISTER.register(name, block);
    }

    private static BlockBehaviour.Properties properties(Material material, float hardness, float resistance, SoundType breakSound) {
        return BlockBehaviour.Properties.of(material).strength(hardness, resistance).sound(breakSound).requiresCorrectToolForDrops();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        init(event.getRegistry());
    }

    public static void init(IForgeRegistry<Block> registry) {
        BLOCK_REGISTER.registerAll(registry);
    }
}

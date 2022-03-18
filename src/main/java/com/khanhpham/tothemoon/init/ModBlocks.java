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
import com.khanhpham.tothemoon.utils.blocks.BaseEntityBlock;
import com.khanhpham.tothemoon.utils.blocks.MineableSlabBlocks;
import com.khanhpham.tothemoon.utils.blocks.MineableStairBlock;
import com.khanhpham.tothemoon.utils.registration.BlockRegister;
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
    public static final Block COPPER_MACHINE_FRAME = register("copper_machine_frame", 2.5f, 3.5f, Material.METAL, SoundType.METAL);
    public static final Block STEEL_MACHINE_FRAME = register("steel_machine_frame", 2.5f, 3.5f, Material.METAL, SoundType.METAL);
    public static final Block REDSTONE_MACHINE_FRAME = register("redstone_machine_frame", 3.0f, 4.0f, Material.METAL, SoundType.METAL);

    //REGULAR ROCKS
    public static final Block MOON_ROCK = register("moon_rock", 3.0f, 6.0f);
    public static final Block MOON_ROCK_STAIRS = registerStair(MOON_ROCK);
    public static final Block MOON_ROCK_SLAB = registerSlab(MOON_ROCK);

    //BRICKS
    public static final Block MOON_ROCK_BRICKS = register("moon_rock_bricks", 3.1f, 6.1f);
    public static final Block MOON_ROCK_BRICK_STAIR = registerStair(MOON_ROCK_BRICKS);
    public static final Block MOON_ROCK_BRICK_SLAB = registerSlab(MOON_ROCK_BRICKS);

    //TEST BLOCK
    public static final Block MOON_ROCK_BARREL = register("moon_rock_barrel", new MoonRockBarrel(properties(Material.STONE, 3.0f, 5.5f, SoundType.STONE), MoonBarrelTileEntity::new));

    //Fuel Energy Generators
    public static final Block COPPER_ENERGY_GENERATOR = register("copper_energy_generator", new CopperEnergyGeneratorBlock(properties(Material.METAL, 3.5f, 5.0f, SoundType.METAL), CopperEnergyGeneratorBlockEntity::new));
    public static final Block IRON_ENERGY_GENERATOR = register("iron_energy_generator", new IronEnergyGeneratorBlock(properties(Material.METAL, 4.0f, 5.0f, SoundType.METAL), IronEnergyGeneratorBlockEntity::new));
    //Alloy Smelter
    public static final Block ALLOY_SMELTER = register("alloy_smelter", new AlloySmelterBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK), AlloySmelterBlockEntity::new));
    public static final Block REDSTONE_METAL_BLOCK = register("redstone_metal_block", 4.5f, 6.0f, Material.METAL, SoundType.METAL);    public static final Block GOLD_ENERGY_GENERATOR = register("gold_energy_generator", new GoldEnergyGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK), GoldEnergyGeneratorBlockEntity::new));
    public static final Block STEEL_BLOCK = register("steel_block", 4.0f, 5.0f, Material.METAL, SoundType.METAL);
    public static final Block REDSTONE_STEEL_ALLOY_BLOCk = register("redstone_steel_alloy_block", 4.5f, 6.0F, Material.METAL, SoundType.METAL);

    private static Block register(String name, float hardness, float resistance) {
        return BLOCK_REGISTER.register(name, new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(hardness, resistance).requiresCorrectToolForDrops()));
    }

    public ModBlocks() {
    }


    private static Block register(String name, float hardness, float resistance, Material material, SoundType sound) {
        return BLOCK_REGISTER.register(name, new Block(BlockBehaviour.Properties.of(material).strength(hardness, resistance).sound(sound)));
    }    public static final Block DIAMOND_ENERGY_GENERATOR = register("diamond_energy_generator", new DiamondEnergyGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK), DiamondEnergyGeneratorBlockEntity::new));

    private static Block registerStair(Block parentBlock) {
        String name = parentBlock.getRegistryName().getPath() + "_stair";
        return BLOCK_REGISTER.register(name, new MineableStairBlock(parentBlock));
    }

    private static Block registerSlab(Block parentBlock) {
        String name = parentBlock.getRegistryName().getPath() + "_slab";
        return BLOCK_REGISTER.register(name, new MineableSlabBlocks(parentBlock));
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

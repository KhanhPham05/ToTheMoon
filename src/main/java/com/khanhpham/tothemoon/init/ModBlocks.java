package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.energygenerator.blocks.CopperEnergyGeneratorBlock;
import com.khanhpham.tothemoon.core.energygenerator.tileentities.CopperEnergyGeneratorTileEntity;
import com.khanhpham.tothemoon.core.storageblock.MoonBarrelTileEntity;
import com.khanhpham.tothemoon.core.storageblock.MoonRockBarrel;
import com.khanhpham.tothemoon.utils.BlockRegister;
import com.khanhpham.tothemoon.utils.blocks.MineableBlock;
import com.khanhpham.tothemoon.utils.blocks.TileEntityBlock;
import com.khanhpham.tothemoon.utils.mining.MiningTool;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    public static final BlockRegister BLOCKS = new BlockRegister();

    public static final Block MOON_ROCK = register("moon_rock", 3.0f, 6.0f);
    public static final Block MOON_ROCK_BRICKS = register("moon_rock_bricks", 3.1f, 6.1f);
    public static final Block MOON_ROCK_BARREL = register("moon_rock_barrel", new MoonRockBarrel(properties(Material.STONE, 3.0f, 5.5f, SoundType.STONE), MoonBarrelTileEntity::new, MiningTool.NEEDS_IRON_PICKAXE));
    public static final Block COPPER_ENERGY_GENERATOR = register("copper_energy_generator", new CopperEnergyGeneratorBlock(properties(Material.METAL, 3.5f, 6.0f, SoundType.METAL), CopperEnergyGeneratorTileEntity::new, MiningTool.NEEDS_IRON_PICKAXE));


    private ModBlocks() {
    }

    private static Block register(String name, float hardness, float resistance) {
        return BLOCKS.register(name, new MineableBlock(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(hardness, resistance).requiresCorrectToolForDrops(), MiningTool.NEEDS_STONE_PICKAXE));
    }

    private static <T extends TileEntityBlock<?>> Block register(String name, T block) {
        return BLOCKS.register(name, (Block) block);
    }

    private static BlockBehaviour.Properties properties(Material material, float hardness, float resistance, SoundType breakSound) {
        return BlockBehaviour.Properties.of(material).strength(hardness, resistance).sound(breakSound).requiresCorrectToolForDrops();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        BLOCKS.registerAll(event.getRegistry());
    }

}

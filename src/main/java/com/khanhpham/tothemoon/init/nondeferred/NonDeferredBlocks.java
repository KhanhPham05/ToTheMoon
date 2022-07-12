package com.khanhpham.tothemoon.init.nondeferred;

import com.khanhpham.tothemoon.core.blocks.tanks.FluidTankBlock;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

public class NonDeferredBlocks {
    public static final Set<Block> REGISTERED_BLOCKS = new HashSet<>();
    public static final Block FLUID_TANK_BLOCK = new FluidTankBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.5f, 4.0f).sound(SoundType.STONE)).setRegistryName(ModUtils.modLoc("fluid_tank"));

    static {
        REGISTERED_BLOCKS.add(FLUID_TANK_BLOCK);
    }

    public static void registerAll(IForgeRegistry<Block> registration) {
        REGISTERED_BLOCKS.forEach(registration::register);
    }
}

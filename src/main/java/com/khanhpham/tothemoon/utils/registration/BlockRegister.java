package com.khanhpham.tothemoon.utils.registration;

import com.khanhpham.tothemoon.Names;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

public class BlockRegister {
    public final Set<Block> blocks = new HashSet<>();

    public BlockRegister() {}

    public <T extends Block> Block register(String name, T block) {
        System.out.println(block);
        Block b = block.setRegistryName(new ResourceLocation(Names.MOD_ID, name));
        System.out.println("registering " + b.getRegistryName());
        blocks.add(b);
        return b;
    }

    public Set<Block> getRegisteredBlocks() {
        return blocks;
    }

    public void registerAll(IForgeRegistry<Block> registry) {
        registry.registerAll(blocks.toArray(new Block[0]));
    }
}

package com.khanhpham.tothemoon.utils.registration;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

@Deprecated
public class BlockRegister {
    public final Set<Block> blocks = new HashSet<>();

    public BlockRegister() {}

    public <T extends Block> Block register(String name, T block) {
        ToTheMoon.LOG.info("Registering [ " + name + " ]");
        Block b = block.setRegistryName(new ResourceLocation(Names.MOD_ID, name));
        blocks.add(b);
        return b;
    }

    public Set<Block> getRegisteredBlocks() {
        return blocks;
    }

    public void registerAll(IForgeRegistry<Block> registry) {
        blocks.forEach(registry::register);
    }
}

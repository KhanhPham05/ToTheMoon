package com.khanhpham.tothemoon.utils;

import com.khanhpham.tothemoon.Names;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

public class BlockEntityRegister {
    private final Set<BlockEntityType<?>> TEs = new HashSet<>();

    public BlockEntityRegister() {}

    public <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntitySupplier<T> blockEntitySupplier, Block... validBlock) {
        BlockEntityType<T> te = BlockEntityType.Builder.of(blockEntitySupplier, validBlock).build(null);
        this.TEs.add(te.setRegistryName(new ResourceLocation(Names.MOD_ID, name)));
        return te;
    }

    public void registerAll(IForgeRegistry<BlockEntityType<?>> registry) {
        TEs.forEach(registry::register);
    }
}

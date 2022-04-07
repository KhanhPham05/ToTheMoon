package com.khanhpham.tothemoon.utils.deferredregistration;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockRegistryObject extends Block implements Supplier<Block> {
    public final RegistryObject<Block> blockRegistryObject;
    private final DeferredRegister<Block> deferredRegister;

    public BlockRegistryObject(DeferredRegister<Block> deferredRegister, String name, BlockBehaviour.Properties properties) {
        super(properties);
        this.deferredRegister = deferredRegister;
        this.blockRegistryObject = this.register(name, new Block(properties));
    }

    protected <T extends Block> RegistryObject<T> register(String name, T block) {
        return deferredRegister.register(name, () -> block);
    }

    @Override
    public Block get() {
        return blockRegistryObject.get();
    }
}

package com.khanhpham.tothemoon.api.registration;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public final class RegisterBlock<B extends Block> extends ModRegistryHelper<B> {

    public RegisterBlock(RegistryObject<B> registryObject) {
        super(registryObject);
    }

    public Block block() {
        return get();
    }

    public Item item() {
        return block().asItem();
    }
}

package com.khanhpham.tothemoon.api.registration;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public final class RegisterItem<I extends Item> extends ModRegistryHelper<I> {
    public RegisterItem(RegistryObject<I> registryObject) {
        super(registryObject);
    }

    public I item() {
        return super.get();
    }
}

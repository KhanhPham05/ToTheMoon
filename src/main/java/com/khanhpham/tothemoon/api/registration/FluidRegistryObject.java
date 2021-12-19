package com.khanhpham.tothemoon.api.registration;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.RegistryObject;

import java.util.Objects;

public class FluidRegistryObject<T extends Fluid> extends ModRegistryHelper<T> {
    public FluidRegistryObject(RegistryObject<T> registryObject) {
        super(registryObject);
    }


    public String getTranslationKey() {
        return "fluid." + Objects.requireNonNull(super.registryObject.get().getRegistryName()).getNamespace() + '.' + Objects.requireNonNull(super.registryObject.get().getRegistryName()).getPath();
    }
}

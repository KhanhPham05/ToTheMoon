package com.khanhpham.tothemoon.utils;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.Map;

public class DirectRegistry<T> {
    private final Map<ResourceLocation, T> allEntries = new HashMap<>();

    public DirectRegistry() {
    }

    public <U extends T> U put(String registryName, U entry) {
        this.allEntries.put(ModUtils.modLoc(registryName), entry);
        return entry;
    }

    public void registerAll(RegisterEvent event, ResourceKey<Registry<T>> resourceKey) {
        event.register(resourceKey, helper -> this.allEntries.forEach(helper::register));
    }
}

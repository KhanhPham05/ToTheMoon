package com.khanhpham.ttm.testfeatures;

import com.khanhpham.ttm.ToTheMoonMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashSet;
import java.util.Set;

/**
 * @see net.minecraftforge.eventbus.api.IEventBus
 */
public class RegistryHelper<T extends IForgeRegistryEntry<T>> {
    protected final Set<T> registrySet = new HashSet<>();
    protected final String modid;

    public RegistryHelper(String modid) {
        this.modid = modid;
    }

    //For testing only
    public <I extends T> T register(String name, I instance) {
       if (!registrySet.add(instance.setRegistryName(new ResourceLocation(modid, name)))) {
            throw new IllegalArgumentException("Duplicate instance of " + instance);
        } else return instance;
    }

    public void init(IForgeRegistry<T> reg) {
        registrySet.forEach(instance -> {
            reg.register(instance);
            System.out.println("Registering " + instance.getRegistryName());
        });
    }

    public Set<T> getEntries() {
        return registrySet;
    }
}
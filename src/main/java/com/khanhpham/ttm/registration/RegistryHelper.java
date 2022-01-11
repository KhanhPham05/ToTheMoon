package com.khanhpham.ttm.registration;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashSet;
import java.util.Set;


/**
 * Example Usage:
 * <pre>{@code
 *      @Mod.EventBusSubscriber(modid = MOD_ID, bus = Bus.MOD)
 *     public class TestRegistry {
 *         public static final RegistryHelper<Block> BLOCK_REGISTRIES = new RegistryHelper<>(MOD_ID);
 *         public static final Block EXAMPLE_BLOCK= BLOCK_REGISTRIES.register("example_block", new Block(BlockBehaviour));
 *
 *      @SubscribeEvent
 *      public static void register(RegistryEvent.Register<Block> event) {
 *          BLOCK_REGISTRIES.init(event.getRegistry());
 *      }
 *     }
 * }</pre>
 */
public class RegistryHelper<T extends IForgeRegistryEntry<T>> {
    protected final Set<T> registrySet = new HashSet<>();
    protected final String modid;

    public RegistryHelper(String modid) {
        this.modid = modid;
    }

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
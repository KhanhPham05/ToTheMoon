package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class ModRegistries {
    private static final Map<DeferredRegister<?>, ObjectInitializer> REGISTER_SET = new HashMap<>();
    public static final DeferredRegister<Block> BLOCKS = create(ForgeRegistries.BLOCKS, ModBlocks::staticInit);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = create(ForgeRegistries.SOUND_EVENTS, ModSoundTypes::staticInit);
    public static final DeferredRegister<Item> ITEMS = create(ForgeRegistries.ITEMS, ModItems::staticInit);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModBlockEntities::staticInit);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = create(ForgeRegistries.MENU_TYPES, ModMenuTypes::staticInit);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE = create(ForgeRegistries.RECIPE_TYPES, ModRecipeTypes::staticInit);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = create(ForgeRegistries.RECIPE_SERIALIZERS, null);

    public static void staticInit(IEventBus modBus) {
        REGISTER_SET.forEach((deferredRegister, initializer) -> {
            deferredRegister.register(modBus);
            if (initializer != null) initializer.init();
        });
    }

    private static <T> DeferredRegister<T> create(IForgeRegistry<T> registry, @Nullable ObjectInitializer initializer) {
        DeferredRegister<T> df = ModUtils.createRegistry(registry);
        REGISTER_SET.put(df, initializer);
        return df;
    }

    //Registry trick/hack to get rid of the annoying deprecated warnings
    public static <T> Optional<T> getOptional(ResourceKey<? extends Registry<T>> registry, ResourceLocation name) {
        return getRegistry(registry).getOptional(name);
    }

    public static <T> T getFromName(ResourceKey<? extends Registry<T>> registry, ResourceLocation name) {
        return getOptional(registry, name).orElseThrow(() -> new IllegalStateException("No instance found for " + name));
    }

    public static <T> T getFromIdOrThrow(ResourceKey<? extends Registry<T>> registry, int fluidId) {
        return getRegistry(registry).byIdOrThrow(fluidId);
    }

    public static <T, A extends T> Optional<ResourceLocation> getName(ResourceKey<? extends Registry<T>> registry, A value) {
        return Optional.ofNullable(getRegistry(registry).getKey(value));
    }

    public static <T, A extends T> ResourceLocation getNameOrThrow(ResourceKey<? extends Registry<T>> registry, A value) {
        return getName(registry, value).orElseThrow(() -> new IllegalStateException("Value is not present in the game registry"));
    }

    public static <T> ResourceLocation getNameFromId(ResourceKey<? extends Registry<T>> registry, int id) {
        return getNameOrThrow(registry, getFromIdOrThrow(registry, id));
    }

    public static <T, A extends T> int getId(ResourceKey<? extends Registry<T>> registry, A value) {
        return getRegistry(registry).getId(value);
    }

    private static <T> Registry<T> getRegistry(ResourceKey<? extends Registry<T>> registry) {
        return (Registry<T>) BuiltInRegistries.REGISTRY.getOptional(registry.location()).orElseThrow(() -> new IllegalStateException(String.format("Registry %s is not exist in the game registry", registry)));
    }


    @FunctionalInterface
    private interface ObjectInitializer {
        void init();
    }


}
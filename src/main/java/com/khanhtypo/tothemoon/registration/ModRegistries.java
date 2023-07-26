package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.ModUtils;
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

public class ModRegistries {
    private static final Map<DeferredRegister<?>, ObjectInitializer> REGISTER_SET = new HashMap<>();
    public static final DeferredRegister<Block> BLOCKS = create(ForgeRegistries.BLOCKS, ModBlocks::staticInit);
    public static final DeferredRegister<Item> ITEMS = create(ForgeRegistries.ITEMS, ModItems::staticInit);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = create(ForgeRegistries.SOUND_EVENTS, ModSoundEvents::staticInit);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModBlockEntities::staticInit);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = create(ForgeRegistries.MENU_TYPES, ModMenus::staticInit);
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

    @FunctionalInterface
    private interface ObjectInitializer {
        void init();
    }

}
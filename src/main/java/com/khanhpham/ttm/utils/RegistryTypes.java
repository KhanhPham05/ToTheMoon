package com.khanhpham.ttm.utils;

import com.khanhpham.ttm.ToTheMoonMain;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class RegistryTypes {
    public static final DeferredRegister<Block> blockDF = DeferredRegister.create(ForgeRegistries.BLOCKS, ToTheMoonMain.MOD_ID);

    public static void init(IEventBus modEventBus) {
        blockDF.register(modEventBus);
    }
}

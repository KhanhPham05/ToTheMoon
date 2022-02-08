package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.energygenerator.tileentities.CopperEnergyGeneratorTileEntity;
import com.khanhpham.tothemoon.utils.BlockEntityRegister;
import com.khanhpham.tothemoon.core.storageblock.MoonBarrelTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModTileEntityTypes {
    private ModTileEntityTypes() {
    }

    public static final BlockEntityRegister BLOCK_ENTITIES = new BlockEntityRegister();

    public static final BlockEntityType<MoonBarrelTileEntity> MOON_STORAGE;
    public static final BlockEntityType<CopperEnergyGeneratorTileEntity> COPPER_ENERGY_GENERATOR_TE;

    static {
        MOON_STORAGE = register("moon_storage", MoonBarrelTileEntity::new, ModBlocks.MOON_ROCK_BARREL);
        COPPER_ENERGY_GENERATOR_TE = register("energy_generator_tile_entity", CopperEnergyGeneratorTileEntity::new, ModBlocks.COPPER_ENERGY_GENERATOR);
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntitySupplier<T> supplier, Block... validBlocks) {
        return BLOCK_ENTITIES.register(name, supplier, validBlocks);
    }

    @SubscribeEvent
    public static void registerTileEntityTypes(RegistryEvent.Register<BlockEntityType<?>> event) {
        BLOCK_ENTITIES.registerAll(event.getRegistry());
    }
}

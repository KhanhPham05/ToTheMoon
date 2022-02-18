package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.core.energygenerator.tileentities.CopperEnergyGeneratorTileEntity;
import com.khanhpham.tothemoon.core.energygenerator.tileentities.DiamondEnergyGeneratorTileEntity;
import com.khanhpham.tothemoon.core.energygenerator.tileentities.GoldEnergyGeneratorTileEntity;
import com.khanhpham.tothemoon.core.energygenerator.tileentities.IronEnergyGeneratorTileEntity;
import com.khanhpham.tothemoon.core.storageblock.MoonBarrelTileEntity;
import com.khanhpham.tothemoon.utils.BlockEntityRegister;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;


@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTileEntityTypes {
    public static final BlockEntityRegister BLOCK_ENTITIES = new BlockEntityRegister();

    public static final BlockEntityType<MoonBarrelTileEntity> MOON_STORAGE;

    public static final BlockEntityType<CopperEnergyGeneratorTileEntity> COPPER_ENERGY_GENERATOR_TE;
    public static final BlockEntityType<IronEnergyGeneratorTileEntity> IRON_ENERGY_GENERATOR_TE;
    public static final BlockEntityType<GoldEnergyGeneratorTileEntity> GOLD_ENERGY_GENERATOR_TE;
    public static final BlockEntityType<DiamondEnergyGeneratorTileEntity> DIAMOND_ENERGY_GENERATOR_TE;

    static {
        MOON_STORAGE = register("moon_storage", MoonBarrelTileEntity::new, ModBlocks.MOON_ROCK_BARREL);
        COPPER_ENERGY_GENERATOR_TE = register("copper_energy_generator_tile_entity", CopperEnergyGeneratorTileEntity::new, ModBlocks.COPPER_ENERGY_GENERATOR);
        IRON_ENERGY_GENERATOR_TE = register("iron_energy_generator_tile_entity", IronEnergyGeneratorTileEntity::new, ModBlocks.IRON_ENERGY_GENERATOR);
        GOLD_ENERGY_GENERATOR_TE = register("gold_energy_generator_tile_entity", GoldEnergyGeneratorTileEntity::new, ModBlocks.GOLD_ENERGY_GENERATOR);
        DIAMOND_ENERGY_GENERATOR_TE = register("diamond_energy_generator_tile_entity", DiamondEnergyGeneratorTileEntity::new, ModBlocks.DIAMOND_ENERGY_GENERATOR);
    }

    private ModTileEntityTypes() {
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntitySupplier<T> supplier, Block... validBlocks) {
        return BLOCK_ENTITIES.register(name, supplier, validBlocks);
    }

    @SubscribeEvent
    public static void init(RegistryEvent.Register<BlockEntityType<?>> event) {
        init(event.getRegistry());
    }

    public static void init(IForgeRegistry<BlockEntityType<?>> registry) {
        BLOCK_ENTITIES.registerAll(registry);
    }
}

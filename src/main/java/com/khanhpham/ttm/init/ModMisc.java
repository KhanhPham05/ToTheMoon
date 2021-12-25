package com.khanhpham.ttm.init;

import com.khanhpham.ttm.ToTheMoonMain;
import com.khanhpham.ttm.core.blockentities.energybank.EnergyBankEntity;
import com.khanhpham.ttm.core.blockentities.energygen.EnergyGeneratorEntity;
import com.khanhpham.ttm.testfeatures.BlockEntityRegister;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ToTheMoonMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModMisc {
    public static final BlockEntityRegister BLOCK_ENTITIES = new BlockEntityRegister(ToTheMoonMain.MOD_ID);

    public static final BlockEntityType<EnergyBankEntity> ENERGY_BANK_ENTITY = register("energy_bank_entity", EnergyBankEntity::new, ModBlocks.ENERGY_BANK);
    public static final BlockEntityType<EnergyGeneratorEntity> ENERGY_GEN_ENTITY = register("energy_gen_entity", EnergyGeneratorEntity::new, ModBlocks.ENERGY_GEN);

    private static IForgeRegistry<BlockEntityType<?>> blockEntities;

    public ModMisc() {
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntitySupplier<T> entitySupplier, Block... blocks) {
        return BLOCK_ENTITIES.register(name, entitySupplier, blocks);
    }

    @SubscribeEvent
    public static void onBlocKEntityRegistration(RegistryEvent.Register<BlockEntityType<?>> event) {
        ToTheMoonMain.LOG.info("Registering Block Entities");
        blockEntities = event.getRegistry();
        r(ENERGY_BANK_ENTITY, "energy_bank_entity");
        r(ENERGY_GEN_ENTITY, "energy_gen_entity");
    }

    private static void r(BlockEntityType<?> entityType, String name) {
        blockEntities.register(entityType.setRegistryName(ToTheMoonMain.MOD_ID, name));
    }
}

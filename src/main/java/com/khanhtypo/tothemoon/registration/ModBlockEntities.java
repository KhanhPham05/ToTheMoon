package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.common.block.machine.powergenerator.*;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;

public class ModBlockEntities {
    static void staticInit() {
    }

    public static final BlockEntityObject<PowerGeneratorBlockEntity> COPPER_POWER_GENERATOR = BlockEntityObject.register("copper_power_generator", CopperPowerGeneratorBlockEntity::new, ModBlocks.COPPER_POWER_GENERATOR);
    public static final BlockEntityObject<PowerGeneratorBlockEntity> IRON_POWER_GENERATOR = BlockEntityObject.register("iron_power_generator", IronPowerGeneratorBlockEntity::new, ModBlocks.IRON_POWER_GENERATOR);
    public static final BlockEntityObject<PowerGeneratorBlockEntity> GOLD_POWER_GENERATOR = BlockEntityObject.register("gold_power_generator", GoldPowerGeneratorBlockEntity::new, ModBlocks.GOLD_POWER_GENERATOR);
    public static final BlockEntityObject<PowerGeneratorBlockEntity> DIAMOND_POWER_GENERATOR = BlockEntityObject.register("diamond_power_generator", DiamondPowerGeneratorBlockEntity::new, ModBlocks.DIAMOND_POWER_GENERATOR);
    public static final BlockEntityObject<PowerGeneratorBlockEntity> NETHERITE_POWER_GENERATOR = BlockEntityObject.register("netherite_power_generator", NetheritePowerGeneratorBlockEntity::new, ModBlocks.NETHERITE_POWER_GENERATOR);
}
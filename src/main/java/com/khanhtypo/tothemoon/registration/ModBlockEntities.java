package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.common.block.machine.powergenerator.CopperPowerGeneratorBlockEntity;
import com.khanhtypo.tothemoon.registration.elements.BlockEntityObject;

public class ModBlockEntities {
    static void staticInit() {
    }

    public static final BlockEntityObject<CopperPowerGeneratorBlockEntity> COPPER_ENERGY_GENERATOR = BlockEntityObject.register("copper_energy_generator", CopperPowerGeneratorBlockEntity::new, ModBlocks.COPPER_ENERGY_GENERATOR);
}
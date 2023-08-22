package com.khanhtypo.tothemoon.common.machine.powergenerator;

import com.khanhtypo.tothemoon.common.block.FunctionalBlock;
import com.khanhtypo.tothemoon.registration.ModBlockEntities;

public class PowerGeneratorBlock extends FunctionalBlock<PowerGeneratorBlockEntity> {
    private final PowerGeneratorLevels generatorLevel;

    public PowerGeneratorBlock(Properties p_49795_, PowerGeneratorLevels generatorLevel) {
        super(p_49795_, ModBlockEntities.POWER_GENERATOR);
        this.generatorLevel = generatorLevel;
    }

    public PowerGeneratorLevels getGeneratorLevel() {
        return generatorLevel;
    }
}

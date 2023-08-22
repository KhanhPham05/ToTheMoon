package com.khanhtypo.tothemoon.common.machine.powergenerator;

public enum PowerGeneratorLevels {
    COPPER(100_000, 50),
    IRON(250_000, 125),
    GOLD(600_000, 225),
    DIAMOND(1_000_000, 500),
    NETHERITE(1_800_000, 750);

    public final int capacity;
    public final int generationPerTick;

    PowerGeneratorLevels(int capacity, int generationPerTick) {
        this.capacity = capacity;
        this.generationPerTick = generationPerTick;
    }
}

package com.khanhpham.ttm.core.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.EnergyStorage;

import java.util.Random;

/**
 * @see net.minecraftforge.registries.ForgeRegistry
 */
public abstract class EnergyData extends EnergyStorage {

    private final Random random = new Random();
    public int maxReceive = super.maxReceive;
    public int maxExtract= super.maxExtract;

    public EnergyData(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        if (capacity == 0 || maxTransfer == 0) throw new IllegalArgumentException();
    }

    public boolean isFull() {
        return energy == capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnergy() {
        return energy;
    };

    @Deprecated
    public void setChanged() {
    }

    public void setChanged(Level level, BlockPos pos, BlockState state) {
    }

    public void addEnergy(int maxAmount, boolean printDebug) {
        //use internal maxTransfer
        int spaceEnergy = capacity - energy;
        if (maxAmount > this.maxReceive) {
            add(Math.min(maxReceive, spaceEnergy), printDebug);
        } else
        //use external maxTransfer
        {
            add(Math.min(maxAmount, spaceEnergy), printDebug);
        }
    }

    /**
     * Producing energy as a generator, return false if the energy is full
     * @return false if the energy is full
     */
    public boolean produceEnergy() {

        //won't add any energy when the current energy is already full
        if (energy == capacity) {
            return false;
        } else

        //add the remaining energy in the generator
        if (energy + maxReceive > capacity) {
            int spaceEnergy = capacity - energy;
            energy += spaceEnergy;
            return false;
        }

        energy += maxReceive;
        return true;

    }
    private void add(int amount, boolean printDebug) {
        energy += amount;
        if (printDebug) System.out.println("Adding " + amount + " FE, now contains " + energy + " FE");
    }

    public void addTestEnergy(boolean debug) {
        if (random.nextInt(100) == 5) {
            if (energy <= capacity) {
                int spaceEnergy = capacity - energy;
                if (spaceEnergy < maxReceive) {
                    energy += spaceEnergy;
                    if (debug) System.out.println("Adding " + spaceEnergy + " FE, now contains " + energy);
                } else {
                    energy += maxReceive;
                    if (debug) System.out.println("Adding " + maxReceive + " FE, now contains " + energy);
                }
            }
        }
    }

    public void addTestEnergy() {
        this.addTestEnergy(false);
    }

    public void removeEnergy() {
        if (maxExtract > energy) {
            energy = 0;
        } else {
            energy = -maxExtract;
        }
    }

    public CompoundTag save(CompoundTag tag) {
        tag.putInt("energy", energy);
        return tag;
    }

    public void load(CompoundTag tag) {
        energy = tag.getInt("energy");
    }
}

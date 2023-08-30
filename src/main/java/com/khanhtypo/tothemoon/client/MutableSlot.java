package com.khanhtypo.tothemoon.client;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public abstract class MutableSlot extends Slot {
    public final int pSlot;
    protected boolean isActive;

    public MutableSlot(Container pContainer, int pSlot, int pX, int pY) {
        super(pContainer, pSlot, pX, pY);
        this.pSlot = pSlot;
        this.isActive = true;
    }

    public void toggleActive() {
        this.isActive = !this.isActive;
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    public MutableSlot setActive(boolean defaultActive) {
        this.isActive = defaultActive;
        return this;
    }


    protected Container getContainer() {
        return super.container;
    }
}

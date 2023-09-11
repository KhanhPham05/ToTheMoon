package com.khanhtypo.tothemoon.api.abstracts;

import net.minecraft.world.Container;

/**
 * Implements if your block entity has a container variable.
 */
public interface IItemDropOnBreak {
    Container getContainer();
}

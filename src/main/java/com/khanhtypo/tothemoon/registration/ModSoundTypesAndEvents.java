package com.khanhtypo.tothemoon.registration;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;

public class ModSoundTypesAndEvents {
    public static final SoundType HARD_GLASS = new ForgeSoundType(1, 1, () -> SoundEvents.GLASS_BREAK, () -> SoundEvents.STONE_STEP, () -> SoundEvents.GLASS_PLACE, () -> SoundEvents.GLASS_HIT, () -> SoundEvents.GLASS_FALL);

    static void staticInit() {
    }
}

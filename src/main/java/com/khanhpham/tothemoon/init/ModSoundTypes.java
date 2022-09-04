package com.khanhpham.tothemoon.init;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

@SuppressWarnings("deprecation")
public class ModSoundTypes {
    public static final SoundType METAL_MACHINE = new SoundType(1, 1, ModSoundEvents.METAL_MACHINE_BREAK, ModSoundEvents.METAL_MACHINE_STEP, ModSoundEvents.METAL_MACHINE_PLACE, SoundEvents.PLAYER_SMALL_FALL, SoundEvents.PLAYER_BIG_FALL);
    public static final SoundType MOON_ROCK = new SoundType(1, 1, ModSoundEvents.MOON_ROCK_BREAK, ModSoundEvents.MOON_ROCK_STEP, ModSoundEvents.MOON_ROCK_PLACE, SoundEvents.PLAYER_SMALL_FALL, SoundEvents.PLAYER_BIG_FALL);
    public static final SoundType MOON_DUST = new SoundType(1, 1, ModSoundEvents.MOON_DUST_BREAK, ModSoundEvents.MOON_DUST_STEP, ModSoundEvents.MOON_DUST_PLACE, SoundEvents.PLAYER_SMALL_FALL, SoundEvents.PLAYER_BIG_FALL);
}

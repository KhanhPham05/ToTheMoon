package com.khanhtypo.tothemoon.registration;

import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import com.khanhtypo.tothemoon.utls.ModUtils;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;

public class ModSoundTypes {
    public static final Object2IntMap<ObjectSupplier<SoundEvent>> SOUND_MAPPING = new Object2IntArrayMap<>();
    public static final ObjectSupplier<SoundEvent> MACHINE_METAL_BREAK = register("machine_metal_break", 3);
    public static final ObjectSupplier<SoundEvent> MACHINE_METAL_PLACE = register("machine_metal_place", 2);
    public static final ObjectSupplier<SoundEvent> MACHINE_METAL_STEP = register("machine_metal_step", 3);
    public static final ObjectSupplier<SoundEvent> METAL_PRESS_USED = register("metal_press_used", 0);
    public static final ObjectSupplier<SoundEvent> MOON_DUST_BREAK = register("moon_dust_break", 3);
    public static final ObjectSupplier<SoundEvent> MOON_DUST_PLACE = register("moon_dust_place", 3);
    public static final ObjectSupplier<SoundEvent> MOON_DUST_STEP = register("moon_dust_step", 3);
    public static final ObjectSupplier<SoundEvent> MOON_ROCK_BREAK = register("moon_rock_break", 3);
    public static final ObjectSupplier<SoundEvent> MOON_ROCK_PLACE = register("moon_rock_place", 3);
    public static final ObjectSupplier<SoundEvent> MOON_ROCK_STEP = register("moon_rock_step", 3);
    public static final SoundType MACHINE_METAL = createType(MACHINE_METAL_BREAK, MACHINE_METAL_PLACE, MACHINE_METAL_STEP, SoundEvents.METAL_HIT ,SoundEvents.METAL_FALL);
    public static final SoundType MOON_ROCK = createType(MOON_ROCK_BREAK, MOON_ROCK_PLACE, MOON_ROCK_STEP, SoundEvents.STONE_HIT, SoundEvents.STONE_FALL);
    public static final SoundType MOON_DUST = createType(MOON_DUST_BREAK, MOON_DUST_PLACE, MOON_DUST_STEP, SoundEvents.SAND_HIT, SoundEvents.SAND_FALL);
    private static SoundType createType(ObjectSupplier<SoundEvent> breakSound, ObjectSupplier<SoundEvent> placeSound, ObjectSupplier<SoundEvent> stepSound, SoundEvent vanillaHitSound, SoundEvent vanillaFallSound) {
        return new ForgeSoundType(1, 1, breakSound, placeSound, stepSound, () -> vanillaHitSound, () -> vanillaFallSound);
    }

    private static ObjectSupplier<SoundEvent> register(String name, int sounds) {
        ObjectSupplier<SoundEvent> supplier = ObjectSupplier.simple(ModRegistries.SOUND_EVENTS, name, () -> SoundEvent.createVariableRangeEvent(ModUtils.location(name)));
        SOUND_MAPPING.put(supplier, sounds);
        return supplier;
    }

    static void staticInit() {
    }
}

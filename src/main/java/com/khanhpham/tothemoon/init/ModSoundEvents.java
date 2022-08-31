package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.utils.DirectRegistry;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSoundEvents {
    public static final DirectRegistry<SoundEvent> ALL_SOUND_EVENTS = new DirectRegistry<>();

    public static final SoundEvent METAL_MACHINE_STEP = register("machine_metal_step");
    public static final SoundEvent METAL_MACHINE_BREAK = register("machine_metal_break");
    public static final SoundEvent METAL_MACHINE_PLACE = register("machine_metal_place");

    public static final SoundEvent MOON_DUST_BREAK = register("moon_dust_break");
    public static final SoundEvent MOON_DUST_PLACE = register("moon_dust_place");
    public static final SoundEvent MOON_DUST_STEP = register("moon_dust_step");

    public static final SoundEvent MOON_ROCK_PLACE = register("moon_rock_place");
    public static final SoundEvent MOON_ROCK_BREAK = register("moon_rock_break");
    public static final SoundEvent MOON_ROCK_STEP = register("moon_rock_step");
    public static final SoundEvent METAL_PRESS_USED = register("metal_press_used");

    private static SoundEvent register(String name) {
        return ALL_SOUND_EVENTS.put(name, new SoundEvent(ModUtils.modLoc(name)));
    }

    @SubscribeEvent
    public static void init(RegisterEvent event) {
        ALL_SOUND_EVENTS.registerAll(event, ForgeRegistries.Keys.SOUND_EVENTS);
    }
}

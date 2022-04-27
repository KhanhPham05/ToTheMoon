package com.khanhpham.tothemoon.init;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;

@Mod.EventBusSubscriber(modid = Names.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSoundEvents {
    public static final HashSet<SoundEvent> SOUND_EVENT_SET = new HashSet<>();
    public static final SoundEvent METAL_MACHINE_STEP = register("machine_metal_step");
    public static final SoundEvent METAL_MACHINE_BREAK = register("machine_metal_break");
    public static final SoundEvent METAL_MACHINE_PLACE = register("machine_metal_place");

    public static final SoundEvent MOON_DUST_BREAK = register("moon_dust_break");
    public static final SoundEvent MOON_DUST_PLACE = register("moon_dust_place");
    public static final SoundEvent MOON_DUST_STEP = register("moon_dust_step");

    public static final SoundEvent MOON_ROCK_PLACE = register("moon_rock_place");
    public static final SoundEvent MOON_ROCK_BREAK = register("moon_rock_break");
    public static final SoundEvent MOON_ROCK_STEP = register("moon_rock_step");

    private static SoundEvent register(String name) {
        ResourceLocation rl = ModUtils.modLoc(name);
        SoundEvent soundEvent = new SoundEvent(rl).setRegistryName(rl);
        SOUND_EVENT_SET.add(soundEvent);
        return soundEvent;
    }

    @SubscribeEvent
    public static void init(RegistryEvent.Register<SoundEvent> event) {
        var reg = event.getRegistry();
        reg.register(METAL_MACHINE_BREAK);
        reg.register(METAL_MACHINE_PLACE);
        reg.register(METAL_MACHINE_STEP);
    }
}

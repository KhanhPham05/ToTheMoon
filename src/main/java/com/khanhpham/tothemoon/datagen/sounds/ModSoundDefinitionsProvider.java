package com.khanhpham.tothemoon.datagen.sounds;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.init.ModSoundEvents;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {

    public ModSoundDefinitionsProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, ToTheMoon.MOD_ID, helper);
    }


    @Override
    public void registerSounds() {
        this.add(ModSoundEvents.METAL_MACHINE_BREAK, 3);
        this.add(ModSoundEvents.METAL_MACHINE_PLACE, 2);
        this.add(ModSoundEvents.METAL_MACHINE_STEP, 3);
        this.add(ModSoundEvents.METAL_PRESS_USED);
        this.add(ModSoundEvents.MOON_DUST_BREAK, 3);
        this.add(ModSoundEvents.MOON_DUST_PLACE, 3);
        this.add(ModSoundEvents.MOON_DUST_STEP, 3);
        this.add(ModSoundEvents.MOON_ROCK_BREAK, 3);
        this.add(ModSoundEvents.MOON_ROCK_PLACE, 3);
        this.add(ModSoundEvents.MOON_ROCK_STEP, 3);
    }

    private void add(SoundEvent soundEvent, int soundParents) {
        String soundEventPath = soundEvent.getLocation().getPath();
        String[] allSoundPaths = new String[soundParents];
        for (int i = 0; i < soundParents; i++) {
            allSoundPaths[i] = soundEventPath + (i + 1);
        }

        super.add(soundEvent, this.soundDefinition(soundEvent, allSoundPaths));
    }

    private void add(SoundEvent soundEvent) {
        super.add(soundEvent, this.soundDefinition(soundEvent, soundEvent.getLocation().getPath()));
    }

    private SoundDefinition soundDefinition(SoundEvent soundEvent, String... sounds) {
        SoundDefinition soundDefinition = SoundDefinition.definition();
        SoundDefinition.Sound[] allSounds = new SoundDefinition.Sound[sounds.length];

        for (int i = 0; i < allSounds.length; i++) {
            allSounds[i] = SoundDefinition.Sound.sound(ModUtils.modLoc(sounds[i]), SoundDefinition.SoundType.SOUND);
        }

        soundDefinition.subtitle(TextUtils.translateFormat("block", soundEvent.getLocation().getPath()));
        return soundDefinition.with(allSounds);
    }
}

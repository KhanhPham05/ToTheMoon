package com.khanhtypo.tothemoon.data.c;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.registration.ModSoundTypes;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import java.util.LinkedList;
import java.util.List;

public class ModSoundDefinitionGenerator extends SoundDefinitionsProvider {
    public ModSoundDefinitionGenerator(PackOutput output, ExistingFileHelper helper) {
        super(output, ToTheMoon.MODID, helper);
    }

    @Override
    public void registerSounds() {
        ModSoundTypes.SOUND_MAPPING.forEach((soundEventObjectSupplier, levels) -> {
            SoundDefinition soundDefinition = definition();
            if (levels > 0) {
                List<SoundDefinition.Sound> sounds = new LinkedList<>();
                for (int i = 1; i <= levels; i++) {
                    sounds.add(sound(soundEventObjectSupplier.getId().withSuffix(String.valueOf(i))));
                }
                soundDefinition.with(sounds.toArray(SoundDefinition.Sound[]::new));
            } else {
                soundDefinition.with(sound(soundEventObjectSupplier.getId()));
            }

            super.add(soundEventObjectSupplier, soundDefinition.subtitle(soundEventObjectSupplier.getId().toLanguageKey("subtitles")));
        });
    }


}

package com.khanhpham.tothemoon.datagen.sounds;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.init.ModSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundSource;

@Deprecated
public class ModSoundsProvider extends SoundDataProvider {

    public ModSoundsProvider(DataGenerator dataProvider) {
        super(dataProvider, ToTheMoon.MOD_ID);
    }

    @Override
    protected void registerSounds() {
        super.add(ModSoundEvents.METAL_MACHINE_BREAK, SoundSource.BLOCKS, 3, "Machine Broken");
        super.add(ModSoundEvents.METAL_MACHINE_STEP, SoundSource.BLOCKS, 3, "Machine Footsteps");
        super.add(ModSoundEvents.METAL_MACHINE_PLACE, SoundSource.BLOCKS, 2, "Machine Placed");

        super.add(ModSoundEvents.MOON_DUST_BREAK, SoundSource.BLOCKS, 3, "Moon Dust Broken");
        super.add(ModSoundEvents.MOON_DUST_PLACE, SoundSource.BLOCKS, 3, "Moon Dust Placed");
        super.add(ModSoundEvents.MOON_DUST_STEP, SoundSource.BLOCKS, 3, "Moon Dust Footsteps");

        super.add(ModSoundEvents.MOON_ROCK_STEP, SoundSource.BLOCKS, 3, "Moon Rock Footsteps");
        super.add(ModSoundEvents.MOON_ROCK_PLACE, SoundSource.BLOCKS, 3, "Moon Rock Placed");
        super.add(ModSoundEvents.MOON_ROCK_BREAK, SoundSource.BLOCKS, 3, "Moon Rock Broken");

        super.add(ModSoundEvents.METAL_PRESS_USED, SoundSource.BLOCKS, "Metal Pressed");
    }
}

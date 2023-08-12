package com.khanhtypo.tothemoon.registration.elements;

import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.sounds.SoundEvent;

public class SoundEventObject extends SimpleObjectSupplier<SoundEvent> {
    public SoundEventObject(String name) {
        super(ModRegistries.SOUND_EVENTS, name, () -> SoundEvent.createVariableRangeEvent(ModUtils.location(name)));
    }
}

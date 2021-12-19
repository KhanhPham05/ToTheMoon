package com.khanhpham.tothemoon.util;

import com.khanhpham.tothemoon.ToTheMoon;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author SilentChaos512
 */
public class TranslationUtils {
    private static final String ENERGY_FORMAT = "%,d";

    private static IFormattableTextComponent create(String pre, String suf, Object... params) {
        return new TranslationTextComponent(String.format("%s.%s.%s", pre, ToTheMoon.ID, suf), params);
    }

    public static IFormattableTextComponent energy(int currentEnergy, int maxEnergy) {
        return create("gui", "energy_index", format(ENERGY_FORMAT, currentEnergy), format(ENERGY_FORMAT, maxEnergy));
    }

    public static String format(String format, Object args) {
        return String.format(format, args);
    }
}

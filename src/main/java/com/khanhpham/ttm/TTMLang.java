package com.khanhpham.ttm;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class TTMLang {
    public static final Map<TranslatableComponent, String> map = new HashMap<>();

    public static final TranslatableComponent TEST_ENERGY_GENERATOR;
    public static final TranslatableComponent COPPER_GEN_LABEL;

    static {
        COPPER_GEN_LABEL = translate("gui", "copper_generator", "Copper Energy Generator");
        TEST_ENERGY_GENERATOR = translate("gui", "test_energy_generator", "Test Energy Generator");
    }

    private static TranslatableComponent translate(String pre, String suf, String value) {
        TranslatableComponent key = new TranslatableComponent(pre + '.' + ToTheMoonMain.MOD_ID + '.' + suf);
        map.put(key, value);
        return key;
    }
}

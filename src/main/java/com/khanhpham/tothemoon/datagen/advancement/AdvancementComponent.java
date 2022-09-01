package com.khanhpham.tothemoon.datagen.advancement;

import com.khanhpham.tothemoon.utils.text.TextUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.HashMap;

public class AdvancementComponent {

    public final HashMap<MutableComponent, String> map = new HashMap<>();

    public MutableComponent add(String titleKey, String titleTranslate, String descriptionTranslate) {
        MutableComponent title = TextUtils.translatable(titleKey);
        MutableComponent description = TextUtils.translatable(titleKey + ".description");

        map.put(title, titleTranslate);
        map.put(description, descriptionTranslate);
        return title;
    }

    public void startTranslating(LanguageProvider language) {
        map.forEach((component, translate) -> language.add(component.getString(), translate));
    }
}

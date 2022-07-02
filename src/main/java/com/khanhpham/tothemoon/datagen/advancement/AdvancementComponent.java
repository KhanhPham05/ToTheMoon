package com.khanhpham.tothemoon.datagen.advancement;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.HashMap;

public class AdvancementComponent {

    public final HashMap<TranslatableComponent, String> map = new HashMap<>();

    public TranslatableComponent add(String titleKey, String titleTranslate, String descriptionTranslate) {
        TranslatableComponent title = new TranslatableComponent(titleKey);
        TranslatableComponent description = new TranslatableComponent(titleKey + ".description");

        map.put(title, titleTranslate);
        map.put(description, descriptionTranslate);
        return title;
    }

    public void startTranslating(LanguageProvider language) {
        map.forEach((component, translate) -> language.add(component.getKey(), translate));
    }
}

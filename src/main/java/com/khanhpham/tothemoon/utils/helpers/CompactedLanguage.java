package com.khanhpham.tothemoon.utils.helpers;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.data.LanguageProvider;

public record CompactedLanguage(TranslatableComponent translatableComponent, String translate) {
    public void addTranslation(LanguageProvider provider) {
        provider.add(translatableComponent.getKey(), translate);
    }
}

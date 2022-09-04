package com.khanhpham.tothemoon.utils.helpers;

import net.minecraftforge.common.data.LanguageProvider;

public record CompactedLanguage(String translatableComponent, String translate) {
    public void addTranslation(LanguageProvider provider) {
        provider.add(translatableComponent, translate);
    }
}

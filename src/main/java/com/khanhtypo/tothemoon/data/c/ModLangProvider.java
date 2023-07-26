package com.khanhtypo.tothemoon.data.c;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.common.TabInstance;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.registration.ModStats;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.stats.Stats;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ModLangProvider extends LanguageProvider {
    private static final Map<Component, String> ENGLISH_TRANSLATION_MAP = new HashMap<>();

    public ModLangProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        this.tabInstances();
        this.blockAndItem();
        this.extraTranslation();
    }

    private void extraTranslation() {
        ENGLISH_TRANSLATION_MAP.forEach((component, s) -> super.add(((TranslatableContents) component.getContents()).getKey(), s));
        ModStats.CONTAINER_INTERACTION_MAP.values().forEach(rl -> super.add("custom_stat." + rl.toLanguageKey(), this.transform(rl.getPath())));
    }

    private void blockAndItem() {
        ModRegistries.ITEMS.getEntries().forEach(
                registryObject -> super.addItem(registryObject, transform(registryObject.getId().getPath()))
        );

        ModRegistries.BLOCKS.getEntries().forEach(
                blockRegistryObject -> super.addBlock(blockRegistryObject, transform(blockRegistryObject.getId().getPath()))
        );
    }

    public static Component createTranslatable(String prefix, String suffix, String translated) {
        Component component = Component.translatable(String.format("%s.%s.%s", prefix, ToTheMoon.MODID, suffix));
        ENGLISH_TRANSLATION_MAP.put(component, translated);
        return component;
    }

    private @NotNull String transform(String itemPath) {
        StringBuilder translationBuilder = new StringBuilder(itemPath.length());
        translationBuilder.append(Character.toUpperCase(itemPath.charAt(0)));
        for (int i = 1; i < itemPath.length(); i++) {
            if (itemPath.charAt(i) == '_') {
                translationBuilder.append(" ");
            } else if (i > 1 && itemPath.charAt(i - 1) == '_') {
                translationBuilder.append(Character.toUpperCase(itemPath.charAt(i)));
            } else translationBuilder.append(itemPath.charAt(i));
        }

        return translationBuilder.toString();
    }


    private void tabInstances() {
        TabInstance.ALL_TABS.forEach(tab -> super.add(tab.getTranslationKey(), tab.getTranslation()));
    }
}

package com.khanhtypo.tothemoon.utls;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.data.c.ModLanguageGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;

public final class AppendableComponent {
    private final Component component;
    private final String key;
    private final String defaultTranslation;

    private AppendableComponent(Component component, String key, String defaultTranslation) {
        this.component = component;
        this.key = key;
        this.defaultTranslation = defaultTranslation;
        ModLanguageGenerator.addTranslationMapping(key, defaultTranslation);
    }

    public static AppendableComponent create(String prefix, String suffix, String defaultTranslation) {
        return create(String.format("%s.%s.%s", prefix, ToTheMoon.MODID, suffix), defaultTranslation);
    }

    public static AppendableComponent create(String key, String defaultTranslation) {
        final TranslatableContents contents = new TranslatableContents(key, defaultTranslation, TranslatableContents.NO_ARGS);
        final MutableComponent mutableComponent = MutableComponent.create(contents);
        return new AppendableComponent(mutableComponent, key, defaultTranslation);
    }

    public Component getComponent() {
        return component;
    }

    public String getKey() {
        return key;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public Component withParam(Object... param) {
        return Component.translatable(this.getKey(), param);
    }
}

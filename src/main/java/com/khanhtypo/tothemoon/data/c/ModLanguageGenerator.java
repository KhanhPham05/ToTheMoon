package com.khanhtypo.tothemoon.data.c;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.common.TabInstance;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.utls.AppendableComponent;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModLanguageGenerator extends LanguageProvider {
    public static final Map<String, String> DEFAULT_TRANSLATION_MAP = new TreeMap<>();
    public static final AppendableComponent MOD_NEEDS_INSTALLATION = AppendableComponent.create("tooltip", "mod_needs_installation", "Install %s.");
    public static final AppendableComponent TOGGLE = AppendableComponent.create("button", "Toggle : %s");
    public static final Component ON = createTranslatable("tooltip", "on", "On");
    public static final Component OFF = createTranslatable("tooltip", "off", "Off");

    public ModLanguageGenerator(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    public static Component createTranslatable(String prefix, String suffix, String translated) {
        String key = String.format("%s.%s.%s", prefix, ToTheMoon.MODID, suffix);
        Component component = Component.translatable(key);
        DEFAULT_TRANSLATION_MAP.put(key, translated);
        return component;
    }

    public static @NotNull String transform(String itemPath) {
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

    public static void addTranslationMapping(String key, String translation) {
        DEFAULT_TRANSLATION_MAP.put(key, translation);
    }

    @Override
    protected void addTranslations() {
        this.tabInstances();
        this.blockAndItem();
        this.extraTranslation();
    }

    private void extraTranslation() {
        DEFAULT_TRANSLATION_MAP.forEach(super::add);
    }

    private void blockAndItem() {
        this.registriesToLang(ModRegistries.ITEMS, super::addItem);
        this.registriesToLang(ModRegistries.BLOCKS, super::addBlock);
    }

    private <T> void registriesToLang(DeferredRegister<T> registry, BiConsumer<Supplier<T>, String> consumer) {
        registry.getEntries().forEach(
                t -> consumer.accept(t, transform(t.getId().getPath()))
        );
    }

    private void tabInstances() {
        TabInstance.ALL_TABS.forEach(tab -> super.add(tab.getTranslationKey(), tab.getTranslation()));
    }
}

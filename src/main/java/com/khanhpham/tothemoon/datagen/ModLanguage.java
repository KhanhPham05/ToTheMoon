package com.khanhpham.tothemoon.datagen;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Supplier;

public class ModLanguage extends LanguageProvider {
    public ModLanguage(DataGenerator gen) {
        super(gen, Names.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ModBlocks.BLOCK_DEFERRED_REGISTER.getEntries().forEach(this::addBlocks);
        ModItems.ITEM_DEFERRED_REGISTER.getEntries().forEach(this::addItems);
        add("gui.tothemoon.alloy_smelter", "Alloy Smelter");
        add("gui.tothemoon.metal_press", "Metal Press");
    }



    private void addBlocks(Supplier<Block> supplier) {
        Block block = supplier.get();
        super.add(block, this.convertToTranslatedText(block));
    }

    private void addItems(Supplier<Item> supplier) {
        Item item = supplier.get();
        super.add(item, this.convertToTranslatedText(item));
    }

    private <T extends ForgeRegistryEntry<T>> String convertToTranslatedText(T entry) {
        StringBuilder buffer = new StringBuilder();
        String id = entry.getRegistryName().getPath();
        buffer.append(Character.toUpperCase(id.charAt(0)));

        for (int i = 1; i < id.length(); i++) {
            char c = id.charAt(i);

            if (c == '_') {
                buffer.append(' ');
            } else
            if (id.charAt(i - 1) == ' ' || id.charAt(i - 1) == '_') {
                buffer.append(Character.toUpperCase(id.charAt(i)));
            } else {
                buffer.append(c);
            }
        }

        return buffer.toString();
    }
}

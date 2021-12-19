package com.khanhpham.tothemoon.datagen;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.api.registration.FluidRegistryObject;
import com.khanhpham.tothemoon.registries.ModFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.data.LanguageProvider;

import static com.khanhpham.tothemoon.registries.ModBlocks.*;
import static com.khanhpham.tothemoon.registries.ModItems.*;

public class EnglishProvider extends LanguageProvider {
    public static final TranslationTextComponent ENERGY_GENERATOR_TITLE = new TranslationTextComponent(translate("gui", "energy_generator"));
    public static final TranslationTextComponent ENERGY_BANK_TITLE = new TranslationTextComponent(translate("gui", "energy_bank"));


    public EnglishProvider(DataGenerator gen) {
        super(gen, ToTheMoon.ID, "en_us");
    }

    private static String translate(String pre, String suf) {
        return pre + ".tothemoon." + suf;
    }

    @Override
    protected void addTranslations() {
        items();
        blocks();
        gases();
        misc();
    }

    private void misc() {
        add(ENERGY_GENERATOR_TITLE, "Energy Generator");
        add(ENERGY_BANK_TITLE, "Energy Bank");
        add("gui.tothemoon.energy_index", "Energy: %s / %s FE");
    }

    private void add(TranslationTextComponent component, String name) {
        super.add(component.getKey(), name);
    }

    private void gases() {
        addFluid(ModFluids.MOLTEN_REDSTONE_STEEL_ALLOY, "Molten Redstone Steel Alloy");
        addFluid(ModFluids.MOLTEN_REDSTONE_ALLOY, "Molten Redstone Alloy");
    }

    private void items() {
        addItem(COPPER_DUST, "Copper Dust");
        addItem(COPPER_INGOT, "Copper Ingot");
        addItem(COPPER_NUGGET, "Copper Nugget");
        addItem(COPPER_SHEET, "Copper Sheet");

        addItem(STEEL_INGOT, "Steel Ingot");
        addItem(STEEL_NUGGET, "Steel Nugget");
        addItem(STEEL_DUST, "Steel Dust");
        addItem(STEEL_SHEET, "Steel Sheet");

        addItem(REDSTONE_STEEL_DUST, "Redstone Steel Alloy Dust");
        addItem(REDSTONE_STEEL_INGOT, "Redstone Steel Alloy Ingot");
        addItem(REDSTONE_STEEL_NUGGETS, "Redstone Steel Alloy Nugget");
        addItem(REDSTONE_STEEL_SHEET, "Redstone Steel Alloy Sheet");

        addItem(STEEL_ENERGY_COIL, "Steel Energy Coil");
        addItem(REDSTONE_POWERED_ENERGY_COIL, "redstone Powered Energy Coil");
        addItem(BASIC_ENERGY_COIL, "Basic Energy Coil");

        addItem(REDSTONE_STEEL_ALLOY_BUCKET, "Bucket Of Molten Redstone Steel Alloy");
        addItem(REDSTONE_ALLOY_BUCKET, "Bucket of Molten Redstone Alloy");
    }

    private void blocks() {
        addBlock(REDSTONE_STEEL_ALLOY_BLOCK, "Block of Redstone Steel Alloy");
        addBlock(MOON_SURFACE_ROCK, "Moon Surface Rock");
        //addBlock(BRONZE_BLOCK, "Block of Bronze");
        addBlock(COPPER_BLOCK, "Block of Copper");
        addBlock(COPPER_ORE, "Copper Ore");
        addBlock(COPPER_MOON_ORE, "Copper Moon Ore");
        addBlock(STEEL_BLOCK, "Block of Steel");
        addBlock(STEEL_MACHINE_CASING, "Steel Machine Casing");
        addBlock(BASIC_MACHINE_CASING, "Basic Machine Casing");
        addBlock(REDSTONE_POWERED_MACHINE_CASING, "Redstone Powered Machine Casing");
        addBlock(ENERGY_BANK, "Energy Bank");
        addBlock(ENERGY_GENERATOR, "Energy Generator");
    }

    private <T extends Fluid> void addFluid(FluidRegistryObject<T> fluid, String name) {
        super.add(fluid.getTranslationKey(), name);
    }
}

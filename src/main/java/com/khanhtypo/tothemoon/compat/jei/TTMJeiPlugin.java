package com.khanhtypo.tothemoon.compat.jei;

import com.khanhtypo.tothemoon.ModUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class TTMJeiPlugin implements IModPlugin {
    public static final ResourceLocation PLUGIN_ID = ModUtils.location("ttm_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        ModCategories.forEachTab(t -> t.register(registration));
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        ModCategories.forEachTab(t -> t.registerTransferHandler(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        ModCategories.forEachTab(t -> t.registerCatalyst(registration));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ModCategories.forEachTab(t -> t.gatherRecipes(registration));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }
}
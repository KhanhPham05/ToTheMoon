package com.khanhpham.tothemoon.core.recipes.type;

import com.khanhpham.tothemoon.core.recipes.SingleProcessRecipe;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public final class SingleProcessRecipeType implements RecipeType<SingleProcessRecipe> {
    public static final Set<SingleProcessRecipeType> ALL_TYPES = new HashSet<>();

    public final ResourceLocation location;
    private final Supplier<? extends ItemLike> catalystIcon;

    public SingleProcessRecipeType(String name, Supplier<? extends ItemLike> catalystIcon) {
        this(ModUtils.modLoc(name), catalystIcon);
    }

    public SingleProcessRecipeType(ResourceLocation location, Supplier<? extends ItemLike> catalystIcon) {
        this.location = location;
        this.catalystIcon = catalystIcon;
        ALL_TYPES.add(this);
    }

    @Override
    public String toString() {
        return this.location.toString();
    }

    public ItemStack getCatalystIcon() {
        return new ItemStack(this.catalystIcon.get());
    }

    public Component getDisplayName() {
        return ModLanguage.create("jei", this.location.getPath());
    }
}

package com.khanhtypo.tothemoon.serverdata.recipes;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.registration.elements.SimpleObjectSupplier;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ToTheMoon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeTypeObject<T extends BaseRecipe<? extends Container>> extends SimpleObjectSupplier<RecipeType<T>> {
    private static final Set<Consumer<RegisterRecipeBookCategoriesEvent>> RECIPE_BOOK_ORDERS = new HashSet<>();
    private final RegistryObject<RecipeSerializer<T>> serializer;
    private final Class<T> recipeClass;
    private final RecipeBookType recipeBookType;

    public RecipeTypeObject(String name, Class<T> recipeClass, Supplier<RecipeSerializer<T>> serializer, ItemLike recipeCategoryIcon) {
        super(ModRegistries.RECIPE_TYPE.register(name, () -> RecipeType.simple(ModUtils.location(name))));
        this.recipeClass = recipeClass;
        this.serializer = ModRegistries.SERIALIZERS.register(name, serializer);
        this.recipeBookType = RecipeBookType.create("TTM_" + name.toUpperCase(Locale.ROOT));
        RECIPE_BOOK_ORDERS.add(event -> {
            final RecipeBookCategories categories = RecipeBookCategories.create(this.recipeBookType.toString(), new ItemStack(recipeCategoryIcon));
            event.registerRecipeCategoryFinder(this.get(), r -> categories);
            event.registerBookCategories(this.recipeBookType, List.of(categories));
        });
    }

    @SubscribeEvent
    public static void onRegisterEvent(RegisterRecipeBookCategoriesEvent event) {
        RECIPE_BOOK_ORDERS.forEach(order -> order.accept(event));
    }

    public Class<T> getRecipeClass() {
        return recipeClass;
    }

    public RecipeSerializer<T> getSerializer() {
        return this.serializer.get();
    }
}

package com.khanhtypo.tothemoon.compat.jei;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicMenu;
import com.khanhtypo.tothemoon.registration.elements.MenuObject;
import com.khanhtypo.tothemoon.serverdata.recipes.BaseRecipe;
import com.khanhtypo.tothemoon.serverdata.recipes.RecipeTypeObject;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BaseRecipeCategory<T extends BaseRecipe<?>> implements IRecipeCategory<T> {
    private final RecipeType<T> recipeType;
    private final MenuObject<?> menuObject;
    private final int width;
    private final int height;
    private final BiConsumer<GuiGraphics, T> extraRenderer;
    private final BiConsumer<IRecipeLayoutBuilder, T> recipeScreenBuilder;
    private final List<T> recipes;
    private IDrawable bg;
    private IDrawable icon;
    private Consumer<IRecipeTransferRegistration> transferHandler;

    private BaseRecipeCategory(RecipeType<T> recipeType, MenuObject<?> menuObject, int width, int height, @Nonnull BiConsumer<IRecipeLayoutBuilder, T> recipeScreenBuilder, @Nullable BiConsumer<GuiGraphics, T> extraRenderer, List<T> recipes) {
        this.recipeType = recipeType;
        this.menuObject = menuObject;
        this.width = width;
        this.height = height;
        this.extraRenderer = extraRenderer;
        this.recipeScreenBuilder = recipeScreenBuilder;
        this.recipes = recipes;
    }

    public static <T extends BaseRecipe<?>> Builder<T> builder(RecipeTypeObject<T> recipeTypeObject, MenuObject<?> menuObject) {
        return new Builder<>(recipeTypeObject, menuObject);
    }

    public static RecipeManager clientRecipeManager() {
        return Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
    }

    public void register(IRecipeCategoryRegistration registration) {
        IGuiHelper drawer = registration.getJeiHelpers().getGuiHelper();
        this.icon = drawer.createDrawableItemStack(new ItemStack(this.menuObject.getTargetedBlock()));
        final ResourceLocation backgroundId = this.menuObject.getId().withPrefix("textures/jei/").withSuffix(".png");
        this.bg = drawer.createDrawable(backgroundId, 0, 0, this.width, this.height);
        registration.addRecipeCategories(this);
    }

    public void registerTransferHandler(IRecipeTransferRegistration registration) {
        if (this.transferHandler != null) {
            this.transferHandler.accept(registration);
        }
    }

    public void registerCatalyst(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(this.menuObject.getTargetedBlock()), this.recipeType);
    }

    private void setTransferHandler(Consumer<IRecipeTransferRegistration> transferHandler) {
        this.transferHandler = transferHandler;
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        if (this.extraRenderer != null) {
            this.extraRenderer.accept(guiGraphics, recipe);
        }
    }

    @Override
    public RecipeType<T> getRecipeType() {
        return this.recipeType;
    }

    @Override
    public Component getTitle() {
        return this.menuObject.getGuiTitle();
    }

    @Override
    public IDrawable getBackground() {
        return this.bg;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
        this.recipeScreenBuilder.accept(builder, recipe);
    }

    public void gatherRecipes(IRecipeRegistration registration) {
        registration.addRecipes(this.recipeType, this.recipes);
    }
    public static final class Builder<T extends BaseRecipe<? extends Container>> {
        private final MenuObject<?> menuObject;
        private final RecipeType<T> recipeType;
        @Nullable
        private BiConsumer<GuiGraphics, T> extraRenderer = null;
        private BiConsumer<IRecipeLayoutBuilder, T> recipeScreenBuilder = null;
        @Nullable
        private Consumer<IRecipeTransferRegistration> transferRegister;
        @Nullable
        private List<T> recipes;

        public Builder(RecipeTypeObject<T> recipeTypeObject, MenuObject<?> menuObject) {
            this.menuObject = menuObject;
            this.recipeType = new RecipeType<>(recipeTypeObject.getId(), recipeTypeObject.getRecipeClass());
        }

        public Builder<T> setExtraRenderer(@Nullable BiConsumer<GuiGraphics, T> extraRenderer) {
            this.extraRenderer = extraRenderer;
            return this;
        }

        public Builder<T> setRecipeScreenBuilder(BiConsumer<IRecipeLayoutBuilder, T> recipeScreenBuilder) {
            this.recipeScreenBuilder = recipeScreenBuilder;
            return this;
        }

        @SuppressWarnings("unchecked")
        public <A extends BasicMenu> Builder<T> setRecipeTransfer(Class<A> menuClass, int recipeSlotStart, int recipeSlotCount, int inventorySlotStart) {
            this.transferRegister = registration -> registration.addRecipeTransferHandler(menuClass, (MenuType<A>) this.menuObject.get(), this.recipeType, recipeSlotStart, recipeSlotCount, inventorySlotStart, 9 * 4);
            return this;
        }

        public Builder<T> collectedRecipe(List<T> recipes) {
            this.recipes = recipes;
            return this;
        }

        public BaseRecipeCategory<T> build(ImmutableSet.Builder<BaseRecipeCategory<?>> setBuilder, int tabWidth, int tabHeight) {
            Preconditions.checkNotNull(this.recipeScreenBuilder);
            Preconditions.checkNotNull(this.recipes);
            BaseRecipeCategory<T> recipeCategory = new BaseRecipeCategory<>(this.recipeType, this.menuObject, tabWidth, tabHeight, this.recipeScreenBuilder, this.extraRenderer, this.recipes);
            if (this.transferRegister != null) recipeCategory.setTransferHandler(this.transferRegister);
            setBuilder.add(recipeCategory);
            return recipeCategory;
        }
    }
}

package com.khanhpham.tothemoon.utils.helpers;

import com.google.common.collect.ImmutableMap;
import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.blockentities.FluidCapableBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")

public class ModUtils {
    public static final IntegerProperty ENERGY_LEVEL = IntegerProperty.create("level", 0, 10);
    public static final IntegerProperty TANK_LEVEL = IntegerProperty.create("fluid_level", 0, 12);
    public static final Random RANDOM = new Random();

    private ModUtils() {
        throw new IllegalStateException("Utilities Class");
    }

    public static ResourceLocation modLoc(String loc) {
        return new ResourceLocation(Names.MOD_ID, loc);
    }

    public static ResourceLocation append(ResourceLocation pre, String suf) {
        String path = pre.getPath() + suf;
        return new ResourceLocation(pre.getNamespace(), path);
    }

    public static TranslatableComponent translate(String key, Object... param) {
        return new TranslatableComponent(key, param);
    }

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(ResourceLocation location) {
        return new RecipeType<>() {
            @Override
            public String toString() {
                return location.toString();
            }
        };
    }

    public static <T extends IForgeRegistryEntry<T>> String registryToPath(@Nonnull T entry) {
        return getPath(entry);
    }

    public static void log(String message, Object... arguments) {
        ToTheMoon.LOG.info(message, arguments);
    }

    public static String getFullItemName(Item item) {
        return Registry.ITEM.getKey(item).toString();
    }

    public static <T extends IForgeRegistryEntry<T>> String getPath(T object) {
        return Objects.requireNonNull(object.getRegistryName()).getPath();
    }


    public static <T> T roll(T whenHit, int chance, T ifNot) {
        int attempt = RANDOM.nextInt(100);
        return attempt < chance ? whenHit : ifNot;
    }

    public static ItemStack getBucketItem(Fluid fluid) {
        String namespace = Objects.requireNonNull(fluid.getRegistryName()).getNamespace();
        String path = fluid.getRegistryName().getPath();

        ResourceLocation bucketItem = new ResourceLocation(namespace, path + "_bucket");
        ResourceLocation bucketItem1 = new ResourceLocation(namespace, "bucket_" + path);

        if (Registry.ITEM.containsKey(bucketItem)) {
            return new ItemStack(Registry.ITEM.get(bucketItem));
        } else if (Registry.ITEM.containsKey(bucketItem1)) {
            return new ItemStack(Registry.ITEM.get(bucketItem1));
        }

        throw new IllegalStateException("The fluid have no representative bucket");
    }

    public static CompoundTag loadFluidToBlock(Level pLevel, BlockPos pPos, ItemStack pStack) {
        CompoundTag tag = pStack.getOrCreateTag();
        if (tag.contains("ttmData", 10)) {
            CompoundTag dataTag = tag.getCompound("ttmData");
            int fluidAmount = dataTag.getInt("fluidAmount");
            Fluid fluid = Registry.FLUID.get(ResourceLocation.tryParse(Objects.requireNonNull(dataTag.get("fluid")).getAsString()));
            if (pLevel.getBlockEntity(pPos) instanceof FluidCapableBlockEntity tile) {
                tile.setFluid(new FluidStack(fluid, fluidAmount));
            }

            return dataTag;
        }

        return tag;
    }

    public static List<ItemStack> getItemsForTags(TagKey<Item> tag, ItemStack slotItem) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).getTag(tag).stream().filter(item -> !slotItem.is(item)).map(ItemStack::new).collect(Collectors.toList());
    }

    public static List<Item> getItemsForTags(String tagName) {
        return ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation(tagName))).stream().toList();
    }

    public static void setupMenuScreen(AbstractContainerScreen<?> screen, String imageNameWithPng, PoseStack pose) {
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, getTextureId(imageNameWithPng));
        screen.blit(pose, screen.getGuiLeft(), screen.getGuiTop(), 0, 0, screen.getXSize(), screen.getYSize());
    }

    @Nonnull
    public static ResourceLocation getTextureId(String imageNameWithPng) {
        return modLoc("textures/gui/" + imageNameWithPng);
    }

    public static <C extends Container, R extends Recipe<C>> Map<ResourceLocation, R> getResourceRecipes(Level level, RecipeType<R> recipeType, ResourceLocation recipeLocation) {
        var recipes = level.getRecipeManager().getAllRecipesFor(recipeType);
        for (R recipe : recipes) {
            if (recipe.getId().equals(recipeLocation)) return ImmutableMap.of(recipeLocation, recipe);
        }
        return ImmutableMap.of();
    }

    //Will leave this method unused but ot remove. Who knows one day I need it.
    @Nullable
    @SuppressWarnings("unused")
    public static <C extends Container, R extends Recipe<C>> R getResourceRecipe(Level level, RecipeType<R> recipeType, String name) {
        if (level != null) {
            if (ResourceLocation.isValidResourceLocation(name)) {
                ResourceLocation recipeName = new ResourceLocation(name);
                var recipes = level.getRecipeManager().getAllRecipesFor(recipeType).stream().filter(recipe -> recipe.getId().equals(recipeName)).toList();
                if (recipes.size() > 0) return recipes.get(0);
                else
                    throw new IllegalStateException(String.format("Recipe with id %s is not present", recipeName));
            }
        }
        return null;
    }

    public static Item getItemFromName(String itemName) {
        Optional<Item> optionalItem = Registry.ITEM.getOptional(new ResourceLocation(itemName));
        return optionalItem.orElseThrow(() -> new IllegalStateException("No Item For Id [" + itemName + "] Was Found"));
    }

    public static ItemStack getItemStackFromName(String name) {
        return new ItemStack(getItemFromName(name));
    }

    public static boolean isSlotFree(Container container, int slotIndex, ItemStack queueStack) {
        ItemStack slot = container.getItem(slotIndex);
        return slot.isEmpty() || (slot.sameItem(queueStack) && slot.getCount() + queueStack.getCount() <= container.getMaxStackSize());
    }

    @Nullable
    public static <C extends Container, R extends Recipe<C>> R getRecipe(Level level, RecipeType<R> recipeType, C container) {
        return level.getRecipeManager().getRecipeFor(recipeType, container, level).orElse(null);
    }

    @SafeVarargs
    public static Ingredient multiTagsIngredient(TagKey<Item>... tags) {
        LinkedList<Ingredient.TagValue> values = new LinkedList<>();
        for (TagKey<Item> tag : tags) {
            values.add(new Ingredient.TagValue(tag));
        }

        return Ingredient.fromValues(values.stream());
    }

    public static int rollIntRange(int from, int to) {
        return from == to ? from : RANDOM.nextInt(from, to);
    }
}

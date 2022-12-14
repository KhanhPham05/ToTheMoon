package com.khanhpham.tothemoon.utils.helpers;

import com.google.common.collect.ImmutableMap;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.blockentities.FluidCapableBlockEntity;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")

public class ModUtils {
    public static final IntegerProperty ENERGY_LEVEL = IntegerProperty.create("level", 0, 10);
    public static final Random RANDOM = new Random();

    private ModUtils() {
        throw new IllegalStateException("Utilities Class");
    }

    public static ResourceLocation modLoc(String loc) {
        return loc.contains("tothemoon:") ? new ResourceLocation(loc) : new ResourceLocation(ToTheMoon.MOD_ID, loc);
    }

    public static ResourceLocation append(ResourceLocation pre, String suf) {
        String path = pre.getPath() + suf;
        return new ResourceLocation(pre.getNamespace(), path);
    }

    public static MutableComponent translate(String key, Object... param) {
        return Component.translatable(key, param);
    }

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(ResourceLocation location) {
        RecipeType<T> recipeType = RecipeType.simple(location);
        ModRecipes.ALL_RECIPE_TYPES.put(location.getPath(), recipeType);
        return recipeType;
    }

    public static void log(String message, Object... arguments) {
        ToTheMoon.LOG.info(message, arguments);
    }

    public static <T> String getFullName(T object) {
        return RegistryEntries.getKeyFrom(object).toString();
    }

    public static <T> String getPath(T object) {
        return RegistryEntries.getKeyFrom(object).getPath();
    }

    public static <T> T roll(T whenHit, int chance, T ifNot) {
        int attempt = RANDOM.nextInt(100);
        return attempt < chance ? whenHit : ifNot;
    }

    public static ItemStack getBucketItem(Fluid fluid) {
        ResourceLocation fluidFullName = RegistryEntries.FLUID.getKey(fluid);
        String namespace = fluidFullName.getNamespace();
        String path = fluidFullName.getPath();


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

    public static void setupMenuScreen(AbstractContainerScreen<?> screen, String imageNameWithPng, PoseStack pose) {
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, getGuiId(imageNameWithPng));
        screen.blit(pose, screen.getGuiLeft(), screen.getGuiTop(), 0, 0, screen.getXSize(), screen.getYSize());
    }

    @Nonnull
    public static ResourceLocation getGuiId(String imageName) {
        return modLoc("textures/gui/" + (imageName.contains(".png") ? imageName : imageName + ".png"));
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

    public static <T> T getRegistry(Registry<T> registry, ResourceLocation location) {
        if (registry.containsKey(location)) return registry.get(location);
        throw new IllegalStateException("No registry represent for [" + location + "]");
    }

    public static <T> void checkNullAndDo(@Nullable T object, Consumer<T> action) {
        if (object != null) action.accept(object);
    }

    public static <T> List<T> collectCondition(List<T> values, Predicate<T> filter) {
        return values.stream().filter(filter).toList();
    }


    public static String getSerializedName(StringRepresentable enumClass) {
        return enumClass.toString().toLowerCase(Locale.ROOT);
    }

    public static BlockState defaultFacing(Block block, DirectionProperty facing) {
        return block.defaultBlockState().setValue(facing, Direction.NORTH);
    }

    public static BlockState directionalPlacement(BlockPlaceContext pContext, BlockState defaultBlockState, DirectionProperty facing) {
        return defaultBlockState.setValue(facing, pContext.getHorizontalDirection().getOpposite());
    }


    @SuppressWarnings("unchecked")
    public static <A, T extends A> Map<T, String> getEntries(DeferredRegister<A> deferredRegister) {
        return Map.ofEntries(deferredRegister.getEntries().stream().map(ro -> Map.entry(ro.get(), ro.getId().getPath())).toArray(Map.Entry[]::new));
    }

    @NotNull
    public static <T extends Comparable<T>> BlockState setNewBlockState(Level level, BlockPos pos, BlockState state, Property<T> property, T value) {
        if (!state.getValue(property).equals(value)) {
            state = state.setValue(property, value);
            level.setBlock(pos, state, 3);
        }
        return state;
    }
}

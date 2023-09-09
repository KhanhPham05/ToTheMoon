package com.khanhtypo.tothemoon.utls;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import com.khanhtypo.tothemoon.serverdata.recipes.BaseRecipe;
import com.khanhtypo.tothemoon.serverdata.recipes.RecipeTypeObject;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

@SuppressWarnings({"unused"})
public class ModUtils {
    private ModUtils() {
    }

    public static <T> DeferredRegister<T> createRegistry(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, ToTheMoon.MODID);
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(ToTheMoon.MODID, path);
    }

    public static <A, B extends Comparable<B>> TreeSet<A> sortedSet(Function<A, B> compareGetter) {
        return new TreeSet<>(Comparator.comparing(compareGetter));
    }

    public static <A extends ObjectSupplier<?>> TreeSet<A> resourceSortedSet() {
        return sortedSet(ObjectSupplier::getId);
    }

    public static <A, B> TreeMap<A, B> resourceSortedMap(Function<A, ResourceLocation> compareGetter) {
        return new TreeMap<>(Comparator.comparing(compareGetter));
    }

    public static <A extends ObjectSupplier<?>, B> TreeMap<A, B> resourceSortedMap() {
        return resourceSortedMap(ObjectSupplier::getId);
    }

    public static <A> String toArrayString(Collection<A> collection) {
        return toArrayString(collection, A::toString);
    }

    public static <A, B> String toArrayString(Collection<A> collection, @Nullable Function<A, B> transformer) {
        if (collection.isEmpty()) return "";
        final StringBuilder builder = new StringBuilder().append('[');
        Object[] array = transformer == null ? collection.toArray() : collection.stream().map(transformer).toArray();
        builder.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            builder.append(", ").append(array[i].toString());
        }
        return builder.append(']').toString();
    }

    public static boolean canBurn(ItemStack itemStack) {
        return ForgeHooks.getBurnTime(itemStack, null) > 0;
    }

    public static <T extends Comparable<T>> boolean changeBlockState(Level level, BlockEntity blockEntity, Property<T> property, T value, boolean checkPresent) {
        return changeBlockState(level, blockEntity.getBlockPos(), blockEntity.getBlockState(), property, value, checkPresent);
    }

    /**
     * @return true if the block state is altered, otherwise false due to property is not present or property value is unchanged
     */
    public static <T extends Comparable<T>> boolean changeBlockState(Level level, BlockPos pos, BlockState blockState, Property<T> property, T value, boolean checkPresent) {
        if (checkPresent) {
            if (!blockState.hasProperty(property)) {
                ToTheMoon.LOGGER.warn("Could not set property %s because it is not present at block [%s - %s]".formatted(property.toString(), level.getBlockState(pos).getBlock().getName(), pos.toString()));
            }
        }

        T prevValue = blockState.getValue(property);
        if (prevValue != value) {
            blockState = blockState.setValue(property, value);
            level.setBlock(pos, blockState, 3);
            return true;
        }

        return false;
    }

    public static <T extends Comparable<T>> T getStateValue(Level level, BlockPos pos, Property<T> stateProperty) {
        return getStateValue(level.getBlockState(pos), stateProperty);
    }

    public static <T extends Comparable<T>> T getStateValue(BlockState currentState, Property<T> stateProperty) {
        Preconditions.checkState(currentState.hasProperty(stateProperty), "Block [%s] does not has the given property [%s]"
                .formatted(
                        ForgeRegistries.BLOCKS.getKey(currentState.getBlock()),
                        stateProperty.getName()
                ));

        return currentState.getValue(stateProperty);
    }

    public static <C extends Container, T extends BaseRecipe<C>> Optional<T> getRecipeFor(Level level, RecipeTypeObject<T> recipeType, C container) {
        return level.getRecipeManager().getRecipeFor(recipeType.get(), container, level);
    }

    public static ReportedException fillCrashReport(Throwable exception, String reason, String crashCategory, BuilderConsumer<CrashReportCategory> categoryFunction) {
        CrashReport crashReport = CrashReport.forThrowable(exception, reason);
        categoryFunction.apply(crashReport.addCategory(crashCategory));
        return new ReportedException(crashReport);
    }
}

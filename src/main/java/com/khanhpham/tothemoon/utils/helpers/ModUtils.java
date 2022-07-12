package com.khanhpham.tothemoon.utils.helpers;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.blockentities.FluidCapableBlockEntity;
import com.khanhpham.tothemoon.core.blocks.battery.BatteryConnectionMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;

public class ModUtils {
    public static final IntegerProperty ENERGY_LEVEL = IntegerProperty.create("level", 0, 10);
    public static final IntegerProperty TANK_LEVEL = IntegerProperty.create("fluid_level", 0, 12);
    public static final Random RANDOM = new Random();

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
                return location.getPath();
            }
        };
    }

    public static <T extends IForgeRegistryEntry<T>> String convertRlToPath(@Nonnull T entry) {
        return Objects.requireNonNull(entry.getRegistryName()).getPath();
    }

    public static void log(String message, Object... arguments) {
        ToTheMoon.LOG.info(message, arguments);
    }

    @SuppressWarnings("deprecation")
    public static String getNameFromItem(Item item) {
        return Registry.ITEM.getKey(item).toString();
    }

    public static <T extends IForgeRegistryEntry<T>> String getPath(T object) {
        return Objects.requireNonNull(object.getRegistryName()).getPath();
    }

    public static int roll(int whenHit, int chance) {
        int attempt = RANDOM.nextInt(100);
        return attempt < chance ? whenHit : 0;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack getBucketItem(Fluid fluid) {
        String namespace = fluid.getRegistryName().getNamespace();
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

    public static void loadFluidToBlock(Level pLevel, BlockPos pPos, ItemStack pStack) {
        CompoundTag tag = pStack.getOrCreateTag();
        if (tag.contains("ttmData", 10)) {
            CompoundTag dataTag = tag.getCompound("ttmData");
            int fluidAmount = dataTag.getInt("fluidAmount");
            Fluid fluid = Registry.FLUID.get(ResourceLocation.tryParse(Objects.requireNonNull(dataTag.get("fluid")).getAsString()));
            if (pLevel.getBlockEntity(pPos) instanceof FluidCapableBlockEntity tile) {
                tile.setFluid(new FluidStack(fluid, fluidAmount));
            }
        }
    }
}

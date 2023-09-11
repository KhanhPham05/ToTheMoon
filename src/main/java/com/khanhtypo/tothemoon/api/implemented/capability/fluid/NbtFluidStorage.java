package com.khanhtypo.tothemoon.api.implemented.capability.fluid;

import com.khanhtypo.tothemoon.api.abstracts.capabilities.fluid.INbtFluidStorage;
import com.khanhtypo.tothemoon.api.helpers.NbtHelper;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class NbtFluidStorage extends FluidTank implements INbtFluidStorage {
    private final int initialCapacity;

    public NbtFluidStorage(int capacity) {
        super(capacity, a -> true);
        initialCapacity = capacity;
    }

    public NbtFluidStorage(int capacity, Predicate<FluidStack> validator) {
        super(capacity, validator);
        this.initialCapacity = capacity;
    }

    @Override
    public FluidStack getInternal() {
        return super.fluid;
    }

    @Override
    public int getInitialCapacity() {
        return this.initialCapacity;
    }

    @Deprecated
    @Override
    public CompoundTag writeToNBT(CompoundTag nbt) {
        throw new UnsupportedOperationException("Please use %s#saveFluidTankTo(CompoundTag, IFluidTank)".formatted(NbtHelper.class.getSimpleName()));
    }

    @Deprecated
    @Override
    public FluidTank readFromNBT(CompoundTag nbt) {
        throw new UnsupportedOperationException("Please use %s#loadFluidTankFrom(CompoundTag, INbtFluidStorage)".formatted(NbtHelper.class.getSimpleName()));
    }

    @Override
    public void readCompound(@Nullable CompoundTag toRead) {
        if (toRead == null) {
            throw ModUtils.fillCrashReport(new IllegalStateException(), "Tag for item %s is not present (null)", "Null CompoundTag", crashReportCategory -> {
                if (this instanceof IFluidHandlerItem fluidHandlerItem) {
                    ItemStack container = fluidHandlerItem.getContainer();
                    crashReportCategory.setDetail("Item", ModRegistries.getName(Registries.ITEM, container.getItem()));
                    crashReportCategory.setDetail("NBT", container.getOrCreateTag());
                }
                return crashReportCategory;
            });
        }

        if (toRead.contains("Amount", Tag.TAG_INT) && toRead.contains("FluidName", Tag.TAG_STRING)) {
            int fluidAmount = toRead.getInt("Amount");
            Fluid fluid = ModRegistries.getFromName(Registries.FLUID, new ResourceLocation(toRead.getString("FluidName")));
            @Nullable CompoundTag fluidExtraNbt = toRead.contains("FluidNbt", Tag.TAG_COMPOUND) ? toRead.getCompound("FluidNbt") : null;
            super.setFluid(new FluidStack(fluid, fluidAmount, fluidExtraNbt));
        } else {
            throw ModUtils.fillCrashReport(new IllegalStateException(), "Missing FluidStack data ('Amount' & 'FluidName') in tag", "Missing Required Tags", crashReportCategory -> {
                if (this instanceof IFluidHandlerItem handlerItem) {
                    crashReportCategory.setDetail("Item", ModRegistries.getName(Registries.ITEM, handlerItem.getContainer().getItem()));
                    crashReportCategory.setDetail("RootNBT", handlerItem.getContainer().getTag());
                }
                return crashReportCategory.setDetail("AskedNBT", toRead.toString());
            });
        }

        if (toRead.contains("TankCapacity", Tag.TAG_INT)) {
            super.setCapacity(toRead.getInt("TankCapacity"));
        }
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return super.isFluidValid(stack) && (stack.isFluidEqual(this.fluid) || this.isEmpty());
    }
}

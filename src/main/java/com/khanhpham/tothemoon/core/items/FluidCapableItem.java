package com.khanhpham.tothemoon.core.items;

import com.khanhpham.tothemoon.utils.capabilities.ItemFluidHandlerCapabilityProvider;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class FluidCapableItem extends BlockItem {
    private final int fluidCapacity;

    public FluidCapableItem(Block pBlock, Properties pProperties, int fluidCapacity) {
        super(pBlock, pProperties);
        this.fluidCapacity = fluidCapacity;
    }

    @Override
    public final ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemFluidHandlerCapabilityProvider(stack, this.fluidCapacity);
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(TextUtils.translateItemFluidTank(pStack, this.fluidCapacity));
    }

    @Deprecated
    protected abstract int getFluidCapacity();
}

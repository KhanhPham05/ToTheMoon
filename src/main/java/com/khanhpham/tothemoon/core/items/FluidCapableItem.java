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
    public FluidCapableItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public final ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemFluidHandlerCapabilityProvider(stack, this.getFluidCapacity());
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(TextUtils.translateItemFluidTank(pStack, getFluidCapacity()));
    }

    protected abstract int getFluidCapacity();
}

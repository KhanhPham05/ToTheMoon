package com.khanhpham.tothemoon.core.blocks.tanks;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.items.FluidCapableItem;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.text.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TankBlockItem extends FluidCapableItem {
    public TankBlockItem(Block pBlock) {
        super(pBlock, new Properties().tab(ToTheMoon.TAB));
    }

    @Override
    protected int getFluidCapacity() {
        return FluidTankBlockEntity.TANK_CAPACITY;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(TextUtils.translateFormatText("tooltip", "lava_support_only").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.UNDERLINE));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}

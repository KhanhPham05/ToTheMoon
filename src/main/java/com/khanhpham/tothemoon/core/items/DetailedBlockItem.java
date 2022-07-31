package com.khanhpham.tothemoon.core.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DetailedBlockItem extends BlockItem {
    private final MutableComponent component;

    public DetailedBlockItem(Block block, Properties properties, MutableComponent component) {
        super(block, properties);
        this.component = component;
    }

    public DetailedBlockItem(Block block, int maxStack, MutableComponent component) {
        this(block, new Item.Properties().stacksTo(maxStack), component);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(this.component.withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}

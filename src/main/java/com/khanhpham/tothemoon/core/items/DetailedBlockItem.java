package com.khanhpham.tothemoon.core.items;

import com.khanhpham.tothemoon.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
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
        //for some reason, stackTo throws an exception during registration... so i set the durability to 0, so it won't happends
        this(block, ModItems.GENERAL_PROPERTIES.durability(0).stacksTo(maxStack), component);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(this.component.withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}

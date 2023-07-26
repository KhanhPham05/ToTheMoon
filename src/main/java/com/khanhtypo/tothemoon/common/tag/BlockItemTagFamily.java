package com.khanhtypo.tothemoon.common.tag;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BlockItemTagFamily {
    protected final TagFamily<Item> itemTagFamily;
    protected final TagFamily<Block> blockTagFamily;

    protected BlockItemTagFamily(TagKey<Block> blockRoot, TagKey<Item> itemRoot) {
        this.itemTagFamily = TagFamily.forItem(itemRoot);
        this.blockTagFamily = TagFamily.forBlock(blockRoot);
    }
}

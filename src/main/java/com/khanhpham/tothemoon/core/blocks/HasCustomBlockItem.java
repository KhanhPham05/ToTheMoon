package com.khanhpham.tothemoon.core.blocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface HasCustomBlockItem {

    BlockItem getRawItem();

    default Item getItem(ResourceLocation registryName) {
        return getRawItem().setRegistryName(registryName);
    }
}

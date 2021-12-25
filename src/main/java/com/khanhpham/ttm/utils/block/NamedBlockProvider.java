package com.khanhpham.ttm.utils.block;

import com.khanhpham.ttm.utils.NamedInstanceProvider;
import net.minecraft.world.item.BlockItem;

public interface NamedBlockProvider extends NamedInstanceProvider {
    void setBlockItem(BlockItem blockItem);
}

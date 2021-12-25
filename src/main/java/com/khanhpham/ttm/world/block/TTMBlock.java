package com.khanhpham.ttm.world.block;

import com.khanhpham.ttm.ToTheMoonMain;
import net.minecraft.world.level.block.Block;

@Deprecated
public final class TTMBlock extends Block {
    public final String id;

    public TTMBlock(Properties p_49795_, String id) {
        super(p_49795_);
        this.id = id;
        setRegistryName(ToTheMoonMain.MOD_ID, id);
    }
}

package com.khanhpham.ttm.utils.item;

import com.khanhpham.ttm.utils.NamedInstanceProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

@Deprecated
public class ModBlockItem extends BlockItem implements NamedInstanceProvider {
    public final String name;

    public ModBlockItem(Block pBlock, Properties pProperties, String name) {
        super(pBlock, pProperties);
        this.name = name;
    }

    public ModBlockItem(Block parentBlock, Properties properties) {
        super(parentBlock, properties);
        this.name = Objects.requireNonNull(parentBlock.getRegistryName()).getPath();
    }

    @Override
    public String getNamed() {
        return name;
    }

}

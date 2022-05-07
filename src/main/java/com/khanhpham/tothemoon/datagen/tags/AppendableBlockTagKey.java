package com.khanhpham.tothemoon.datagen.tags;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class AppendableBlockTagKey extends AbstractAppendable<Block>{

    public AppendableBlockTagKey(ResourceLocation location) {
        super(BlockTags.create(location));
    }

    public final TagKey<Block> append(String name, Supplier<? extends Block> block) {
        TagKey<Block> child = BlockTags.create(ModUtils.append(this.mainTag.location(), '/' + name));
        this.map.put(child, block.get());
        return child;
    }
}

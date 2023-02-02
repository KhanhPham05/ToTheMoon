package com.khanhpham.tothemoon.datagen.tags;

import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class AppendableBlockTagKey extends AbstractAppendableTag<Block> {

    public AppendableBlockTagKey(ResourceLocation location) {
        super(BlockTags.create(location));
    }

    public AppendableBlockTagKey(TagKey<Block> mainTag) {
        super(mainTag);
    }

    @SafeVarargs
    public final TagKey<Block> append(String name, Supplier<? extends Block>... block) {
        TagKey<Block> child = BlockTags.create(ModUtils.append(this.mainTag.location(), '/' + name));
        for (Supplier<? extends Block> supplier : block) {
            super.map.put(child, supplier.get());
        }
        return child;
    }
}

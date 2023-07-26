package com.khanhtypo.tothemoon.common.tag;

import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import com.khanhtypo.tothemoon.registration.elements.BlockObject;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class BlockItemTag {
    private final TagKey<Item> itemTag;
    private final TagKey<Block> blockTag;

    private BlockItemTag(TagKey<Item> itemTag, TagKey<Block> blockTag) {
        this.itemTag = itemTag;
        this.blockTag = blockTag;
    }

    public static BlockItemTag of(BlockItemTagFamily family, String childName, BlockObject<? extends Block> block) {
        return new BlockItemTag(family.itemTagFamily.createChild(childName, block.getItemObject()), family.blockTagFamily.createChild(childName, block));
    }

    public TagKey<Item> getItemTag() {
        return itemTag;
    }

    public TagKey<Block> getBlockTag() {
        return blockTag;
    }

    public Ingredient toIngredient() {
        return Ingredient.of(this.getItemTag());
    }
}

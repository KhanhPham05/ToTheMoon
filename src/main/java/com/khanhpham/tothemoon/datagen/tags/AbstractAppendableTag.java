package com.khanhpham.tothemoon.datagen.tags;

import net.minecraft.tags.TagKey;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Supplier;

abstract class AbstractAppendableTag<T> {
    protected final TagKey<T> mainTag;
    protected final HashMap<TagKey<T>, T> map = new HashMap<>();

    public AbstractAppendableTag(TagKey<T> mainTag) {
        this.mainTag = mainTag;
    }

    public abstract TagKey<T> append(String name, Supplier<? extends T> supplier);

    public TagKey<T> getMainTag() {
        return mainTag;
    }

    public HashMap<TagKey<T>, T> getMap() {
        return map;
    }

    public Set<TagKey<T>> getAllChild() {
        return this.map.keySet();
    }

}

package com.khanhtypo.tothemoon.registration.elements;

import com.khanhtypo.tothemoon.ToTheMoon;
import net.minecraft.world.item.Item;

public class BasicItem extends ItemObject<Item> {
    public BasicItem(String name) {
        this(name, new Item.Properties());
    }

    public BasicItem(String name, Item.Properties properties) {
        super(name, () -> new Item(properties), ToTheMoon.DEFAULT_ITEM_TAB);
    }
}

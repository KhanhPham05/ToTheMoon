package com.khanhpham.ttm.utils.item;

import com.khanhpham.ttm.utils.NamedInstanceProvider;
import net.minecraft.world.item.Item;

@Deprecated
public class ModItem extends Item implements NamedInstanceProvider {
    public final String name;

    public ModItem(Properties pProperties, String name) {
        super(pProperties);
        this.name = name;
    }

    @Override
    public String getNamed() {
        return name;
    }

}

package com.khanhtypo.tothemoon.common.blockentitiesandcontainer;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class AlwaysSavedContainer extends SimpleContainer {
    protected final BaseMenu menu;

    public AlwaysSavedContainer(BaseMenu menu, int p_19150_) {
        super(p_19150_);
        this.menu = menu;
    }

    @Override
    public void setItem(int p_19162_, ItemStack p_19163_) {
        super.setItem(p_19162_, p_19163_);
        this.setChanged();
    }

    @Override
    public ItemStack removeItem(int p_19159_, int p_19160_) {
        ItemStack itemStack = super.removeItem(p_19159_, p_19160_);
        if (!itemStack.isEmpty()) setChanged();
        return itemStack;
    }

    @Override
    public void setChanged() {
        this.menu.slotsChanged(this);
    }

    public BaseMenu getMenu() {
        return menu;
    }

}

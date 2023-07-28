package com.khanhtypo.tothemoon.common.blockentitiesandcontainer;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BasicMenu;

public class AlwaysSavedResultContainer extends AlwaysSavedContainer {
    public AlwaysSavedResultContainer(BasicMenu menu) {
        super(menu, 1);
    }

    @Override
    public void setChanged() {}
}

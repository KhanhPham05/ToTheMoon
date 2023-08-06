package com.khanhtypo.tothemoon.common.blockentitiesandcontainer;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;

public class AlwaysSavedResultContainer extends AlwaysSavedContainer {
    public AlwaysSavedResultContainer(BaseMenu menu) {
        super(menu, 1);
    }

    @Override
    public void setChanged() {}
}

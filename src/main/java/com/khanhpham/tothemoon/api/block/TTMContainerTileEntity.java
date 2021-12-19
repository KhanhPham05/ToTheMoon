package com.khanhpham.tothemoon.api.block;

import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

/**
 * This TE only have item slots and no energy storage
 */
public abstract class TTMContainerTileEntity extends TileEntity implements IContainerProvider, INamedContainerProvider {
    public TTMContainerTileEntity(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }


}

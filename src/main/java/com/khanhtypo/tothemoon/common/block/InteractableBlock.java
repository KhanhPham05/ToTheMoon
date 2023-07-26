package com.khanhtypo.tothemoon.common.block;

import com.khanhtypo.tothemoon.registration.elements.BlockObject;

import java.util.function.Supplier;

public class InteractableBlock extends BlockObject<ContainerBlock> {
    public InteractableBlock(String name, Supplier<ContainerBlock> blockSupplier) {
        super(name, blockSupplier);
    }
}

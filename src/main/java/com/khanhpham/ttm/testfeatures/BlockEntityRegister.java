package com.khanhpham.ttm.testfeatures;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRegister extends RegistryHelper<BlockEntityType<?>> {
    public BlockEntityRegister(String modid) {
        super(modid);
    }

    @Override
    @Deprecated()
    public <I extends BlockEntityType<?>> I register(String name, I instance) {
        throw new IllegalArgumentException("You can not call this, use another register method instead");
    }

    public <I extends BlockEntity> BlockEntityType<I> register(String name, BlockEntityType.BlockEntitySupplier<I> blockEntitySupplier, Block... validBlocks) {
        final BlockEntityType<I> te = BlockEntityType.Builder.of(blockEntitySupplier, validBlocks).build(null);
        if (!registrySet.add(te.setRegistryName(modid, name))) {
            throw new IllegalArgumentException("Duplicate instance of " + te);
        }
        return te;
    }
}

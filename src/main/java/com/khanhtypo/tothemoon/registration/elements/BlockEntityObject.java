package com.khanhtypo.tothemoon.registration.elements;

import com.khanhtypo.tothemoon.registration.ModRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.function.Supplier;

public class BlockEntityObject<T extends BlockEntity> extends BaseObjectSupplier<BlockEntityType<?>> {
    private BlockEntityObject(String name, Supplier<? extends BlockEntityType<T>> builder) {
        super(ModRegistries.BLOCK_ENTITY_TYPES, name, builder);
    }

    @Nonnull
    @SafeVarargs
    public static <B extends BlockEntity> BlockEntityObject<B> register(String name, BlockEntityType.BlockEntitySupplier<B> constructor, Supplier<? extends Block>... blocks) {
        return new BlockEntityObject<>(name, () -> BlockEntityType.Builder.of(constructor, Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new)).build(null));
    }

    @Override
    @SuppressWarnings("unchecked")
    public BlockEntityType<T> get() {
        return (BlockEntityType<T>) super.get();
    }
}

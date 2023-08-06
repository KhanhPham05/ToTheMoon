package com.khanhtypo.tothemoon.registration.elements;

import com.khanhtypo.tothemoon.common.blockentitiesandcontainer.base.BaseMenu;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

public class BlockEntityObject<T extends BlockEntity> extends BaseObjectSupplier<BlockEntityType<?>> {

    private BlockEntityObject(String name, Supplier<? extends BlockEntityType<T>> builder) {
        super(ModRegistries.BLOCK_ENTITY_TYPES, name, builder);
    }

    @Nonnull
    public static <B extends BlockEntity, M extends BaseMenu> BlockEntityObject<B> register(String name, BlockEntityType.BlockEntitySupplier<B> constructor, Supplier<? extends Block> blocks) {
        return new BlockEntityObject<>(name, () -> BlockEntityType.Builder.of(constructor, blocks.get()).build(null));
    }

    @Nullable
    public T create(BlockPos blockPos, BlockState state) {
        return this.get().create(blockPos, state);
    }

    public void openBlockEntityContainer(Level level, BlockPos pos, Player player) {
        Optional<T> optional = level.getBlockEntity(pos, this.get());
        if (optional.isPresent() && optional.get() instanceof MenuProvider blockEntity) {
            player.openMenu(blockEntity);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public BlockEntityType<T> get() {
        return (BlockEntityType<T>) super.get();
    }
}

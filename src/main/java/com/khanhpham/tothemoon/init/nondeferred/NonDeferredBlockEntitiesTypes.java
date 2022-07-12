package com.khanhpham.tothemoon.init.nondeferred;

import com.khanhpham.tothemoon.core.blocks.tanks.FluidTankBlockEntity;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class NonDeferredBlockEntitiesTypes {
    public static final Set<BlockEntityType<?>> ALL_BLOCK_ENTITIES = new HashSet<>();

    public static final BlockEntityType<FluidTankBlockEntity> FLUID_TANK_NON_DEFERRED;

    static {
        FLUID_TANK_NON_DEFERRED = register("fluid_tanks", BlockEntityType.Builder.of(FluidTankBlockEntity::new, NonDeferredBlocks.FLUID_TANK_BLOCK));
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(@Nonnull String name, BlockEntityType.Builder<T> builder) {
        BlockEntityType<T> blockEntityType = builder.build(null);
        ALL_BLOCK_ENTITIES.add(blockEntityType.setRegistryName(ModUtils.modLoc(name)));
        return blockEntityType;
    }

    public static void registerAll(IForgeRegistry<BlockEntityType<?>> event) {
        ALL_BLOCK_ENTITIES.forEach(event::register);
    }
}

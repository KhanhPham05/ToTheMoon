package com.khanhtypo.tothemoon.mixin;

import com.khanhtypo.tothemoon.registration.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilBlock.class)
public abstract class AnvilBlockMixin {
    @Inject(method = "onLand", at = @At("RETURN"))
    public void onLand(Level pLevel, BlockPos pPos, BlockState pState, BlockState pReplaceableState, FallingBlockEntity pFallingBlock, CallbackInfo ci) {
        for (ItemEntity itemEntity : pLevel.getEntities(EntityType.ITEM, AABB.of(new BoundingBox(pPos)), item -> item.getItem().is(Items.COAL))) {
            itemEntity.setItem(new ItemStack(ModItems.COAL_DUST.get(), itemEntity.getItem().getCount()));
        }
    }
}

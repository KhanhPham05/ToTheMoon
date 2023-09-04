package com.khanhtypo.tothemoon.mixin;

import com.khanhtypo.tothemoon.registration.ModRecipeTypes;
import com.khanhtypo.tothemoon.serverdata.recipes.AnvilSmashingRecipe;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedList;
import java.util.List;

@Mixin(AnvilBlock.class)
public abstract class AnvilBlockMixin {
    @Unique
    private static final SimpleContainer ttm$simpleContainer = new SimpleContainer(1);

    @Unique
    private static final List<ItemEntity> ttm$list = new LinkedList<>();
    @Inject(method = "onLand", at = @At("RETURN"))
    public void onLand(Level pLevel, BlockPos pPos, BlockState pState, BlockState pReplaceableState, FallingBlockEntity pFallingBlock, CallbackInfo ci) {
        if (!pLevel.isClientSide()) {
            ttm$list.clear();
            pLevel.getEntities(EntityType.ITEM, AABB.of(new BoundingBox(pPos)), itemEntity -> {
                ttm$simpleContainer.setItem(0, itemEntity.getItem());
                return ModUtils.getRecipeFor(pLevel, ModRecipeTypes.ANVIL_SMASHING, ttm$simpleContainer).isPresent();
            }, ttm$list);
            if (!ttm$list.isEmpty()) {
                for (ItemEntity itemEntity : ttm$list) {
                    ttm$simpleContainer.setItem(0, itemEntity.getItem());
                    AnvilSmashingRecipe recipe = ModUtils.getRecipeFor(pLevel, ModRecipeTypes.ANVIL_SMASHING, ttm$simpleContainer).orElseThrow();
                    itemEntity.setItem(recipe.assemble(ttm$simpleContainer, pLevel.registryAccess()).copyWithCount(itemEntity.getItem().getCount()));
                }
            }
        }
    }
}

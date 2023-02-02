package com.khanhpham.tothemoon.core.blocks;

import com.google.common.base.Preconditions;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DecorationBlocks {
    public static final List<DecorationBlocks> ALL_DECORATION_BLOCKS = new ArrayList<>();
    private final RegistryObject<Block> craftedFrom;

    @Nullable
    private final RegistryObject<StairBlock> stairBlock;
    @Nullable
    private final RegistryObject<SlabBlock> slabBlock;

    @Nullable
    private RegistryObject<Block> bricks;

    DecorationBlocks(RegistryObject<Block> craftedFrom, @Nullable RegistryObject<StairBlock> stairBlock, @Nullable RegistryObject<SlabBlock> slabBlock) {
        this.craftedFrom = craftedFrom;
        this.stairBlock = stairBlock;
        this.slabBlock = slabBlock;
        ALL_DECORATION_BLOCKS.add(this);
    }

    public DecorationBlocks setBricks(RegistryObject<Block> bricks) {
        this.bricks = bricks;
        return this;
    }

    public StairBlock getStairBlock() {
        Preconditions.checkNotNull(this.stairBlock);
        return stairBlock.get();
    }

    public SlabBlock getSlabBlock() {
        Preconditions.checkNotNull(this.slabBlock);
        return slabBlock.get();
    }

    public boolean hasStairBlock() {
        return this.stairBlock != null && this.stairBlock.isPresent();
    }

    public boolean hasSlabBlock() {
        return this.slabBlock != null && this.slabBlock.isPresent();
    }

    private boolean hasBricks() {
        return this.bricks != null && this.bricks.isPresent();
    }

    public void generateCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        final Pair<String, InventoryChangeTrigger.TriggerInstance> triggerInstance = Pair.of("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(this.craftedFrom.get()));
        if (this.hasSlabBlock()) {
            unlock(
                    ShapedRecipeBuilder.shaped(this.getSlabBlock(), 6)
                    .pattern("AAA")
                    .define('A', this.craftedFrom.get()),
                    triggerInstance
            ).save(consumer, ModUtils.modLoc("crafting/" + ModUtils.getPath(this.getSlabBlock())));

            unlock(SingleItemRecipeBuilder.stonecutting(Ingredient.of(this.craftedFrom.get()), getSlabBlock(), 2), triggerInstance)
                    .save(consumer, ModUtils.modLoc("stonecutting/" + ModUtils.getPath(this.getSlabBlock())));
        }

        if (this.hasStairBlock()) {
            //noinspection ConstantConditions
            unlock(ShapedRecipeBuilder.shaped(getStairBlock(), 4)
                    .pattern("A  ")
                    .pattern("AA ")
                    .pattern("AAA")
                    .define('A', this.craftedFrom.get()),
                    triggerInstance
            ).save(consumer, ModUtils.modLoc("crafting/" + this.stairBlock.getId().getPath()));

            unlock(SingleItemRecipeBuilder.stonecutting(Ingredient.of(this.craftedFrom.get()), getStairBlock()), triggerInstance)
                    .save(consumer, ModUtils.modLoc("stonecutting/" + this.stairBlock.getId().getPath()));
        }

        if (this.hasBricks()) {
            unlock(
                    ShapedRecipeBuilder.shaped(getBricks(), 4)
                            .define('A', this.getMaterial())
                            .pattern("AA")
                            .pattern("AA")
                    , triggerInstance
            ).save(consumer, ModUtils.modLoc("crafting/" + this.getBricksId()));

            unlock(
                    SingleItemRecipeBuilder.stonecutting(Ingredient.of(this.getMaterial()), this.getBricks()),
                    triggerInstance
            ).save(consumer, ModUtils.modLoc("stonecutting/" + this.getBricksId()));
        }
    }

    private ItemLike getMaterial() {
        return this.craftedFrom.get();
    }

    private String getMaterialId() {
        return this.craftedFrom.getId().getPath();
    }

    private Block getBricks() {
        Preconditions.checkNotNull(this.bricks);
        return this.bricks.get();
    }

    private String getBricksId() {
        Preconditions.checkNotNull(this.bricks);
        return this.bricks.getId().getPath();
    }

    public void registerStateAndModels(BlockStateProvider blockStateProvider) {
        ResourceLocation id = ModUtils.modLoc("block/" + this.craftedFrom.getId().getPath());


        if (this.hasSlabBlock()) {
            blockStateProvider.slabBlock(this.getSlabBlock(), id, id);
        }

        if (this.hasStairBlock()) {
            blockStateProvider.stairsBlockWithRenderType(this.getStairBlock(), id, "cutout");
        }
    }

    private RecipeBuilder unlock(final RecipeBuilder builder, final Pair<String, InventoryChangeTrigger.TriggerInstance> triggerInstance) {
        return builder.unlockedBy(triggerInstance.getFirst(), triggerInstance.getSecond());
    }

    public static final class Builder {
        private final RegistryObject<Block> craftedFrom;
        @Nullable
        private RegistryObject<StairBlock> stairBlock;
        @Nullable
        private RegistryObject<SlabBlock> slabBlock;


        public Builder(RegistryObject<Block> craftedFrom) {
            this.craftedFrom = craftedFrom;
        }

        public static Builder builder(RegistryObject<Block> craftedFrom) {
            return new Builder(craftedFrom);
        }

        public static DecorationBlocks buildBoth(RegistryObject<Block> craftedFrom) {
            return builder(craftedFrom).setSlab().setStair().build();
        }

        public Builder setStair() {
            Preconditions.checkState(stairBlock == null, "Stair block has already been set.");
            this.stairBlock = ModBlocks.BLOCK_DEFERRED_REGISTER.register(craftedFrom.getId().getPath() + "_stair", () -> new StairBlock(() -> this.craftedFrom.get().defaultBlockState(), BlockBehaviour.Properties.copy(this.craftedFrom.get())));
            return this;
        }

        public Builder setSlab() {
            Preconditions.checkState(this.slabBlock == null, "Slab Block has already been set.");
            this.slabBlock = ModBlocks.BLOCK_DEFERRED_REGISTER.register(craftedFrom.getId().getPath() + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(this.craftedFrom.get())));
            return this;
        }

        public DecorationBlocks build() {
            return new DecorationBlocks(this.craftedFrom, stairBlock, slabBlock);
        }
    }
}

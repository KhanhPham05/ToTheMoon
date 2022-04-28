package com.khanhpham.tothemoon.datagen.loottable;

import com.google.common.collect.ImmutableList;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.khanhpham.tothemoon.init.ModBlocks.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModLootTables extends LootTableProvider {
    public ModLootTables(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    public String getName() {
        return super.getName() + "TTM";
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(Pair.of(ModBlockLoots::new, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {
        map.forEach((rl, loot) -> {
            LootTables.validate(validationTracker, rl, loot);
            ModUtils.info("validating {} - {}", rl, loot);
        });
    }

    public static final class ModBlockLoots extends BlockLoot {
        private static final ImmutableList<Supplier<? extends Block>> DROP_SELF_BLOCKS = ImmutableList.of(DIAMOND_ENERGY_GENERATOR, GOLD_ENERGY_GENERATOR, COPPER_MACHINE_FRAME, STEEL_MACHINE_FRAME, REDSTONE_MACHINE_FRAME, ANTI_PRESSURE_GLASS, MOON_ROCK, MOON_ROCK_STAIR, MOON_ROCK_SLAB, MOON_ROCK_BRICK, MOON_ROCK_BRICK_STAIR, MOON_ROCK_BRICK_SLAB, POLISHED_MOON_ROCK, POLISHED_MOON_ROCK_STAIR, POLISHED_MOON_ROCK_SLAB, MOON_DUST, REDSTONE_METAL_BLOCK, STEEL_BLOCK, REINFORCED_WOOD, STEEL_SHEET_BLOCK, REDSTONE_STEEL_ALLOY_BLOCK, COPPER_SHEET_BLOCK, GOLD_SHEET_BLOCK, IRON_SHEET_BLOCK, PROCESSED_WOOD, PURIFIED_QUARTZ_BLOCK, SMOOTH_PURIFIED_QUARTZ_BLOCK, MOON_ROCK_BARREL, COPPER_ENERGY_GENERATOR, IRON_ENERGY_GENERATOR, ALLOY_SMELTER, METAL_PRESS, METAL_PRESSING_PLATE);

        public ModBlockLoots() {
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return BLOCK_DEFERRED_REGISTER.getEntries().stream().map(Supplier::get).collect(Collectors.toSet());
        }

        @Override
        protected void addTables() {
            ModUtils.info("Creating loot tables");
            this.createOreDrops(ModBlocks.MOON_IRON_ORE, Items.RAW_IRON);
            this.createOreDrops(ModItems.RAW_URANIUM_ORE.get(), ModBlocks.MOON_URANIUM_ORE, DEEPSLATE_URANIUM_ORE);
            this.createOreDrops(ModBlocks.MOON_GOLD_ORE, Items.RAW_GOLD);
            add(MOON_QUARTZ_ORE.get(), this::quartzDrops);
            add(MOON_REDSTONE_ORE.get(), BlockLoot::createRedstoneOreDrops);
            DROP_SELF_BLOCKS.stream().map(Supplier::get).forEach(super::dropSelf);
        }

        private LootTable.Builder quartzDrops(Block p_176051_) {
            return createSilkTouchDispatchTable(p_176051_, applyExplosionDecay(p_176051_, LootItem.lootTableItem(ModItems.PURIFIED_QUARTZ.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
        }

        private void createOreDrops(Supplier<? extends Block> blockSupplier, Item item) {
            add(blockSupplier.get(), block -> createOreDrop(block, item));
        }

        @SafeVarargs
        private void createOreDrops(Item item, Supplier<? extends Block>... suppliers) {
            for (Supplier<? extends Block> supplier : suppliers) {
                add(supplier.get(), block -> createOreDrop(block, item));
            }
        }
    }
}

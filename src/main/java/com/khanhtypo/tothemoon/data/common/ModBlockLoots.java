package com.khanhtypo.tothemoon.data.common;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.khanhtypo.tothemoon.common.battery.BatteryBlock;
import com.khanhtypo.tothemoon.common.machine.powergenerator.PowerGeneratorBlock;
import com.khanhtypo.tothemoon.registration.ModItems;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.registration.elements.BlockObject;
import com.khanhtypo.tothemoon.registration.elements.ChildBlockObject;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Supplier;

import static com.khanhtypo.tothemoon.registration.ModBlocks.*;

@SuppressWarnings("SameParameterValue")
public class ModBlockLoots extends BlockLootSubProvider {
    public ModBlockLoots() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    @Nonnull
    protected Iterable<Block> getKnownBlocks() {
        return ImmutableSet.copyOf(ModRegistries.BLOCKS.getEntries().stream().map(Supplier::get).iterator());
    }

    @Override
    protected void generate() {
        ChildBlockObject.generateLoot(this::dropSelf, this::slab);
        this.dropSelf(COBBLED_METEORITE);
        this.dropSelf(COBBLED_MOON_ROCK);
        this.dropSelf(COPPER_MACHINE_FRAME);
        this.oreDrop(DEEPSLATE_URANIUM_ORE, ModItems.RAW_URANIUM_CHUNK);
        this.dropSelf(ERODED_METEORITE);
        this.gildedOre(GILDED_METEORITE_BRICKS);
        this.dropSelf(GOLD_SHEET_BLOCK);
        this.dropSelf(COPPER_SHEET_BLOCK);
        this.dropSelf(IRON_SHEET_BLOCK);
        this.dropSelf(METEORITE);
        this.dropSelf(METEORITE_BRICKS);
        this.dropSelf(METEORITE_LAMP);
        this.dropSelf(METEORITE_TILES);
        this.oreDrop(METEORITE_ZIRCONIUM_ORE, ModItems.RAW_ZIRCONIUM);
        this.dropSelf(MOON_DUST);
        this.oreDrop(MOON_GOLD_ORE, () -> Items.RAW_GOLD);
        this.oreDrop(MOON_IRON_ORE, () -> Items.RAW_IRON);
        this.oreDrop(MOON_QUARTZ_ORE, () -> Items.QUARTZ);
        this.add(MOON_REDSTONE_ORE.get(), super::createRedstoneOreDrops);
        this.dropSelf(MOON_ROCK);
        this.dropSelf(MOON_ROCK_BRICKS);
        this.oreDrop(MOON_URANIUM_ORE, ModItems.RAW_URANIUM_CHUNK);
        this.dropSelf(MOON_ROCK_BARREL);
        this.dropSelf(POLISHED_METEORITE);
        this.dropSelf(POLISHED_MOON_ROCK);
        this.dropSelf(PROCESSED_WOOD_PLANKS);
        this.dropSelf(PURE_ZIRCONIUM_BLOCK);
        this.dropSelf(PURIFIED_QUARTZ_BLOCK);
        this.dropSelf(RAW_URANIUM_BLOCK);
        this.dropSelf(RAW_ZIRCONIUM_BLOCK);
        this.dropSelf(REDSTONE_METAL_MACHINE_FRAME);
        this.dropSelf(REDSTONE_METAL_BLOCK);
        this.dropSelf(REDSTONE_STEEL_ALLOY_BLOCK);
        this.dropSelf(REDSTONE_STEEL_ALLOY_SHEET_BLOCK);
        this.dropSelf(REINFORCED_WOOD);
        this.dropSelf(SMOOTH_BLACKSTONE);
        this.dropSelf(SMOOTH_METEORITE);
        this.dropSelf(SMOOTH_PURIFIED_QUARTZ_BLOCK);
        this.dropSelf(STEEL_BLOCK);
        this.dropSelf(STEEL_MACHINE_FRAME);
        this.dropSelf(STEEL_SHEET_BLOCK);
        this.dropSelf(URANIUM_BLOCK);
        this.dropSelf(ZIRCONIUM_BLOCK);
        this.dropSelf(ZIRCONIUM_ALLOY_BLOCK);
        this.dropSelf(WORKBENCH);
        this.createPowerGeneratorLoot(COPPER_POWER_GENERATOR);
        this.createPowerGeneratorLoot(IRON_POWER_GENERATOR);
        this.createPowerGeneratorLoot(GOLD_POWER_GENERATOR);
        this.createPowerGeneratorLoot(DIAMOND_POWER_GENERATOR);
        this.createPowerGeneratorLoot(NETHERITE_POWER_GENERATOR);
        this.dropSelf(BLACK_STONE_FURNACE_CONTROLLER);
        this.dropSelf(BLACKSTONE_EMPTY_ACCEPTOR);
        this.dropSelf(NETHER_BRICKS_EMPTY_ACCEPTOR);
        this.dropSelf(NETHER_BRICKS_ITEM_ACCEPTOR);
        this.dropSelf(BLACKSTONE_ITEM_ACCEPTOR);
        this.createBatteryLoot(STANDARD_BATTERY);
        this.createBatteryLoot(REDSTONE_BATTERY);
        this.createBatteryLoot(STEEL_BATTERY);
        this.add(FLUID_TANK.get(), tank -> LootTable.lootTable().withPool(this.createCopyEntityDataLootPool(tank, "FluidTank")));
        this.add(ELECTRICAL_SMELTER.get(), smelter -> LootTable.lootTable().withPool(this.createCopyEntityDataLootPool(smelter, "Upgrades", "Energy")));
    }

    private void createPowerGeneratorLoot(BlockObject<PowerGeneratorBlock> generatorBlockBlockObject) {
        super.add(generatorBlockBlockObject.get(), LootTable.lootTable().withPool(this.createCopyEntityDataLootPool(generatorBlockBlockObject, "Upgrades", "Energy")));
    }

    private void createBatteryLoot(BlockObject<BatteryBlock> batteryBlock) {
        super.add(batteryBlock.get(), LootTable.lootTable().withPool(this.createCopyEntityDataLootPool(batteryBlock, "Energy")));
    }

    private LootPool.Builder createCopyEntityDataLootPool(ItemLike drop, String... fromNbt) {
        Preconditions.checkState(fromNbt.length > 0);

        var copyNbtFunction = CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY);

        for (String nbtTag : fromNbt) {
            copyNbtFunction.copy(nbtTag, "MachineData." + nbtTag);
        }

        return LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(drop).apply(copyNbtFunction));
    }

    private void dropSelf(BlockObject<?> blockObject) {
        super.dropSelf(blockObject.get());
    }

    private void slab(BlockObject<?> basicBlock) {
        super.add(basicBlock.get(), super::createSlabItemTable);
    }

    private void oreDrop(BlockObject<?> blockObject, Supplier<? extends Item> dropItem) {
        super.add(blockObject.get(), block -> super.createOreDrop(block, dropItem.get()));
    }

    private void gildedOre(BlockObject<?> blockObject) {
        Preconditions.checkState(blockObject.getId().getPath().contains("gilded"));
        super.add(blockObject.get(), p_250428_ -> createSilkTouchDispatchTable(p_250428_, this.applyExplosionCondition(p_250428_, LootItem.lootTableItem(Items.GOLD_NUGGET).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F)).otherwise(LootItem.lootTableItem(p_250428_)))));
    }
}

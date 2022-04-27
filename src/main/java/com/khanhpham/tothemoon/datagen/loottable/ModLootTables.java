package com.khanhpham.tothemoon.datagen.loottable;

import com.google.common.collect.ImmutableList;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTables extends LootTableProvider {
    public ModLootTables(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(Pair.of(SubProvider::new, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((rl, loot) -> {
            LootTables.validate(validationtracker, rl, loot);
        });
    }

    public static final class SubProvider extends BlockLoot {
        private final List<Field> DROP_SELF_BLOCKS = Arrays.stream(ModBlocks.class.getDeclaredFields()).filter(field -> field.getType().equals(RegistryObject.class)).toList();

        @Override
        public void accept(@Nonnull BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            this.createOreDrops(ModBlocks.MOON_IRON_ORE, Items.RAW_IRON);
            this.createOreDrops(ModItems.RAW_URANIUM_ORE.get(), ModBlocks.MOON_URANIUM_ORE, ModBlocks.MOON_URANIUM_ORE);
            this.createOreDrops(ModBlocks.MOON_GOLD_ORE, Items.RAW_GOLD);
            this.createOreDrops(ModBlocks.MOON_QUARTZ_ORE, Items.QUARTZ);


            try {
                this.createDropSelf(DROP_SELF_BLOCKS.toArray(new Field[0]));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            createRedstoneOreDrops(ModBlocks.MOON_REDSTONE_ORE.get());
        }

        @SuppressWarnings("unchecked")
        private <T extends RegistryObject<U>, U extends Block> void createDropSelf(Field... blocks) throws IllegalAccessException {
            for (Field field : blocks) {
                T b = (T) field.get(RegistryObject.class);
                U block = b.get();
                if (!(block instanceof OreBlock || block instanceof RedStoneOreBlock)) {
                    ModUtils.info("Drop self for {} - Name : {} ", block.getRegistryType(), block.getRegistryName().toString());
                    super.dropSelf(block);
                }
            }
        }

        private void createOreDrops(Supplier<? extends Block> blockSupplier, Item item) {
            add(blockSupplier.get(), block -> createOreDrop(block, item));
        }

        @SafeVarargs
        private void createOreDrops(Item item, Supplier<? extends Block>... suppliers) {
            for (Supplier<? extends Block> supplier : suppliers) {
                this.createOreDrops(supplier, item);
            }
        }
    }
}

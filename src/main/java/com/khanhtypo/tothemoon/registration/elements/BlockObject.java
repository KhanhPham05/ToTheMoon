package com.khanhtypo.tothemoon.registration.elements;

import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.registration.bases.IngredientProvider;
import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import com.khanhtypo.tothemoon.utls.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BlockObject<T extends Block> implements ObjectSupplier<T>, IngredientProvider {
    public static final Set<BlockObject<? extends Block>> BLOCK_SET = ModUtils.resourceSortedSet();
    private static final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
    private final RegistryObject<T> object;
    @Nullable
    private MutableComponent translateName = null;
    private final Item.Properties blockItemProperties;
    @Nullable
    private BiFunction<Block, Item.Properties, BlockItem> blockItemSupplier;

    public BlockObject(String name, Supplier<T> blockSupplier) {
        this(ModRegistries.BLOCKS.register(name, blockSupplier));
    }

    public BlockObject(RegistryObject<T> object) {
        this.object = object;
        BLOCK_SET.add(this);
        ToTheMoon.DEFAULT_BLOCK_TAB.addItem(this);
        this.blockItemProperties = new Item.Properties();
    }

    public static Stream<BlockObject<? extends Block>> allBlocks() {
        return BLOCK_SET.stream();
    }

    public Supplier<BlockState> defaultBlockState() {
        return () -> this.get().defaultBlockState();
    }

    public boolean isBasic() {
        return this instanceof BasicBlockObject;
    }

    public @Nonnull ResourceLocation getId() {
        return this.object.getId();
    }

    @Override
    public T get() {
        return this.object.get();
    }

    @Nonnull
    @Override
    public Item asItem() {
        return this.get().asItem();
    }

    public ObjectSupplier<Item> getItemObject() {
        return ObjectSupplier.preExisted(this.get().asItem(), this.getId());
    }

    public MutableComponent getTranslationName() {
        return translateName != null ? translateName : (translateName = this.get().getName());
    }

    public String getTranslationKey() {
        return ((TranslatableContents) this.getTranslationName().getContents()).getKey();
    }

    private BlockState getStateAt(Level level, BlockPos pos) {
        return level.getBlockState(pos);
    }

    public boolean isSame(BlockState state) {
        return state.is(this.get());
    }

    public boolean isSame(Level level, BlockPos pos) {
        return this.isSame(this.getStateAt(level, pos));
    }

    public boolean isSame(Level level, int x, int y, int z) {
        return this.isSame(level, mutableBlockPos.set(x, y, z));
    }

    @Nullable
    public BiFunction<Block, Item.Properties, BlockItem> getBlockItemSupplier() {
        return blockItemSupplier;
    }

    public BlockObject<T> setBlockItemSupplier(@Nullable BiFunction<Block, Item.Properties, BlockItem> blockItemSupplier) {
        this.blockItemSupplier = blockItemSupplier;
        return this;
    }

    public BlockObject<T> setMaxStackSize(int maxStackSize) {
        this.blockItemProperties.stacksTo(maxStackSize);
        return this;
    }

    public Item.Properties getBlockItemProperties() {
        return blockItemProperties;
    }
}

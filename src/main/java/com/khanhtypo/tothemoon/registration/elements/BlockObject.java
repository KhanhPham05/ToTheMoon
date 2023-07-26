package com.khanhtypo.tothemoon.registration.elements;

import com.khanhtypo.tothemoon.registration.ModRegistries;
import com.khanhtypo.tothemoon.registration.bases.IngredientProvider;
import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BlockObject<T extends Block> implements ObjectSupplier<T>, IngredientProvider {
    public static final Set<BlockObject<? extends Block>> BLOCK_SET = new HashSet<>();
    private final RegistryObject<T> object;

    public BlockObject(String name, Supplier<T> blockSupplier) {
        this(ModRegistries.BLOCKS.register(name, blockSupplier));
    }

    public BlockObject(RegistryObject<T> object) {
        this.object = object;
        BLOCK_SET.add(this);
    }

    public static Stream<BlockObject<? extends Block>> allBlocks() {
        return BLOCK_SET.stream();
    }

    public Supplier<BlockState> defaultBlockState() {
        return () -> this.get().defaultBlockState();
    }

    public boolean isBasic() {
        return this instanceof BasicBlock;
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
        return ObjectSupplier.existedItem(this.get().asItem(), this.getId());
    }

    public Component getTranslationName() {
        return this.get().getName();
    }

}

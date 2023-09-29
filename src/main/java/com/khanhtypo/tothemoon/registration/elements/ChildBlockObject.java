package com.khanhtypo.tothemoon.registration.elements;

import com.khanhtypo.tothemoon.utls.ModUtils;
import com.khanhtypo.tothemoon.registration.bases.IngredientProvider;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@MethodsReturnNonnullByDefault
public class ChildBlockObject extends BasicBlockObject {
    private static final Map<ChildBlockObject, BlockObject<?>> PARENT_MAP = ModUtils.resourceSortedMap(ChildBlockObject::getId);
    private final BlockObject<? extends Block> parent;

    public ChildBlockObject(String name, BlockObject<?> parent, Function<BlockBehaviour.Properties, ? extends Block> factory) {
        super(name, parent, factory);
        this.parent = parent;
        PARENT_MAP.put(this, parent);
    }

    @SafeVarargs
    public static void generateRecipe(Consumer<IngredientProvider>... generators) {
        PARENT_MAP.keySet().forEach(childBlock -> generators[childBlock.getType()].accept(childBlock));
    }

    public static void generateLoot(Consumer<BlockObject<?>> actor, Consumer<BlockObject<?>> slab) {
        PARENT_MAP.forEach((block, parent) -> {
            if (block.getType() == 0) slab.accept(block);
            else actor.accept(block);
        });
    }

    @SafeVarargs
    public static void generateModel(Consumer<BasicBlockObject>... generators) {
        PARENT_MAP.keySet().forEach(b -> generators[b.getType()].accept(b));
    }

    //slab 0, wall 1, stairs 2
    private int getType() {
        String string = super.getId().getPath();
        return string.contains("wall") ? 1 : string.contains("stairs") ? 2 : 0;
    }

    @Override
    public Ingredient toIngredient(int a) {
        return this.parent.toIngredient(a);
    }

    @Override
    public Ingredient toIngredient() {
        return this.parent.toIngredient();
    }

    @Override
    public InventoryChangeTrigger.TriggerInstance trigger() {
        return this.parent.trigger();
    }

    @Override
    public BlockObject<? extends Block> getParent() {
        return this.parent;
    }

    @Override
    public BlockObject<?> getParentObject() {
        return this.getParent();
    }
}

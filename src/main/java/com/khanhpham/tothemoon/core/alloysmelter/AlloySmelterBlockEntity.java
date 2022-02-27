package com.khanhpham.tothemoon.core.alloysmelter;

import com.khanhpham.tothemoon.ModUtils;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.init.ModRecipes;
import com.khanhpham.tothemoon.utils.energy.Energy;
import com.khanhpham.tothemoon.utils.recipes.AlloySmeltingRecipe;
import com.khanhpham.tothemoon.utils.te.EnergyItemCapableBlockEntity;
import com.khanhpham.tothemoon.utils.te.energygenerator.AbstractEnergyGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * @see AbstractEnergyGeneratorBlockEntity
 */
public class AlloySmelterBlockEntity extends EnergyItemCapableBlockEntity {
    public static final int MENU_SIZE = 3;
    public static final TranslatableComponent LABEL = ModUtils.translate("gui.tothemoon.alloy_smelter");
    public static final int DATA_CAPACITY = 4;
    int isActive = 0;
    private int workingTime;
    private int workingDuration;
    private final int[] datas = new int[]{workingTime, workingDuration, energy.getEnergyStored(), energy.getMaxEnergyStored(), isActive};
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return datas[pIndex];
        }

        @Override
        public void set(int pIndex, int pValue) {
        }

        @Override
        public int getCount() {
            return datas.length;
        }
    };

    public AlloySmelterBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState, Energy energy, @NotNull Component label, int containerSize) {
        super(pType, pWorldPosition, pBlockState, energy, label, containerSize);
    }

    public AlloySmelterBlockEntity(BlockPos blockPos, BlockState state) {
        this(ModBlockEntityTypes.ALLOY_SMELTER, blockPos, state, new Energy(175000, 500, 1) {
            @Override
            public boolean canExtract() {
                return false;
            }
        }, LABEL, MENU_SIZE);
    }

    /**
     * @see net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
     */

    public static void serverTick(Level level, BlockPos pos, BlockState state, AlloySmelterBlockEntity blockEntity) {

        blockEntity.receiveEnergyFromOther(level, pos);

        if (!blockEntity.energy.isEmpty()) {
            ItemStack resultSlot = blockEntity.items.get(2);
            AlloySmeltingRecipe recipe = level.getRecipeManager().getRecipeFor(ModRecipes.ALLOY_SMELTING, blockEntity, level).orElse(null);
            if (!resultSlot.isEmpty() && recipe != null) {
                if (blockEntity.canProcess()) {
                    if (recipe.matches(blockEntity, level)) {
                        blockEntity.items.get(0).shrink(recipe.getFirstIngredient().getAmount());
                        blockEntity.items.get(1).shrink(recipe.getSecondIngredient().getAmount());
                        blockEntity.setTime();
                        blockEntity.isActive = 1;
                    } else {
                        blockEntity.isActive = 0;
                    }
                }
            }
        }

        markDirty(level, pos, state);
    }

    private boolean canProcess() {
        return workingTime <= 0;
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return new AlloySmelterMenu(containerId, playerInventory, this, this.data);
    }

    private void processRecipes() {
        ItemStack input1 = items.get(0);
        ItemStack input2 = items.get(1);
        ItemStack output = items.get(2);

        if (!input1.isEmpty() && !input2.isEmpty()) {
            SetRecipes[] recipes = SetRecipes.values();
            for (SetRecipes recipe : recipes) {
                if (recipe.check(input1, input2) && (output.getCount() <= output.getMaxStackSize() - recipe.resultAmount && (output.is(recipe.result) || output.isEmpty()))) {
                    setTime();
                    input1.shrink(recipe.amount1);
                    input2.shrink(recipe.amount2);
                }
            }
        }

        if (this.workingTime <= 0) {

        }
    }

    private void setTime() {
        this.workingTime = 200;
        this.workingDuration = this.workingTime;
    }

    private enum SetRecipes {
        REDSTONE_STEEL_ALLOY(ModItems.STEEL_INGOT, Items.REDSTONE, ModItems.REDSTONE_STEEL_ALLOY, 1, 3, 1),
        REDSTONE_INGOT(Items.IRON_INGOT, Items.REDSTONE, ModItems.REDSTONE_INGOT, 1, 3, 1);

        final Item req1;
        final Item req2;
        final Item result;
        final int amount1;
        final int amount2;
        final int resultAmount;

        SetRecipes(Item req1, Item req2, Item result, int amount1, int amound2, int resultAmount) {
            this.req1 = req1;
            this.req2 = req2;
            this.result = result;
            this.amount1 = amount1;
            this.amount2 = amound2;
            this.resultAmount = resultAmount;
        }

        private boolean check(ItemStack stack1, ItemStack stack2) {
            return (!stack1.isEmpty() && !stack2.isEmpty()) && (check(stack1, req1, amount1) && check(stack2, req2, amount2));
        }

        private boolean check(ItemStack stack1, Item req, int amount) {
            return (stack1.is(req)) && stack1.getCount() >= amount;
        }
    }
}

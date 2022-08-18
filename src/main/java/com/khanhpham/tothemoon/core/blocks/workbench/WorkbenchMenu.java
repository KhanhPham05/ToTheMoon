package com.khanhpham.tothemoon.core.blocks.workbench;

import com.khanhpham.tothemoon.core.menus.containers.WorkbenchCraftingContainer;
import com.khanhpham.tothemoon.core.recipes.WorkbenchCraftingRecipe;
import com.khanhpham.tothemoon.datagen.tags.ModItemTags;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModMenuTypes;
import com.khanhpham.tothemoon.utils.slot.SlotPlacePredicate;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class WorkbenchMenu extends AbstractContainerMenu {
    private static final int RESULT_INDEX = 0;
    public final Player player;
    public final WorkbenchCraftingContainer workbenchContainer;
    private final ContainerLevelAccess access;
    public final ResultContainer resultContainer = new ResultContainer() {
        @Override
        public void setChanged() {
            WorkbenchMenu.craftingSlotsChanged(WorkbenchMenu.this);
            super.setChanged();
        }
    };
    private final Block block;
    public WorkbenchMenu(int pContainerId, Inventory playerInventory) {
        this(pContainerId, playerInventory, ModBlocks.WORKBENCH.get(), ContainerLevelAccess.NULL);
    }

    public WorkbenchMenu(int pContainerId, Inventory pPlayerInventory, Block block, ContainerLevelAccess access) {
        super(ModMenuTypes.WORKBENCH_CRAFTING, pContainerId);
        this.workbenchContainer = new WorkbenchCraftingContainer(this);
        this.access = access;
        this.block = block;
        this.player = pPlayerInventory.player;

        //result
        super.addSlot(new WorkbenchResultSlot(this, this.resultContainer, 0, 156, 54));

        //hammer
        this.addSlot(new SlotPlacePredicate(this.workbenchContainer, 0, 22, 36, stack -> stack.is(ModItemTags.GENERAL_TTM_HAMMERS)));
        //extra
        this.addSlot(new Slot(this.workbenchContainer, 1, 22, 73));


        this.workbenchContainer.addCraftingGrid(this);

        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(pPlayerInventory, i1 + k * 9 + 9, 15 + i1 * 18, 127 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(pPlayerInventory, l, 15 + l * 18, 185));
        }
    }

    public static void craftingSlotsChanged(WorkbenchMenu menu) {
        menu.slotChangedCraftingGrid(menu, menu.player.level, menu.player, menu.resultContainer);
    }

    @Override
    public void slotsChanged(Container pContainer) {
        this.access.execute(((level, pos) -> WorkbenchMenu.craftingSlotsChanged(this)));
    }

    @Override
    public Slot addSlot(Slot pSlot) {
        return super.addSlot(pSlot);
    }

    public void slotChangedCraftingGrid(AbstractContainerMenu menu, Level level, Player player, ResultContainer resultContainer) {
        if (!level.isClientSide) {
            ServerPlayer serverplayer = (ServerPlayer) player;
            ServerLevel serverLevel = (ServerLevel) level;
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<WorkbenchCraftingRecipe> optional = serverLevel.getRecipeManager().getRecipeFor(WorkbenchCraftingRecipe.RECIPE_TYPE, this.workbenchContainer, level);
            if (optional.isPresent()) {
                WorkbenchCraftingRecipe workbenchCraftingRecipe = optional.get();
                if (resultContainer.setRecipeUsed(level, serverplayer, workbenchCraftingRecipe)) {
                    itemstack = workbenchCraftingRecipe.assemble(this.workbenchContainer);
                }
            }
            resultContainer.setItem(0, itemstack);
            menu.setRemoteSlot(0, itemstack);
            serverplayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemstack));

        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            final int inventoryStarts = 28;

            if (pIndex == RESULT_INDEX) {
                itemstack.getItem().onCraftedBy(itemstack1, pPlayer.level, pPlayer);
                if (!this.moveItemStackTo(itemstack1, 28, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex < inventoryStarts) {
                if (!this.moveItemStackTo(itemstack1, inventoryStarts, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!super.moveItemStackTo(itemstack1, 1, 28, false)) {
                return ItemStack.EMPTY;
            }


            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
            if (pIndex == 0) {
                pPlayer.drop(itemstack1, false);
            }
            this.broadcastChanges();
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(access, pPlayer, this.block);
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return pSlot.container != this.resultContainer && super.canTakeItemForPickAll(pStack, pSlot);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((level, pos) -> clearContainer(pPlayer, this.workbenchContainer));
    }

    public int getSlotSize() {
        return super.slots.size();
    }


}
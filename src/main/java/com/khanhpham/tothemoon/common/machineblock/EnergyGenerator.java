package com.khanhpham.tothemoon.common.machineblock;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.api.block.TTMEnergyContainerTileEntity;
import com.khanhpham.tothemoon.api.block.TTMEnergyStorage;
import com.khanhpham.tothemoon.api.block.TileEntityBlock;
import com.khanhpham.tothemoon.api.slots.EnergyFuelSlot;
import com.khanhpham.tothemoon.api.utls.BaseContainer;
import com.khanhpham.tothemoon.api.utls.ScreenTexture;
import com.khanhpham.tothemoon.api.utls.TTMContainerScreen;
import com.khanhpham.tothemoon.datagen.EnglishProvider;
import com.khanhpham.tothemoon.registries.ModBlocks;
import com.khanhpham.tothemoon.registries.ModSpecials;
import com.khanhpham.tothemoon.util.Identity;
import com.khanhpham.tothemoon.util.ModUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class EnergyGenerator {
    public static final class BlockInstance extends TileEntityBlock {
        public BlockInstance() {
            super(AbstractBlock.Properties.of(Material.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(1).strength(3.5f).requiresCorrectToolForDrops());
        }

        @Override
        public boolean hasTileEntity(BlockState state) {
            return true;
        }


        @Override
        protected TileEntity getTE() {
            return new Tile();
        }

        @Nonnull
        @Override
        protected ActionResultType action(World world, BlockPos pos, PlayerEntity playerEntity) {
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof Tile && !world.isClientSide) {
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, (INamedContainerProvider) te, pos);
                return ActionResultType.SUCCESS;
            }

            return ActionResultType.SUCCESS;
        }
    }


    public static final class Tile extends TTMEnergyContainerTileEntity implements ITickableTileEntity {
        private static final int containerSize = 1;
        private static final int dataCount = 3;
        private final TTMEnergyStorage energyStorage = new TTMEnergyStorage(50000, 2000) {
            @Override
            public void setChanged() {
                Tile.this.setChanged();
            }
        };

        private final LazyOptional<IEnergyStorage> energyOptional = LazyOptional.of(() -> energyStorage);
        private final NonNullList<ItemStack> storedItems = NonNullList.withSize(containerSize, ItemStack.EMPTY);
        private int burningTick;
        private final IIntArray clientData = new IIntArray() {
            @Override
            public int get(int i) {
                switch (i) {
                    case 0:
                        return energyStorage.getEnergyStored();
                    case 1:
                        return energyStorage.getMaxEnergyStored();
                    case 2:
                        return burningTick;
                }

                throw new ArrayIndexOutOfBoundsException();
            }

            @Override
            public void set(int i, int i1) {
                throw new IllegalArgumentException("You can not change anything here");
            }

            @Override
            public int getCount() {
                return dataCount;
            }
        };

        public Tile() {
            super(ModSpecials.energyGeneratorTileEntity);
        }

        /**
         * @see Items
         */
        @Override
        public void tick() {
            if (level != null) {
                if (level.isClientSide()) {
                    ItemStack inputStack = storedItems.get(0);
                    if (burningTick <= 0) {
                        if (ModUtils.getBurnTime(inputStack) > 0) {
                            if (!inputStack.isEmpty()) {
                                inputStack.shrink(1);
                                burningTick = ModUtils.getBurnTime(inputStack);
                            }
                        }
                    } else {
                        burningTick--;
                        energyStorage.addEnergy(200);
                    }
                }
            }

            setChanged();
            energyStorage.setChanged();
        }

        /*private void addEnergy() {
            energyStorage. = storedEnergy = energyStorage.receiveEnergy(200, false);

        }*/

        @Override
        protected void loadExtra(CompoundNBT nbt) {
            burningTick = nbt.getInt("burningTick");
        }

        @Override
        protected void saveExtra(CompoundNBT nbt) {
            nbt.putInt("burningTick", burningTick);
        }

        @Override
        public int getContainerSize() {
            return containerSize;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ItemStack getItem(int i) {
            return storedItems.get(i);
        }

        @Override
        public ItemStack removeItem(int i, int i1) {
            return ItemStackHelper.removeItem(storedItems, i, i1);
        }

        @Override
        public ItemStack removeItemNoUpdate(int i) {
            return ItemStackHelper.takeItem(storedItems, i);
        }

        @Override
        public void setItem(int i, ItemStack itemStack) {
            storedItems.set(i, itemStack);
        }

        @Override
        public boolean stillValid(PlayerEntity playerEntity) {

            assert this.level != null;
            if (this.level.getBlockEntity(this.worldPosition) != this) {
                return false;
            } else {
                return !(playerEntity.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) > 64.0D);
            }
        }

        @Override
        public void clearContent() {
            storedItems.clear();
        }

        @Override
        public ITextComponent getDisplayName() {
            return EnglishProvider.ENERGY_GENERATOR_TITLE;
        }

        @Override
        public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
            return new Storage(this, playerInventory, i, clientData);
        }

        @Override
        public Block getBlock() {
            return ModBlocks.ENERGY_GENERATOR.get();
        }

        @Override
        protected LazyOptional<IEnergyStorage> energyOptional() {
            return energyOptional;
        }

        @Nonnull
        @Override
        public TTMEnergyStorage getEnergyData() {
            return energyStorage;
        }


        @Override
        protected NonNullList<ItemStack> getInventorySlots() {
            return storedItems;
        }
    }

    public static final class Storage extends BaseContainer {
        private Storage(IInventory tileInventory, PlayerInventory playerInventory, int windowId, IIntArray dataSlots) {
            super(ModSpecials.energyGeneratorContainer, windowId, dataSlots);

            addSlot(new EnergyFuelSlot(tileInventory, 0, 107, 31));

            addPlayerInventorySlots(playerInventory, 36, 122);

            addDataSlots(dataSlots);
        }

        public Storage(int var1, PlayerInventory var2) {
            this(new Inventory(1), var2, var1, new IntArray(Tile.dataCount));
        }

        @Override
        protected Block getBlockForContainer() {
            return ModBlocks.ENERGY_GENERATOR.get();
        }
    }


    public static final class StorageScreen extends TTMContainerScreen<Storage> {
        public static final ResourceLocation GUI_ID = Identity.of(ToTheMoon.ID, "textures/gui/energy_generator.png");


        public StorageScreen(Storage container, PlayerInventory playerInv, ITextComponent text) {
            super(container, playerInv, text);
        }

        @Nonnull
        @Override
        protected ScreenTexture getScreenTexture() {
            return new ScreenTexture(GUI_ID, 230, 199);
        }

        @Override
        protected void drawLabels(MatrixStack matrixStack) {
            drawPlayerInventoryLabel(matrixStack, 36, 113, colorTextBlack);
            drawText(matrixStack, EnglishProvider.ENERGY_GENERATOR_TITLE, 15, 10, colorTextWhite);
        }
    }
}

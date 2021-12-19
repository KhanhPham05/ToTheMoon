package com.khanhpham.tothemoon.common.energybank;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.api.block.TTMEnergyStorage;
import com.khanhpham.tothemoon.api.block.TTMEnergyStorageTileEntity;
import com.khanhpham.tothemoon.api.block.TileEntityBlock;
import com.khanhpham.tothemoon.api.utls.BaseContainer;
import com.khanhpham.tothemoon.api.utls.ScreenTexture;
import com.khanhpham.tothemoon.api.utls.TTMContainerScreen;
import com.khanhpham.tothemoon.datagen.EnglishProvider;
import com.khanhpham.tothemoon.datagen.ModTags;
import com.khanhpham.tothemoon.registries.ModBlocks;
import com.khanhpham.tothemoon.registries.ModSpecials;
import com.khanhpham.tothemoon.util.Identity;
import com.khanhpham.tothemoon.util.TranslationUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

/**
 * @see net.minecraft.block.AbstractFurnaceBlock
 */
public class EnergyBank {
    public static final class BlockInstance extends TileEntityBlock {
        public static final DirectionProperty DIRECTIONS = HorizontalBlock.FACING;

        public BlockInstance() {
            super(AbstractBlock.Properties.of(Material.METAL).strength(5, 6).harvestTool(ToolType.PICKAXE).harvestLevel(1).requiresCorrectToolForDrops());
        }

        @Override
        public BlockState getStateForPlacement(BlockItemUseContext use) {
            return defaultBlockState().setValue(DIRECTIONS, use.getHorizontalDirection().getOpposite());
        }

        @Override
        protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
            builder.add(DIRECTIONS);
        }

        @Override
        protected TileEntity getTE() {
            return new Tile();
        }

        @Nonnull
        @Override
        protected ActionResultType action(World world, BlockPos pos, PlayerEntity playerEntity) {
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof EnergyBank.Tile && !world.isClientSide) {
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, (INamedContainerProvider) te, pos);
                return ActionResultType.SUCCESS;
            }

            return ActionResultType.SUCCESS;
        }
    }

    public static final class Tile extends TTMEnergyStorageTileEntity implements ITickableTileEntity {
        private final TTMEnergyStorage internalEnergy = new TTMEnergyStorage(500000, 10000) {
            @Override
            public void setChanged() {
                Tile.this.setChanged();
            }
        };

        private final IIntArray clientData = new IIntArray() {
            private final int[] values = new int[]{getEnergyData().getEnergyStored(), getEnergyData().getMaxEnergyStored()};

            @Override
            public int get(int i) {
                return values[i];
            }

            @Override
            public void set(int i, int i1) {
                throw new IllegalStateException("You can not change anything here");
            }

            @Override
            public int getCount() {
                return values.length;
            }
        };

        private final int internalMaxReceive = internalEnergy.getMaxReceive();
        private final int maxExtract = internalEnergy.getMaxExtract();

        public Tile() {
            super(ModSpecials.energyBankTileEntity);
        }

        public void tick() {
            if (level != null) {
                for (Direction direction : Direction.values()) {
                    TileEntity energyBlock = level.getBlockEntity(getBlockPos().relative(direction));
                    if (energyBlock instanceof TTMEnergyStorageTileEntity && !(energyBlock.equals(this))) {
                        if (level.getBlockState(getBlockPos().relative(direction)).getBlock().is(ModTags.energyReceive)) {
                            transferTo((TTMEnergyStorageTileEntity) energyBlock, maxExtract);
                        } else if (level.getBlockState(getBlockPos().relative(direction)).getBlock().is(ModTags.energyTransferable)) {
                            receiveFrom((TTMEnergyStorageTileEntity) energyBlock, internalMaxReceive);
                        }
                    }
                }
            }
            setChanged();
            internalEnergy.setChanged();
        }

        private void transferTo(TTMEnergyStorageTileEntity externalEnergy, int maxAmount) {
            if (getEnergyData().getEnergyStored() > 0) {
                int externalMaxReceive = externalEnergy.getEnergyData().getMaxReceive();
                if (maxAmount > externalMaxReceive) {
                    ToTheMoon.LOG.debug("Transfer " + externalMaxReceive + " FE to " + externalEnergy.getBlock());
                    externalEnergy.getEnergyData().addEnergy(externalMaxReceive);
                    getEnergyData().removeEnergy(externalMaxReceive);
                } else {
                    ToTheMoon.LOG.debug("Transfer " + maxAmount + " FE to " + externalEnergy.getBlock());
                    externalEnergy.getEnergyData().addEnergy(maxAmount);
                    getEnergyData().removeEnergy(maxAmount);
                }
            }
        }

        private void receiveFrom(TTMEnergyStorageTileEntity externalEnergy, int maxAmount) {
            if (externalEnergy.getEnergyData().getEnergyStored() > 0)
                if (getEnergyData().getEnergyStored() >= getEnergyData().getMaxEnergyStored()) {
                    int externalMaxExtract = externalEnergy.getEnergyData().getMaxExtract();
                    if (externalEnergy.getEnergyData().canExtract()) {

                        if (maxAmount > externalMaxExtract) {
                            ToTheMoon.LOG.debug("Receive " + externalMaxExtract + "FE from " + externalEnergy.getBlock());
                            externalEnergy.getEnergyData().removeEnergy(externalMaxExtract);
                            getEnergyData().addEnergy(externalMaxExtract);
                        } else {
                            ToTheMoon.LOG.debug("Receive " + maxAmount + "FE from " + externalEnergy.getBlock());
                            externalEnergy.getEnergyData().removeEnergy(maxAmount);
                            getEnergyData().addEnergy(maxAmount);
                        }
                    }
                }
        }

        @Override
        public Block getBlock() {
            return ModBlocks.ENERGY_BANK.get();
        }

        @Override
        protected LazyOptional<IEnergyStorage> energyOptional() {
            return LazyOptional.of(() -> internalEnergy);
        }

        @Nonnull
        @Override
        public TTMEnergyStorage getEnergyData() {
            return internalEnergy;
        }

        @Override
        public ITextComponent getDisplayName() {
            return EnglishProvider.ENERGY_BANK_TITLE;
        }

        @Override
        public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
            return new Storage(i, playerInventory, clientData);
        }
    }

    /**
     * @see net.minecraft.inventory.container.AbstractFurnaceContainer
     */
    public static final class Storage extends BaseContainer {

        private Storage(int p_i50105_2_, PlayerInventory playerInventory, IIntArray dataSlots) {
            super(ModSpecials.energyBankContainer, p_i50105_2_, dataSlots);

            addPlayerInventorySlots(playerInventory, 20, 122);

            addDataSlots(dataSlots);
        }

        public Storage(int windowId, PlayerInventory playerInventory) {
            this(windowId, playerInventory, new IntArray(2));
        }

        private int getEnergyProgressBar() {
            int i = this.dataSlots.get(0);
            int j = this.dataSlots.get(1);
            return j != 0 && i != 0 ? i * 178 / j : 0;
        }

        @Override
        protected Block getBlockForContainer() {
            return ModBlocks.ENERGY_BANK.get();
        }
    }


    public static final class StorageScreen extends TTMContainerScreen<Storage> {
        private static final ResourceLocation GUI = Identity.mod("textures/gui/energy_bank.png");
        public final IFormattableTextComponent component;

        public StorageScreen(Storage p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
            super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
            this.component = TranslationUtils.energy(menu.getCurrentEnergy(0), menu.getMaxEnergy(1));
        }

        @Nonnull
        @Override
        protected ScreenTexture getScreenTexture() {
            return new ScreenTexture(GUI, 200, 204);
        }

        @Override
        protected void extraRenderBg(MatrixStack matrixStack) {
            blit(matrixStack, 12, 94, 12, 233, menu.getEnergyProgressBar(), 16);
        }

        @Override
        protected void drawLabels(MatrixStack matrixStack) {
            drawPlayerInventoryLabel(matrixStack, 20, 112, colorTextWhite);
            drawText(matrixStack, title, 12, 9, colorTextBlack);
            drawText(matrixStack, component, 13, 86, colorTextWhite);
        }
    }
}

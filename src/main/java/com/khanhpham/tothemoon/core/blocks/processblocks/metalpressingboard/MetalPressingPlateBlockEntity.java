package com.khanhpham.tothemoon.core.blocks.processblocks.metalpressingboard;

import com.khanhpham.tothemoon.ToTheMoon;
import com.khanhpham.tothemoon.core.recipes.metalpressing.IMetalPressBlockEntity;
import com.khanhpham.tothemoon.core.recipes.metalpressing.ManualMetalPressRecipe;
import com.khanhpham.tothemoon.init.ModBlockEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MetalPressingPlateBlockEntity extends BlockEntity implements Container, IMetalPressBlockEntity {
    public final NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);

    private int metalAt;

    public MetalPressingPlateBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntityTypes.METAL_PRESSING_PLATE.get(), pWorldPosition, pBlockState);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return this.items.get(pIndex);
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return ContainerHelper.removeItem(this.items, pIndex, pCount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return ContainerHelper.takeItem(this.items, pIndex);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        this.items.set(pIndex, pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return !(pPlayer instanceof FakePlayer);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        ContainerHelper.saveAllItems(pTag, this.items, true);
    }

    @Override
    public void load(CompoundTag pTag) {
        ContainerHelper.loadAllItems(pTag, this.items);
    }

    public boolean attemptPutItem(ItemStack item) {
        if (this.items.get(0).isEmpty()) {
            this.items.set(0, new ItemStack(item.getItem(), 1));
            setChanged();
            return true;
        } else if (items.get(1).isEmpty()) {
            this.items.set(1, new ItemStack(item.getItem(), 1));
            setChanged();
            return true;
        }

        setChanged();
        this.pressLeft = 5;
        return false;
    }
    public int pressLeft;
    public void performPress(Level pLevel, BlockPos pPos, Player pPlayer) {
        checkMetal();

        ManualMetalPressRecipe recipe = this.getRecipe(pLevel);
        if (recipe != null && recipe.matches(this, pLevel)) {
            if (pressLeft <= 0) {
                items.get(metalAt).shrink(1);
                Block.popResourceFromFace(pLevel, pPos, Direction.UP, recipe.result);
            } else {
                pPlayer.causeFoodExhaustion(1.5f);
                pressLeft--;
            }
        } else {
            pressLeft = 5;
        }

        setChanged();
    }

    @Nullable
    private ManualMetalPressRecipe getRecipe(Level level) {
        return level.getRecipeManager().getRecipeFor(ManualMetalPressRecipe.RECIPE_TYPE, this, level).orElse(null);
    }

    private void checkMetal() {
        if (this.getItem(0).is(Tags.Items.INGOTS)) {
            this.metalAt = 0;
        } else if (this.getItem(1).is(Tags.Items.INGOTS)) {
            this.metalAt = 1;
        }
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    private void takeoutItem(ItemStack item) {
        if (items.get(0).equals(item)) {
            items.set(0, items.get(1));
            return;
        }

        if (items.get(1).equals(item)) {
            items.set(1, ItemStack.EMPTY);
        }
    }

    public ItemStack takeItem() {
        ItemStack stack;
        if (!items.get(1).isEmpty()) {
            stack = items.get(1);
            this.items.set(1, ItemStack.EMPTY);
            setChanged();
            return stack;
        }

        if (!items.get(0).isEmpty()) {
            stack = items.get(0);
            this.items.set(0, ItemStack.EMPTY);
            setChanged();
            return stack;
        }

        this.pressLeft = 5;
        setChanged();
        return ItemStack.EMPTY;

    }

    public void checkSlots() {
        ToTheMoon.LOG.debug("slot 0 [{}] - slot 1 [{}]", getItem(0), getItem(1));
    }

    public static final class Renderer implements BlockEntityRenderer<MetalPressingPlateBlockEntity> {

        public Renderer(BlockEntityRendererProvider.Context ignored) {
        }

        @Override
        public void render(MetalPressingPlateBlockEntity blockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
            poseStack.pushPose();
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            poseStack.translate(.3f, .50f, .1f);
            poseStack.scale(.5f, .5f, .5f);
            poseStack.mulPose(Vector3f.XN.rotationDegrees(90.0f));
            itemRenderer.renderStatic(blockEntity.items.get(0), ItemTransforms.TransformType.FIXED, pPackedLight, OverlayTexture.NO_OVERLAY, poseStack, pBufferSource, pPackedOverlay);
            poseStack.translate(.5f, 0.0f, .5f);
            itemRenderer.renderStatic(blockEntity.items.get(1), ItemTransforms.TransformType.FIXED, pPackedLight, OverlayTexture.NO_OVERLAY, poseStack, pBufferSource, pPackedOverlay);
            poseStack.popPose();
        }
    }
}

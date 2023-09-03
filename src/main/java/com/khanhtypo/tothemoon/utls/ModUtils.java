package com.khanhtypo.tothemoon.utls;

import com.google.common.base.Preconditions;
import com.khanhtypo.tothemoon.ToTheMoon;
import com.khanhtypo.tothemoon.registration.bases.ObjectSupplier;
import com.khanhtypo.tothemoon.serverdata.recipes.BaseRecipe;
import com.khanhtypo.tothemoon.serverdata.recipes.RecipeTypeObject;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.joml.Matrix4f;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

@SuppressWarnings({"deprecation","unused"})
public class ModUtils {
    private ModUtils() {
    }

    public static <T> DeferredRegister<T> createRegistry(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, ToTheMoon.MODID);
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(ToTheMoon.MODID, path);
    }

    public static <A, B extends Comparable<B>> TreeSet<A> sortedSet(Function<A, B> compareGetter) {
        return new TreeSet<>(Comparator.comparing(compareGetter));
    }

    public static <A extends ObjectSupplier<?>> TreeSet<A> resourceSortedSet() {
        return sortedSet(ObjectSupplier::getId);
    }

    public static <A, B> TreeMap<A, B> resourceSortedMap(Function<A, ResourceLocation> compareGetter) {
        return new TreeMap<>(Comparator.comparing(compareGetter));
    }

    public static <A extends ObjectSupplier<?>, B> TreeMap<A, B> resourceSortedMap() {
        return resourceSortedMap(ObjectSupplier::getId);
    }

    public static <A> String toArrayString(Collection<A> collection) {
        return toArrayString(collection, A::toString);
    }

    public static <A, B> String toArrayString(Collection<A> collection, @Nullable Function<A, B> transformer) {
        if (collection.isEmpty()) return "";
        final StringBuilder builder = new StringBuilder().append('[');
        Object[] array = transformer == null ? collection.toArray() : collection.stream().map(transformer).toArray();
        builder.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            builder.append(", ").append(array[i].toString());
        }
        return builder.append(']').toString();
    }

    public static boolean canBurn(ItemStack itemStack) {
        return ForgeHooks.getBurnTime(itemStack, null) > 0;
    }

    /**
     * @param guiGraphics   the renderer
     * @param minecraft     minecraft client
     * @param fluidAmount   amount of stack
     * @param tankCapacity  capacity of stack
     * @param x             leftPos + bottom right corner
     * @param y             topPos + bottom right corner
     * @param width         width of the box to render
     * @param height        height of the box to render
     */
    public static void renderFluidToScreen(GuiGraphics guiGraphics, Minecraft minecraft, int fluidId, int fluidAmount, int tankCapacity, int x, int y, int width, int height) {
        if (fluidId > 0 && fluidAmount > 0) {
            IClientFluidTypeExtensions textureGetter = IClientFluidTypeExtensions.of(BuiltInRegistries.FLUID.byIdOrThrow(fluidId));
            TextureAtlasSprite fluidSprite = getFluidStillSprite(minecraft, textureGetter);
            if (fluidSprite != null) {
                y += height;
                float renderAmount = Math.max(Math.min(height, fluidAmount * height / tankCapacity), 1);
                float posY = y - height - renderAmount;

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, fluidSprite.atlasLocation());

                int fluidColor = textureGetter.getTintColor();
                float r = ((fluidColor >> 16) & 0xFF) / 255f;
                float g = ((fluidColor >> 8) & 0xFF) / 255f;
                float b = (fluidColor & 0xFF) / 255f;
                float a = ((fluidColor >> 24) & 0xFF) / 255F;
                RenderSystem.setShaderColor(r, g, b, a);

                for (int i = 0; i < width; i += 16) {
                    for (int j = 0; j < renderAmount; j += 16) {
                        Matrix4f matrix = guiGraphics.pose().last().pose();
                        float drawWidth = Math.min(width - i, 16);
                        float drawHeight = Math.min(renderAmount - j, 16);

                        float drawX = x + i;
                        float drawY = posY + j;

                        float minU = fluidSprite.getU0();
                        float maxU = fluidSprite.getU1();
                        float minV = fluidSprite.getV0();
                        float maxV = fluidSprite.getV1();

                        float v = minV + (maxV - minV) * drawHeight / 16F;
                        float v1 = minU + (maxU - minU) * drawWidth / 16F;

                        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
                        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                        bufferBuilder.vertex(matrix, drawX, drawY + drawHeight, 0)
                                .uv(minU, v)
                                .endVertex();
                        bufferBuilder.vertex(matrix, drawX + drawWidth, drawY + drawHeight, 0)
                                .uv(v1, v)
                                .endVertex();
                        bufferBuilder.vertex(matrix, drawX + drawWidth, drawY, 0)
                                .uv(v1, minV)
                                .endVertex();
                        bufferBuilder.vertex(matrix, drawX, drawY, 0)
                                .uv(minU, minV)
                                .endVertex();
                        BufferUploader.drawWithShader(bufferBuilder.end());
                    }
                }
            }
        }
    }

    @Nullable
    public static TextureAtlasSprite getFluidStillSprite(Minecraft minecraft, int fluidId) {
        return getFluidStillSprite(minecraft, BuiltInRegistries.FLUID.byIdOrThrow(fluidId));
    }

    @Nullable
    public static TextureAtlasSprite getFluidStillSprite(Minecraft minecraft, Fluid fluid) {
        return getFluidStillSprite(minecraft, IClientFluidTypeExtensions.of(fluid));
    }

    @Nullable
    public static TextureAtlasSprite getFluidStillSprite(Minecraft minecraft, IClientFluidTypeExtensions fluidRenderer) {
        @Nullable ResourceLocation resourceLocation = fluidRenderer.getStillTexture();
        return resourceLocation != null ? minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(resourceLocation) : null;
    }

    public static <T extends Comparable<T>> void changeBlockState(Level level, BlockEntity blockEntity, Property<T> property, T value, boolean checkPresent) {
        changeBlockState(level, blockEntity.getBlockPos(), blockEntity.getBlockState(), property, value, checkPresent);
    }

    public static <T extends Comparable<T>> void changeBlockState(Level level, BlockPos pos, BlockState blockState, Property<T> property, T value, boolean checkPresent) {
        if (checkPresent) {
            if (!blockState.hasProperty(property)) {
                ToTheMoon.LOGGER.warn("Could not set property %s because it is not present at block [%s - %s]".formatted(property.toString(), level.getBlockState(pos).getBlock().getName(), pos.toString()));
                return;
            }
        }

        T prevValue = blockState.getValue(property);
        if (prevValue != value) {
            blockState = blockState.setValue(property, value);
            level.setBlock(pos, blockState, 3);
        }
    }

    public static <T extends Comparable<T>> T getStateValue(Level level, BlockPos pos, Property<T> stateProperty) {
        return getStateValue(level.getBlockState(pos), stateProperty);
    }

    public static <T extends Comparable<T>> T getStateValue(BlockState currentState, Property<T> stateProperty) {
        Preconditions.checkState(currentState.hasProperty(stateProperty), "Block [%s] does not has the given property [%s]"
                .formatted(
                        ForgeRegistries.BLOCKS.getKey(currentState.getBlock()),
                        stateProperty.getName()
                ));

        return currentState.getValue(stateProperty);
    }

    public static <C extends Container, T extends BaseRecipe<C>> Optional<T> getRecipeFor(Level level, RecipeTypeObject<T> recipeType, C container) {
        return level.getRecipeManager().getRecipeFor(recipeType.get(), container, level);
    }
}

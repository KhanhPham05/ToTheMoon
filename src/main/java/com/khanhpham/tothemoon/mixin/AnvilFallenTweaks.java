package com.khanhpham.tothemoon.mixin;

import com.khanhpham.tothemoon.advancements.ModdedTriggers;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @see net.minecraft.world.level.block.ConduitBlock
 * @see net.minecraft.client.ClientRecipeBook
 */
@SuppressWarnings("deprecation")
@Mixin(AnvilBlock.class)
public class AnvilFallenTweaks {
    @Overwrite
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState state1, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            level.levelEvent(1031, pos, 0);
        }



        List<ItemEntity> items = level.getEntities(EntityType.ITEM, AABB.of(new BoundingBox(pos)), item -> !(item.getItem().getItem() instanceof BlockItem));
        if (!items.isEmpty()) {
            for (ItemEntity item : items) {
                if (!level.getBlockState(pos.below()).getBlock().equals(Blocks.MAGMA_BLOCK))
                    if (item.getItem().is(Items.COAL)) {
                        item.setItem(new ItemStack(ModItems.COAL_DUST.get(), item.getItem().getCount() * 2));
                    } else if (item.getItem().is(Tags.Items.RAW_MATERIALS)) {
                        Set<TagKey<Item>> tags = item.getItem().getTags().filter(tag -> tag.location().getPath().contains("raw_materials/")).collect(Collectors.toSet());
                        tags.forEach(rawMaterialTag -> {
                            String tagPath = rawMaterialTag.location().getPath();
                            String material = null;
                            if (tagPath.contains("/")) {
                                for (int i = 0; i < tagPath.length(); i++) {
                                    if (tagPath.charAt(i) == '/') {
                                        material = new StringBuilder(tagPath).delete(0, i + 1).toString();
                                    }
                                }
                            }
                            if (material != null) {
                                ItemStack result = ItemStack.EMPTY;
                                String modid = item.getItem().getItem().getRegistryName().getNamespace();
                                if (modid.equals("minecraft")) {
                                    result = new ItemStack(Registry.ITEM.get(ModUtils.modLoc(material + "_dust")));
                                } else if (Registry.ITEM.containsKey(new ResourceLocation(modid, "dust_" + material))) {
                                    result = new ItemStack(Registry.ITEM.get(new ResourceLocation(modid, "dust_" + material)));
                                } else if (Registry.ITEM.containsKey(new ResourceLocation(modid, material + "_dust"))) {
                                    result = new ItemStack(Registry.ITEM.get(new ResourceLocation(modid, material + "_dust")));
                                } else if (Registry.ITEM.containsKey(new ResourceLocation(modid, "grit_" + material))) {
                                    result = new ItemStack(Registry.ITEM.get(new ResourceLocation(modid, "grit_" + material)));
                                } else if (Registry.ITEM.containsKey(new ResourceLocation(modid, material + "_grit"))) {
                                    result = new ItemStack(Registry.ITEM.get(new ResourceLocation(modid, material + "_grit")));
                                }

                                if (!result.isEmpty()) {
                                    result.setCount(item.getItem().getCount() + ModUtils.roll(1, 15));
                                    item.setItem(result);
                                }
                            }
                        });
                    }
            }

            //ServerPlayer serverPlayer = (ServerPlayer) level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 4.0d, entity -> true);
            //ModdedTriggers.ANVIL_CRUSHING.trigger(Objects.requireNonNull(serverPlayer), items.stream().map(ItemEntity::getItem).toList());
        }
    }
}

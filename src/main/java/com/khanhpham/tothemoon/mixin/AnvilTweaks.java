package com.khanhpham.tothemoon.mixin;

import com.khanhpham.tothemoon.advancements.ModdedTriggers;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import com.khanhpham.tothemoon.utils.helpers.RegistryEntries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@ParametersAreNonnullByDefault
@SuppressWarnings("deprecation")
@Mixin(AnvilBlock.class)
public class AnvilTweaks extends FallingBlock {
    public AnvilTweaks(Properties pProperties) {
        super(pProperties);
    }

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
                                ItemStack resultStack = ItemStack.EMPTY;
                                String modid = RegistryEntries.ITEM.getNameSpace(item.getItem().getItem());
                                if (modid.equals("minecraft")) {
                                    resultStack = new ItemStack(Registry.ITEM.get(ModUtils.modLoc(material + "_dust")));
                                } else if (Registry.ITEM.containsKey(new ResourceLocation(modid, "dust_" + material))) {
                                    resultStack = new ItemStack(Registry.ITEM.get(new ResourceLocation(modid, "dust_" + material)));
                                } else if (Registry.ITEM.containsKey(new ResourceLocation(modid, material + "_dust"))) {
                                    resultStack = new ItemStack(Registry.ITEM.get(new ResourceLocation(modid, material + "_dust")));

                                } else if (Registry.ITEM.containsKey(new ResourceLocation(modid, "grit_" + material))) {
                                    resultStack = new ItemStack(Registry.ITEM.get(new ResourceLocation(modid, "grit_" + material)));
                                } else if (Registry.ITEM.containsKey(new ResourceLocation(modid, material + "_grit"))) {
                                    resultStack = new ItemStack(Registry.ITEM.get(new ResourceLocation(modid, material + "_grit")));
                                }

                                if (!resultStack.isEmpty()) {
                                    ItemStack crushedStack = item.getItem();
                                    int totalDustAmount = 0;
                                    for (int i = 0; i < crushedStack.getCount(); i++) {
                                        totalDustAmount += 1 + ModUtils.roll(1, 15, 0);
                                    }
                                    resultStack.setCount(totalDustAmount);
                                    item.setItem(resultStack);
                                }
                            }
                        });
                    }
            }

            if (!level.isClientSide && level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 4.0d, entity -> entity instanceof ServerPlayer) instanceof ServerPlayer player) {
                ModdedTriggers.ANVIL_CRUSHING.trigger(player, items.stream().map(ItemEntity::getItem).toList());
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(ModLanguage.ANVIL_DESCRIPTION);
    }
}

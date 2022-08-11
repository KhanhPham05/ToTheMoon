package com.khanhpham.tothemoon.datagen.advancement;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.advancements.AnvilCrushingTrigger;
import com.khanhpham.tothemoon.advancements.MultiblockFormedTrigger;
import com.khanhpham.tothemoon.core.items.ModArmorItem;
import com.khanhpham.tothemoon.core.items.tool.ModArmorMaterial;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.TickTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

import static com.khanhpham.tothemoon.datagen.lang.ModLanguage.*;

public class ModAdvancementProvider extends AdvancementProvider {
    public ModAdvancementProvider(DataGenerator generatorIn, ExistingFileHelper fileHelperIn) {
        super(generatorIn, fileHelperIn);
    }

    @Override
    protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
        Advancement root = Advancement.Builder.advancement().display(ModItems.REDSTONE_STEEL_ALLOY.get(), ROOT, ROOT_DESCRIPTION, new ResourceLocation(Names.MOD_ID, "textures/block/uranium_block.png"), FrameType.CHALLENGE, false, false, false).addCriterion("tick", new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY)).save(consumer, loc("root"), fileHelper);
        Advancement heavyCrushing = display(Items.ANVIL, HEAVY_CRUSHING, FrameType.TASK).addCriterion("req", AnvilCrushingTrigger.TriggerInstance.crushItem()).parent(root).parent(root).save(consumer, loc("anvil_crushing"), fileHelper);
        Advancement aHeatedTopic = display(ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER.get(), A_HEATED_TOPIC, FrameType.TASK).addCriterion("req", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER.get())).parent(heavyCrushing).save(consumer, loc("a_heated_topic"), fileHelper);
        Advancement highHeatSmelting = display(Items.NETHER_BRICK, HIGH_HEAT_SMELTING, FrameType.GOAL)
                .addCriterion("multiblock_formed", MultiblockFormedTrigger.TriggerInstance.multiblock(MultiblockFormedTrigger.MultiblockType.NETHER_BRICK_FURNACE))
                .parent(aHeatedTopic)
                .save(consumer, loc("multiblock_nether_furnace"), fileHelper);


        Advancement gettingATrueUpgrade = display(ModItems.STEEL_INGOT.get(), GETTING_A_TRUE_UPGRADE, FrameType.GOAL)
                .parent(highHeatSmelting)
                .addCriterion("gather_steel", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.STEEL_INGOT.get()))
                .save(consumer, loc("getting_a_true_upgrade"), fileHelper);

        Advancement.Builder coverMeWithCarbonizedIronBuilder = Advancement.Builder.advancement()
                .parent(gettingATrueUpgrade)
                .display(ModItems.ALL_ARMORS.get(EquipmentSlot.CHEST, ModArmorMaterial.STEEL).get(), COVER_ME_WITH_CARBONIZED_IRON, new TranslatableComponent(COVER_ME_WITH_CARBONIZED_IRON.getKey() + ".description"), null, FrameType.TASK, true, true, true);
        //.addCriterion("equip_armors", InventoryChangeTrigger.TriggerInstance.hasItems((ItemLike[]) ModUtils.combineArrays(ModItems.ALL_ARMORS.column(ModArmorMaterial.STEEL).values().stream().map(Supplier::get).toArray(), ModItems.ALL_TOOLS.values().stream().map(Supplier::get).toArray())));
        //


        for (RegistryObject<ModArmorItem> armor : ModItems.ALL_ARMORS.column(ModArmorMaterial.STEEL).values()) {
            String itemName = armor.get().getRegistryName().getPath();
            coverMeWithCarbonizedIronBuilder.addCriterion(itemName, InventoryChangeTrigger.TriggerInstance.hasItems(armor.get()));
        }

        for (RegistryObject<? extends TieredItem> tool : ModItems.ALL_TOOLS.values()) {
            String itemName = tool.get().getRegistryName().getPath();
            coverMeWithCarbonizedIronBuilder.addCriterion(itemName, InventoryChangeTrigger.TriggerInstance.hasItems(tool.get()));
        }

        coverMeWithCarbonizedIronBuilder.requirements(RequirementsStrategy.AND).save(consumer, loc("cover_me_with_carbonized_iron"), fileHelper);
        this.invisibleAdvancements(consumer, fileHelper, root);
    }

    //I hate IntelliJ warnings. I have OCD :PP
    @SuppressWarnings("unused")
    private void invisibleAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper, Advancement root) {
        Advancement ironBlock = Advancement.Builder.advancement().display(Items.IRON_BLOCK, ModLanguage.HIDDEN_ADVANCEMENT, ModLanguage.HIDDEN_ADVANCEMENT, null, FrameType.TASK, false, false, true).addCriterion("get_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_BLOCK)).parent(root).save(consumer, ModUtils.modLoc("hidden/iron_block"), fileHelper);
    }

    private Advancement.Builder display(ItemLike icon, TranslatableComponent advancementComponent, FrameType frame) {
        return Advancement.Builder.advancement().display(icon, advancementComponent, new TranslatableComponent(advancementComponent.getKey() + ".description"), null, frame, true, true, false);
    }


    private ResourceLocation loc(String loc) {
        return ModUtils.modLoc(loc);
    }
}

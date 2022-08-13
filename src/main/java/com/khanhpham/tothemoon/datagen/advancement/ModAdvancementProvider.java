package com.khanhpham.tothemoon.datagen.advancement;

import com.khanhpham.tothemoon.Names;
import com.khanhpham.tothemoon.advancements.AnvilCrushingTrigger;
import com.khanhpham.tothemoon.advancements.MultiblockFormedTrigger;
import com.khanhpham.tothemoon.core.items.ModArmorItem;
import com.khanhpham.tothemoon.core.items.tool.ModArmorMaterial;
import com.khanhpham.tothemoon.core.items.tool.ModToolTiers;
import com.khanhpham.tothemoon.datagen.lang.ModLanguage;
import com.khanhpham.tothemoon.init.ModBlocks;
import com.khanhpham.tothemoon.init.ModItems;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
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

//I hate IntelliJ warnings. I have OCD :PP
@SuppressWarnings("unused")
public class ModAdvancementProvider extends AdvancementProvider {
    public ModAdvancementProvider(DataGenerator generatorIn, ExistingFileHelper fileHelperIn) {
        super(generatorIn, fileHelperIn);
    }

    @Override
    protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
        //Advancement aVerySeriousDedication = this.display(Items.NETHERITE_HOE, VERY_SERIOUS_DEDICATION, FrameType.CHALLENGE).rewards(AdvancementRewards.Builder.experience(1000)).addCriterion("req", ItemDurabilityTrigger.TriggerInstance.changedDurability(ItemPredicate.Builder.item().of(Items.NETHERITE_HOE).build(), MinMaxBounds.Ints.exactly(0))).parent(new ResourceLocation("husbandry/obtain_netherite_hoe")).save(consumer, "a_very_serious_dedication");

        Advancement root = Advancement.Builder.advancement().display(ModItems.REDSTONE_STEEL_ALLOY.get(), ROOT, ROOT_DESCRIPTION, new ResourceLocation(Names.MOD_ID, "textures/block/uranium_block.png"), FrameType.CHALLENGE, false, false, false).addCriterion("tick", new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY)).save(consumer, loc("root"), fileHelper);
        Advancement heavyCrushing = display(Items.ANVIL, HEAVY_CRUSHING, FrameType.GOAL).addCriterion("req", AnvilCrushingTrigger.TriggerInstance.crushItem()).parent(root).parent(root).save(consumer, loc("anvil_crushing"), fileHelper);
        Advancement aHeatedTopic = display(ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER.get(), A_HEATED_TOPIC, FrameType.GOAL).addCriterion("req", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.NETHER_BRICK_FURNACE_CONTROLLER.get())).parent(heavyCrushing).save(consumer, loc("a_heated_topic"), fileHelper);
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

        for (RegistryObject<ModArmorItem> armor : ModItems.ALL_ARMORS.column(ModArmorMaterial.STEEL).values()) {
            String itemName = armor.get().getRegistryName().getPath();
            coverMeWithCarbonizedIronBuilder.addCriterion(itemName, InventoryChangeTrigger.TriggerInstance.hasItems(armor.get()));
        }
        Advancement steelArmors = coverMeWithCarbonizedIronBuilder.requirements(RequirementsStrategy.AND).save(consumer, loc("cover_me_with_carbonized_iron"), fileHelper);

        TieredItem steelPickaxeItem = ModItems.ALL_TOOLS.get(ModToolTiers.ToolType.PICKAXE, ModToolTiers.STEEL.tier()).get();
        Advancement isntThisSteelPickaxe = this.display(steelPickaxeItem, ISNT_THIS_STEEL_PICKAXE, FrameType.TASK, true, true, true).addCriterion("req", InventoryChangeTrigger.TriggerInstance.hasItems(steelPickaxeItem)).parent(steelArmors).save(consumer, loc("steel_pickaxe"), fileHelper);

        Advancement.Builder radiationProtected = this.display(ModItems.ALL_ARMORS.get(EquipmentSlot.CHEST, ModArmorMaterial.URANIUM).get(), RADIATION_PROTECTED, FrameType.TASK, true, true, true).parent(steelArmors);

        Advancement benchWorking = this.display(ModBlocks.WORKBENCH.get(), ModLanguage.BENCH_WORKING, FrameType.TASK).parent(gettingATrueUpgrade).addCriterion("req", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.WORKBENCH.get())).save(consumer, ModUtils.modLoc("bench_working"), fileHelper);
        Advancement automateTheFuel = this.display(ModBlocks.NETHER_BRICKS_FLUID_ACCEPTOR.get(), ModLanguage.AUTOMATE_THE_FUEL, FrameType.TASK).parent(aHeatedTopic).addCriterion("req", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.NETHER_BRICKS_FLUID_ACCEPTOR.get(), ModBlocks.BLACKSTONE_FLUID_ACCEPTOR.get())).requirements(RequirementsStrategy.OR).save(consumer, ModUtils.modLoc("automate_the_fuel"), fileHelper);
        Advancement machineExoskeleton = this.display(ModBlocks.COPPER_MACHINE_FRAME.get(), MACHINE_EXOSKELETON, FrameType.GOAL).parent(benchWorking).addCriterion("req", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.COPPER_MACHINE_FRAME.get())).save(consumer, loc("machine_exoskeleton"), fileHelper);
        Advancement energizeTheFuel = this.display(ModBlocks.COPPER_ENERGY_GENERATOR.get(), ENERGIZE_THE_FUEL, FrameType.GOAL).parent(machineExoskeleton).addCriterion("energize_the_fuel", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.COPPER_MACHINE_FRAME.get())).save(consumer, ModUtils.modLoc("energize_the_fuel"), fileHelper);
        Advancement burningEnergy = this.display(ModBlocks.ENERGY_SMELTER.get(), BURNING_ENERGY, FrameType.GOAL).parent(energizeTheFuel).addCriterion("req", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.ENERGY_SMELTER.get())).save(consumer, loc("burning_energy"), fileHelper);
        Advancement combiningMaterials = this.display(ModBlocks.ALLOY_SMELTER.get(), COMBINING_MATERIALS, FrameType.GOAL).parent(burningEnergy).addCriterion("req", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.ALLOY_SMELTER.get())).save(consumer, loc("combining_materials"),fileHelper);

        Advancement fullyEnergized = this.display(ModBlocks.DIAMOND_ENERGY_GENERATOR.get(), FULLY_ENERGIZED, FrameType.CHALLENGE, true, true, true).addCriterion("req", PlacedBlockTrigger.TriggerInstance.placedBlock(ModBlocks.DIAMOND_ENERGY_GENERATOR.get())).parent(energizeTheFuel).save(consumer, loc("fully_energized"), fileHelper);

        this.invisibleAdvancements(consumer, fileHelper, root);
    }


    private void invisibleAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper, Advancement root) {
        Advancement ironBlock = Advancement.Builder.advancement().display(Items.IRON_BLOCK, ModLanguage.HIDDEN_ADVANCEMENT, ModLanguage.HIDDEN_ADVANCEMENT, null, FrameType.TASK, false, false, true).addCriterion("get_item", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_BLOCK)).parent(root).save(consumer, ModUtils.modLoc("hidden/iron_block"), fileHelper);
    }

    private Advancement.Builder display(ItemLike icon, TranslatableComponent advancementComponent, FrameType frame) {
        return this.display(icon, advancementComponent, frame, true, true, false);
    }

    private Advancement.Builder display(ItemLike icon, TranslatableComponent advancementComponent, FrameType frame, boolean toast, boolean toChat, boolean hide) {
        return Advancement.Builder.advancement().display(icon, advancementComponent, new TranslatableComponent(advancementComponent.getKey() + ".description"), null, frame, toast, toChat, hide);
    }


    private ResourceLocation loc(String loc) {
        return ModUtils.modLoc(loc);
    }
}

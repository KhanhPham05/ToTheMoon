package com.khanhpham.tothemoon.advancements;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AnvilCrushingTrigger extends SimpleCriterionTrigger<AnvilCrushingTrigger.TriggerInstance> {

    private static final ResourceLocation ID = ModUtils.modLoc("anvil_crushing");

    @Override
    protected TriggerInstance createInstance(JsonObject pJson, EntityPredicate.Composite pPlayer, DeserializationContext pContext) {
        ItemPredicate itemPredicate = ItemPredicate.fromJson(pJson.get("item"));
        return new TriggerInstance(ID, itemPredicate);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player, List<ItemStack> groundItems) {
        super.trigger(player, predicate -> predicate.matches(groundItems));
    }

    public static final class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate itemPredicate;

        public TriggerInstance(ResourceLocation pCriterion, ItemPredicate predicate) {
            super(pCriterion, EntityPredicate.Composite.ANY);
            this.itemPredicate = predicate;
        }

        public static TriggerInstance crushItem(ItemPredicate.Builder builder) {
            return new TriggerInstance(ID, builder.build());
        }

        public static TriggerInstance crushItem() {
            return new TriggerInstance(ID, ItemPredicate.ANY);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject json = super.serializeToJson(pConditions);
            if (itemPredicate != ItemPredicate.ANY) json.add(JsonNames.ITEM, itemPredicate.serializeToJson());
            return json;
        }

        public boolean matches(List<ItemStack> groundItems) {
            if (itemPredicate == ItemPredicate.ANY) return true;

            for (ItemStack groundItem : groundItems) {
                return itemPredicate.matches(groundItem);
            }

            return false;
        }

    }
}

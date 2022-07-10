package com.khanhpham.tothemoon.advancements;

import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.JsonNames;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

public class MultiblockFormedTrigger extends SimpleCriterionTrigger<MultiblockFormedTrigger.TriggerInstance> {
    public static final ResourceLocation ID = ModUtils.modLoc("multiblock_formed");

    @Override
    protected TriggerInstance createInstance(JsonObject pJson, EntityPredicate.Composite pPlayer, DeserializationContext pContext) {
        String multiblockName = GsonHelper.getAsString(pJson, JsonNames.MULTIBLOCK, "ANY");

        return new TriggerInstance(pPlayer, multiblockName);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player, MultiblockType multiblockType) {
        ModUtils.log("triggering advancement [{}] for player : {}", this.getId().toString(), player.getName());
        super.trigger(player, (triggerInstance) -> triggerInstance.matches(multiblockType));
    }

    public enum MultiblockType {
        ANY,
        NETHER_BRICK_FURNACE;
    }

    public static final class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final MultiblockType multiblockType;

        public TriggerInstance(EntityPredicate.Composite pPlayer, String multiblockName) {
            this(pPlayer, MultiblockType.valueOf(multiblockName));
        }

        public TriggerInstance(EntityPredicate.Composite pPlayer, MultiblockType multiblockType) {
            super(ID, pPlayer);
            this.multiblockType = multiblockType;
        }

        public static TriggerInstance multiblock(MultiblockType type) {
            return new TriggerInstance(EntityPredicate.Composite.ANY, type);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject json = super.serializeToJson(pConditions);
            if (multiblockType != MultiblockType.ANY) {
                json.addProperty(JsonNames.MULTIBLOCK, multiblockType.toString());
            }
            return json;
        }

        public boolean matches(MultiblockType multiblock) {
            return multiblock == this.multiblockType;
        }
    }
}

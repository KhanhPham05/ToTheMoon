package com.khanhpham.tothemoon.core.recipes.elements;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.khanhpham.tothemoon.utils.helpers.ModUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;

public class ChancedResult {
    public static final String JSON_OBJECT_NAME = "extra_result";
    public static final ChancedResult NO_EXTRA = new ChancedResult(ItemStack.EMPTY, 100);
    private final ItemStack item;
    private final int chance;

    private ChancedResult(ItemStack item, int chance) {
        this.item = item;
        Preconditions.checkState(chance <= 100 && chance > 0);
        this.chance = chance;
    }

    public static ChancedResult fromJson(JsonObject serializedRecipe) {
        if (serializedRecipe.has(JSON_OBJECT_NAME)) {
            JsonObject resultObject = GsonHelper.getAsJsonObject(serializedRecipe, "extra_result");
            ItemStack item = new ItemStack(ModUtils.getItemFromName(GsonHelper.getAsString(resultObject, "item")));
            item.setCount(GsonHelper.getAsInt(resultObject, "count", 1));
            int chance = GsonHelper.getAsInt(resultObject, "chance");
            return new ChancedResult(item, chance);
        }
        return ChancedResult.NO_EXTRA;
    }

    public static ChancedResult fromNetwork(FriendlyByteBuf buffer) {
        ItemStack item = buffer.readItem();
        int chance = buffer.readInt();
        return new ChancedResult(item, chance);
    }

    public static ChancedResult of(ItemStack output, int percentChance) {
        return new ChancedResult(output, percentChance);
    }

    public void toNetwork(FriendlyByteBuf buffer) {
        buffer.writeItem(this.item);
        buffer.writeInt(this.chance);
    }

    public ItemStack tryGiveExtra() {
        return this != NO_EXTRA ? ModUtils.roll(this.item.copy(), this.chance, ItemStack.EMPTY) : ItemStack.EMPTY;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("item", ModUtils.getFullItemName(this.item.getItem()));
        jsonObject.addProperty("chance", this.chance);
        if (this.item.getCount() > 1) jsonObject.addProperty("count", this.item.getCount());
        return jsonObject;
    }

    @Override
    public String toString() {
        return "ChancedResult{" +
                "item=" + item +
                ", chance=" + chance +
                '}';
    }
}

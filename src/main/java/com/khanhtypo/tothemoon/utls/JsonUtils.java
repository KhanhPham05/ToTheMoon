package com.khanhtypo.tothemoon.utls;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistries;
import org.checkerframework.checker.guieffect.qual.UI;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public final class JsonUtils {
    private JsonUtils() {
    }

    private static <T extends JsonElement> T make(T object, Consumer<T> consumer) {
        consumer.accept(object);
        return object;
    }

    public static <T> void addArrayTo(JsonObject object, String name, T[] array) {
        addArrayTo(object, name, a -> {
            for (T t : array) {
                a.add(t.toString());
            }
        });
    }

    public static <A, B> void mapToObject(JsonObject object, String name, Map<A, B> map, Function<A, String> stringFunction, Function<B, JsonElement> elementFunction) {
        object.add(name, make(new JsonObject(), o -> map.forEach(((a, b) -> o.add(stringFunction.apply(a), elementFunction.apply(b))))));
    }

    public static void addArrayTo(JsonObject object, String name, Consumer<JsonArray> arrayConsumer) {
        object.add(name, make(new JsonArray(), arrayConsumer));
    }

    public static <A, B> JsonArray mapToJsonArrays(Map<A, B> map, Function<A, String> aToString, Function<B, JsonElement> bToJson) {
        return make(new JsonArray(), array -> map.forEach((a, b) -> array.add(make(new JsonObject(), jsonObject -> jsonObject.add(aToString.apply(a), bToJson.apply(b))))));
    }

    public static JsonElement itemToJson(ItemStack stack) {
        String itemName = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(stack.getItem())).toString();
        return stack.getCount() == 1
                ? new JsonPrimitive(itemName)
                : make(new JsonObject(), obj -> {
            obj.addProperty("item", itemName);
            obj.addProperty("count", stack.getCount());
        });
    }

    public static <T> void putNumberIfNonNull(JsonObject jsonObject, String name, @Nullable T nullableObj, Function<T, Number> getter) {
        if (nullableObj != null) {
            jsonObject.addProperty(name, getter.apply(nullableObj));
        }
    }

    public static void putIngredientIfNonNull(JsonObject jsonObject, String name, @Nullable Ingredient ingredient) {
        if (ingredient != null) {
            jsonObject.add(name, ingredient.toJson());
        }
    }

    public static ItemStack jsonToItem(JsonObject jsonFile, String name) {
        JsonElement resultElement = jsonFile.get(name);
        Preconditions.checkNotNull(resultElement, "%s is not present".formatted(name));

        if (resultElement.isJsonPrimitive()) {
            return new ItemStack(
                    Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(resultElement.getAsString())))
            );
        }

        return ShapedRecipe.itemStackFromJson(resultElement.getAsJsonObject());
    }
}

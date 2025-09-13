package bnjmn21.realrocket.util;

import com.google.gson.JsonObject;
import com.lowdragmc.lowdraglib.LDLib;
import com.lowdragmc.lowdraglib.utils.NBTToJsonConverter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class StonecuttingRecipeHelper {
    public static void addStonecuttingRecipe(
            Consumer<FinishedRecipe> consumer,
            ResourceLocation id,
            Ingredient input,
            ItemStack output
    ) {
        StonecuttingRecipeHelper.save(consumer, id, input, output);
    }

    public static void toJson(JsonObject json, ResourceLocation id, Ingredient input, ItemStack output) {
        if (!input.isEmpty()) {
            json.add("ingredient", input.toJson());
        }
        if (output.isEmpty()) {
            LDLib.LOGGER.error("shapeless recipe {} output is empty", id);
            throw new IllegalArgumentException(id + ": output items is empty");
        } else {
            json.addProperty("result", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(output.getItem())).toString());
            json.addProperty("count", output.getCount());
        }
    }

    public static void save(Consumer<FinishedRecipe> consumer, ResourceLocation id, Ingredient input, ItemStack output) {
        consumer.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(@NotNull JsonObject pJson) {
                StonecuttingRecipeHelper.toJson(pJson, id, input, output);
            }
            @Override
            public @NotNull ResourceLocation getId() {
                return id.withPrefix("stonecutting/");
            }
            @Override
            public @NotNull RecipeSerializer<?> getType() {
                return RecipeSerializer.STONECUTTER;
            }
            @Nullable
            @Override
            public JsonObject serializeAdvancement() {
                return null;
            }
            @Nullable
            @Override
            public ResourceLocation getAdvancementId() {
                return null;
            }
        });
    }
}

package bnjmn21.realrocket.common.data;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeType;

import bnjmn21.realrocket.data.recipe.generated.PrinterRecipes;

import static bnjmn21.realrocket.RealRocket.id;
import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

public class RRRecipeTypes {

    public static final GTRecipeType BASIC_ROCKET_MOTOR = register("basic_rocket_motor", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(0, 0, 2, 0)
            .setEUIO(IO.NONE)
            .setIconSupplier(RRBlocks.BASIC_ROCKET_MOTOR::asStack);

    static {
        REGISTRATE.addRawLang("realrocket.basic_rocket_motor", "Basic Rocket Motor");
        REGISTRATE.addXei(registry -> {
            registry.addWorkstation(RRRecipeTypes.BASIC_ROCKET_MOTOR, RRBlocks.BASIC_ROCKET_MOTOR.get());
        });
    }

    public static final GTRecipeType PRINTER = register("printer", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(3, 2, 0, 0)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.DUST_OVERLAY)
            .setSlotOverlay(true, false, GuiTextures.SLOT)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ASSEMBLER, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(RRSoundEntries.PRINTER);

    static {
        REGISTRATE.addRawLang("realrocket.printer", "3D Printing");
        REGISTRATE.addRecipes(prov -> {
            for (Material material : GTCEuAPI.materialManager.getRegisteredMaterials()) {
                PrinterRecipes.run(prov, material);
            }
        });
    }

    @SuppressWarnings("deprecation")
    public static GTRecipeType register(String name, String group, RecipeType<?>... proxyRecipes) {
        var recipeType = new GTRecipeType(id(name), group, proxyRecipes);
        GTRegistries.register(BuiltInRegistries.RECIPE_TYPE, recipeType.registryName, recipeType);
        GTRegistries.register(BuiltInRegistries.RECIPE_SERIALIZER, recipeType.registryName, new GTRecipeSerializer());
        GTRegistries.RECIPE_TYPES.register(recipeType.registryName, recipeType);

        return recipeType;
    }

    public static void init() {}
}

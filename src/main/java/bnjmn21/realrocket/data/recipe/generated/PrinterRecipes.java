package bnjmn21.realrocket.data.recipe.generated;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.ToolProperty;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static bnjmn21.realrocket.RealRocket.id;
import static bnjmn21.realrocket.common.data.RRRecipeTypes.PRINTER;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.GTValues.LV;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_PLATE;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;

public class PrinterRecipes {

    public static void run(Consumer<FinishedRecipe> provider, Material material) {
        processParts(provider, material);
        ToolProperty property = material.getProperty(PropertyKey.TOOL);
        if (property != null) {
            processTool(provider, material, property);
        }
    }

    public static void processParts(Consumer<FinishedRecipe> prov, Material material) {
        if (!material.hasProperty(PropertyKey.DUST)) {
            return;
        }
        if (material.shouldGenerateRecipesFor(rotor)) {
            PRINTER.recipeBuilder(id("print_" + material.getName() + "_rotor"))
                    .inputItems(dust, material, 4)
                    .outputItems(rotor, material)
                    .duration((int) material.getMass() * 16)
                    .EUt(material.getBlastTemperature() >= 2800 ? VA[MV] : VA[LV])
                    .circuitMeta(1)
                    .save(prov);
        }
        if (material.shouldGenerateRecipesFor(turbineBlade)) {
            PRINTER.recipeBuilder(id("print_" + material.getName() + "_turbine_blade"))
                    .inputItems(dust, material, 10)
                    .outputItems(turbineBlade, material)
                    .duration(500)
                    .EUt(material.getBlastTemperature() >= 2800 ? VA[HV] : VA[MV])
                    .circuitMeta(2)
                    .save(prov);
        }
        if (material.shouldGenerateRecipesFor(gear)) {
            PRINTER.recipeBuilder(id("print_" + material.getName() + "_gear"))
                    .inputItems(dust, material, 4)
                    .outputItems(gear, material)
                    .duration((int) material.getMass() * 8)
                    .EUt(material.getBlastTemperature() >= 2800 ? VA[MV] : VA[LV])
                    .circuitMeta(3)
                    .save(prov);
        }
        if (material.shouldGenerateRecipesFor(gearSmall)) {
            PRINTER.recipeBuilder(id("print_" + material.getName() + "_small_gear"))
                    .inputItems(dust, material)
                    .outputItems(gearSmall, material)
                    .duration((int) material.getMass() * 4)
                    .EUt(material.getBlastTemperature() >= 2800 ? VA[MV] : VA[LV])
                    .circuitMeta(4)
                    .save(prov);
        }
    }

    public static void processTool(Consumer<FinishedRecipe> provider, Material material, ToolProperty property) {
        if (!material.shouldGenerateRecipesFor(plate)) {
            return;
        }
        final int meltingVoltage = material.getBlastTemperature() > 2800 ? VA[MV] :
                VA[GTValues.LV];

        if (material.hasFlag(GENERATE_PLATE)) {
            if (property.hasType(GTToolType.DRILL_LV)) {
                PRINTER.recipeBuilder(id("print_drill_head_" + material.getName()))
                        .inputItems(dust, material, 4)
                        .inputItems(dust, GTMaterials.Steel, 4)
                        .outputItems(toolHeadDrill, material)
                        .circuitMeta(10)
                        .duration(500)
                        .EUt(meltingVoltage)
                        .save(provider);
            }
            if (property.hasType(GTToolType.CHAINSAW_LV)) {
                PRINTER.recipeBuilder(id("print_chainsaw_head_" + material.getName()))
                        .inputItems(dust, material, 2)
                        .inputItems(dust, GTMaterials.Steel, 4)
                        .outputItems(toolHeadChainsaw, material)
                        .circuitMeta(11)
                        .duration(500)
                        .EUt(meltingVoltage)
                        .save(provider);
            }
            if (property.hasType(GTToolType.WRENCH_LV)) {
                PRINTER.recipeBuilder(id("print_wrench_head_" + material.getName()))
                        .inputItems(dust, material, 4)
                        .inputItems(dust, GTMaterials.Steel, 1)
                        .outputItems(toolHeadWrench, material)
                        .circuitMeta(12)
                        .duration(500)
                        .EUt(meltingVoltage)
                        .save(provider);
            }
            if (property.hasType(GTToolType.WIRE_CUTTER_LV)) {
                PRINTER.recipeBuilder(id("print_wire_cutter_head_" + material.getName()))
                        .inputItems(dust, material, 4)
                        .inputItems(dust, GTMaterials.Steel, 1)
                        .outputItems(toolHeadWireCutter, material)
                        .circuitMeta(13)
                        .duration(500)
                        .EUt(meltingVoltage)
                        .save(provider);
            }
            if (property.hasType(GTToolType.BUZZSAW)) {
                PRINTER.recipeBuilder(id("print_buzzsaw_head_" + material.getName()))
                        .inputItems(dust, material, 4)
                        .outputItems(toolHeadBuzzSaw, material)
                        .circuitMeta(14)
                        .duration(500)
                        .EUt(meltingVoltage)
                        .save(provider);
            }
        }
    }
}

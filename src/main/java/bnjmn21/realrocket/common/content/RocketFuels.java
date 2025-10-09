package bnjmn21.realrocket.common.content;

import bnjmn21.realrocket.api.rocket.ThrustRecipeCapability;
import bnjmn21.realrocket.common.data.RRMaterials;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import static bnjmn21.realrocket.RealRocket.id;
import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;
import static bnjmn21.realrocket.common.data.RRMaterials.Kerosene;
import static bnjmn21.realrocket.common.data.RRMaterials.RP1;
import static bnjmn21.realrocket.common.data.RRRecipeTypes.BASIC_ROCKET_MOTOR;
import static com.gregtechceu.gtceu.api.GTValues.*;

public class RocketFuels {

    public static void init() {
        Kerosene = new Material.Builder(id("kerosene"))
                .liquid(new FluidBuilder().customStill()).flags(MaterialFlags.FLAMMABLE, MaterialFlags.EXPLOSIVE)
                .buildAndRegister();
        RP1 = new Material.Builder(id("rp1"))
                .liquid(new FluidBuilder().customStill()).flags(MaterialFlags.FLAMMABLE, MaterialFlags.EXPLOSIVE)
                .buildAndRegister();

        REGISTRATE.addLang("material", Kerosene.getResourceLocation(), "Kerosene");
        REGISTRATE.addLang("material", RP1.getResourceLocation(), "RP-1");

        REGISTRATE.addRecipes(prov -> {
            BASIC_ROCKET_MOTOR.recipeBuilder(id("rp1_lox"))
                    .inputFluids(RRMaterials.RP1.getFluid(1000))
                    .inputFluids(GTMaterials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 2000))
                    .output(ThrustRecipeCapability.CAP, 10)
                    .duration(8 * 20)
                    .save(prov);
            GTRecipeTypes.COMBUSTION_GENERATOR_FUELS.recipeBuilder(id("kerosene"))
                    .inputFluids(Kerosene.getFluid(1))
                    .duration(15)
                    .EUt(-V[LV])
                    .save(prov);
            GTRecipeTypes.COMBUSTION_GENERATOR_FUELS.recipeBuilder(id("rp1"))
                    .inputFluids(RP1.getFluid(1))
                    .duration(20)
                    .EUt(-V[LV])
                    .save(prov);
            GTRecipeTypes.DISTILLERY_RECIPES.recipeBuilder(id("kerosene"))
                    .inputFluids(GTMaterials.Diesel.getFluid(1000))
                    .outputFluids(Kerosene.getFluid(1000))
                    .circuitMeta(1)
                    .duration(200)
                    .EUt(V[MV])
                    .save(prov);
            GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder(id("rp1"))
                    .inputFluids(Kerosene.getFluid(2000))
                    .inputFluids(GTMaterials.Hydrogen.getFluid(100))
                    .outputFluids(RP1.getFluid(2000))
                    .outputFluids(GTMaterials.HydrogenSulfide.getFluid(50))
                    .duration(160)
                    .EUt(V[HV])
                    .save(prov);
        });
    }
}

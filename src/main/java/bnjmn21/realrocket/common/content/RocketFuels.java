package bnjmn21.realrocket.common.content;

import bnjmn21.realrocket.api.rocket.ThrustRecipeCapability;
import bnjmn21.realrocket.common.data.RRMaterials;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static bnjmn21.realrocket.RealRocket.id;
import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;
import static bnjmn21.realrocket.common.data.RRRecipeTypes.BASIC_ROCKET_MOTOR;

public class RocketFuels {
    public static void init() {
        REGISTRATE.addRecipes(RocketFuels::addRecipes);
    }

    private static void addRecipes(Consumer<FinishedRecipe> prov) {
        BASIC_ROCKET_MOTOR.recipeBuilder(id("rp1_lox"))
                .inputFluids(RRMaterials.RP1.getFluid(1000))
                .inputFluids(GTMaterials.Oxygen.getFluid(FluidStorageKeys.LIQUID, 2000))
                .output(ThrustRecipeCapability.CAP, 10)
                .duration(8*20)
                .save(prov);
    }
}

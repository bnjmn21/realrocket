package bnjmn21.realrocket.integration.emi;

import bnjmn21.realrocket.common.data.RRBlocks;
import bnjmn21.realrocket.common.data.RRRecipeTypes;
import bnjmn21.realrocket.integration.xei.XeiPlugin;
import bnjmn21.realrocket.integration.xei.XeiRegistry;

import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.integration.emi.recipe.GTRecipeEMICategory;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiIngredient;

import java.util.function.Consumer;

@EmiEntrypoint
public class RealRocketEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        XeiPlugin.register(new Registry(registry));
    }

    public static GTRecipeEMICategory categoryForRecipeType(GTRecipeType recipeType) {
        return GTRecipeEMICategory.CATEGORIES.apply(recipeType.getCategory());
    }

    static class Registry extends XeiRegistry {

        EmiRegistry registry;

        Registry(EmiRegistry registry) {
            this.registry = registry;
        }

        public void addWorkstation(GTRecipeType recipeType, Block workstation) {
            registry.addWorkstation(
                    categoryForRecipeType(RRRecipeTypes.BASIC_ROCKET_MOTOR),
                    EmiIngredient.of(Ingredient.of(RRBlocks.BASIC_ROCKET_MOTOR)));
        }

        @Override
        public void emiOnly(Consumer<EmiRegistry> emiFn) {
            emiFn.accept(registry);
        }
    }
}

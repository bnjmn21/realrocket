package bnjmn21.realrocket.integration.xei;

import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import dev.emi.emi.api.EmiRegistry;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

/**
 * Abstraction layer for JEI, REI, EMI
 */
public abstract class XeiRegistry {
    public abstract void addWorkstation(GTRecipeType recipeType, Block workstation);
    public void emiOnly(Consumer<EmiRegistry> emiFn) {}
}

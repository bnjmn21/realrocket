package bnjmn21.realrocket.api.rocket;

import com.gregtechceu.gtceu.api.recipe.GTRecipeType;

import bnjmn21.realrocket.common.block.EngineBlock;

/**
 * An engine / rocket motor.
 * Implement this on your {@link net.minecraft.world.level.block.Block} class.
 * Or use {@link EngineBlock}, an implementation.
 */
public interface Engine {

    int getTier();

    GTRecipeType getRecipeType();
}

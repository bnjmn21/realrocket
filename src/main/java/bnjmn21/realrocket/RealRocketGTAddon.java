package bnjmn21.realrocket;

import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.api.rocket.ThrustRecipeCapability;
import bnjmn21.realrocket.common.data.RRSoundEntries;
import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

@GTAddon
public class RealRocketGTAddon implements IGTAddon {
    @Override
    public GTRegistrate getRegistrate() {
        return RRRegistries.REGISTRATE;
    }

    @Override
    public void initializeAddon() {

    }

    @Override
    public String addonModId() {
        return RealRocket.MOD_ID;
    }

    public void registerTagPrefixes() {
        RRRegistries.REGISTRATE.registerTagPrefixes();
    }

    @Override
    public void registerWorldgenLayers() {
        RRRegistries.REGISTRATE.registerWorldgenLayers();
    }

    @Override
    public void registerOreVeins() {
        RRRegistries.REGISTRATE.registerOreVeins();
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> consumer) {
        RRRegistries.REGISTRATE.registerRecipes(consumer);
    }

    @Override
    public void registerSounds() {
        RRSoundEntries.init();
    }

    @Override
    public void registerRecipeCapabilities() {
        GTRegistries.RECIPE_CAPABILITIES.register("thrust", ThrustRecipeCapability.CAP);
    }
}

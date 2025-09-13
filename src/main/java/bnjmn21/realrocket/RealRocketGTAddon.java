package bnjmn21.realrocket;

import bnjmn21.realrocket.api.RRRegistries;
import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.BlockTags;

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
}

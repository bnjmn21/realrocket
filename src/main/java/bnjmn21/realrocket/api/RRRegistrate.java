package bnjmn21.realrocket.api;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.api.celestial_body.CelestialBodyType;
import bnjmn21.realrocket.api.units.BaseUnitBuilder;
import bnjmn21.realrocket.api.units.BaseUnitType;
import bnjmn21.realrocket.util.Lazy;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.api.data.worldgen.IWorldGenLayer;
import com.gregtechceu.gtceu.api.data.worldgen.SimpleWorldGenLayer;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.common.data.GTOres;
import com.mojang.serialization.Codec;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This mod uses a slightly different structure for registering things.
 * Instead of splitting the data across classes by type of data, e.g. {@code RRRecipes}, {@code RRMaterials}, etc.,
 * the data is split by topic, e.g. {@code Moon}, {@code Mars}, etc.
 * <p>
 * This requires some extra code to work with {@link IGTAddon}s, which is handled here.
 */
public class RRRegistrate extends GTRegistrate {
    ArrayList<Consumer<Consumer<FinishedRecipe>>> recipeAdders = new ArrayList<>();
    ArrayList<Runnable> tagPrefixRegistrars = new ArrayList<>();
    ArrayList<Lazy<IWorldGenLayer>> worldGenLayers = new ArrayList<>();
    ArrayList<Pair<ResourceLocation, Consumer<GTOreDefinition>>> oreVeins = new ArrayList<>();

    protected RRRegistrate(String modId) {
        super(modId);
    }

    public static @NotNull RRRegistrate create(@NotNull String modId) {
        return new RRRegistrate(modId);
    }

    public BaseUnitBuilder<Registrate> baseUnit(String name, BaseUnitType unit) {
        return entry(name, callback ->
                new BaseUnitBuilder<>(this, this, name, callback, unit)
        );
    }

    public RegistryEntry<Codec<? extends CelestialBodyType>> celestialBodyType(String name, Codec<? extends CelestialBodyType> unit) {
        return simple(name, RRRegistries.CELESTIAL_BODY_TYPES, () -> unit);
    }

    public void addRecipes(Consumer<Consumer<FinishedRecipe>> recipeAdder) {
        this.recipeAdders.add(recipeAdder);
    }

    public void addTagPrefix(Runnable tagPrefixRegistrar) {
        this.tagPrefixRegistrars.add(tagPrefixRegistrar);
    }

    public Lazy<IWorldGenLayer> addWorldGenLayer(Supplier<IWorldGenLayer> worldGenLayerSupplier) {
        Lazy<IWorldGenLayer> lazy = new Lazy<>(worldGenLayerSupplier);
        this.worldGenLayers.add(lazy);
        return lazy;
    }

    public Lazy<IWorldGenLayer> addSimpleWorldGenLayer(String name, IWorldGenLayer.RuleTestSupplier target, Set<ResourceLocation> levels) {
        return addWorldGenLayer(() -> new SimpleWorldGenLayer(name, target, levels));
    }

    public void addOreVein(ResourceLocation name, Consumer<GTOreDefinition> config) {
        this.oreVeins.add(new Pair<>(name, config));
    }

    public void addOreVein(String name, Consumer<GTOreDefinition> config) {
        addOreVein(RealRocket.id(name), config);
    }

    /**
     * Call this in {@link IGTAddon#addRecipes}
     */
    public void registerRecipes(Consumer<FinishedRecipe> consumer) {
        this.recipeAdders.forEach(recipeAdder -> recipeAdder.accept(consumer));
    }

    /**
     * Call this in {@link IGTAddon#registerTagPrefixes}
     */
    public void registerTagPrefixes() {
        this.tagPrefixRegistrars.forEach(Runnable::run);
    }

    /**
     * Call this in {@link IGTAddon#registerWorldgenLayers}
     */
    public void registerWorldgenLayers() {
        this.worldGenLayers.forEach(Lazy::get);
    }

    public void registerOreVeins() {
        this.oreVeins.forEach(pair -> GTOres.create(pair.getA(), pair.getB()));
    }
}

package bnjmn21.realrocket.api;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.api.celestial_body.CelestialBodyTypeCodec;
import bnjmn21.realrocket.api.rocket.Engine;
import bnjmn21.realrocket.api.units.Unit;
import bnjmn21.realrocket.common.data.RRDimensionMarkers;
import bnjmn21.realrocket.integration.xei.XeiRegistry;
import bnjmn21.realrocket.util.Lazy;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.api.data.worldgen.IWorldGenLayer;
import com.gregtechceu.gtceu.api.data.worldgen.SimpleWorldGenLayer;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.common.data.GTDimensionMarkers;
import com.gregtechceu.gtceu.common.data.GTOres;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
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
    String currentLangPrefix = "";
    ArrayList<Consumer<XeiRegistry>> xeiRegistrars = new ArrayList<>();
    ArrayList<DimMarkerReg> dimMarkers = new ArrayList<>();

    private record DimMarkerReg(ResourceKey<Level> loc, int tier, BlockEntry<Block> block) { }

    protected RRRegistrate(String modId) {
        super(modId);
    }

    public static @NotNull RRRegistrate create(@NotNull String modId) {
        return new RRRegistrate(modId);
    }

//    public <T extends BaseUnit<?, ?>> BaseUnitType baseUnit(String name, Function<Double, T> constructor, Class<T> clazz) {
//        return RRRegistries.UNITS.register(RealRocket.id(name), new BaseUnitType(constructor, clazz));
//    }
    public void unit(ResourceLocation id, Unit<?> unit) {
        RRRegistries.UNITS.register(id, unit);
    }

    public void celestialBodyType(String name, CelestialBodyTypeCodec type) {
        RRRegistries.CELESTIAL_BODY_TYPES.register(RealRocket.id(name), type);
    }

    public void engine(ResourceKey<? extends Engine> engine) {
        RRRegistries.ENGINES.register(engine.location(), engine.location());
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

    public void setLangPrefix(String prefix) {
        this.currentLangPrefix = prefix;
    }

    /**
     * Creates a new lang entry, where the id is prefixed by {@link #setLangPrefix(String)}
     */
    public MutableComponent addPrefixedLang(String id, String value) {
        return addRawLang(this.currentLangPrefix + "." + id, value);
    }

    /**
     * Creates a new lang entry
     * @return a helper for applying some arguments to the translation string template
     */
    public Translatable addRawLangTemplate(String key, String value, Function<MutableComponent, MutableComponent> factory) {
        addRawLang(key, value);
        return new Translatable(key, factory);
    }

    public Translatable addPrefixedLangTemplate(String key, String value, Function<MutableComponent, MutableComponent> factory) {
        addPrefixedLang(key, value);
        return new Translatable(this.currentLangPrefix + "." + key, factory);
    }

    public record Translatable(String key, Function<MutableComponent, MutableComponent> factory) {
        public MutableComponent apply(Object... args) {
            return factory.apply(Component.translatable(key, args));
        }
    }

    public void addBlockTag(TagKey<Block> key, Block... blocks) {
        this.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> {
            prov.addTag(key).add(blocks);
        });
    }

    public void addFluidTags(NonNullConsumer<RegistrateTagsProvider.IntrinsicImpl<Fluid>> data) {
        this.addDataGenerator(ProviderType.FLUID_TAGS, data);
    }

    public void addXei(Consumer<XeiRegistry> registrar) {
        this.xeiRegistrars.add(registrar);
    }

    public void registerXei(XeiRegistry registry) {
        this.xeiRegistrars.forEach(registrar -> registrar.accept(registry));
    }

    public void dimensionMarker(ResourceKey<Level> dim, int tier) {
        this.dimMarkers.add(new DimMarkerReg(dim, tier, RRDimensionMarkers.createMarker(dim.location().getPath())));
    }

    public void registerDimMarkers() {
        this.dimMarkers.forEach(marker ->
            GTDimensionMarkers.createAndRegister(marker.loc.location(), marker.tier, marker.block::asItem, null)
        );
    }
}

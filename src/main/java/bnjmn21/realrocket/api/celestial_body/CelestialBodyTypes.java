package bnjmn21.realrocket.api.celestial_body;

import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.api.units.Distance;
import bnjmn21.realrocket.api.units.DoseRate;
import bnjmn21.realrocket.api.units.Temperature;
import bnjmn21.realrocket.api.units.Time;
import bnjmn21.realrocket.util.serialization.ByNameCodec;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModLoader;

import java.util.Optional;
import java.util.stream.Stream;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

public class CelestialBodyTypes {
    public static final Codec<CelestialBodyType> CODEC = new ByNameCodec<>(RRRegistries.CELESTIAL_BODY_TYPES)
            .dispatch(CelestialBodyType::codec, CelestialBodyTypeCodec::codec);
    /**
     * A planet that you can visit.
     *
     * @param day how long one rotation around its axis takes
     * @param radius determines how large it looks from other planets
     * @param level the dimension
     * @param atmosphere atmosphere description
     * @param minTemperature temperature at midnight
     * @param maxTemperature temperature at daytime
     * @param terrestrialRadiation radiation on the surface
     * @param cosmicRadiation radiation in space
     * @param cosmicRadiationOnSurface additional radiation, scales with skylight, that means no radiation at night or in caves
     * @param marker icon for use in GUIs
     * @param tier the planet tier
     * @param alwaysDiscovered whether the planet is always discovered or requires research
     * @param gravity relative to earth
     */
    public record Planet(
            Time day,
            Distance radius,
            ResourceKey<Level> level,
            Atmosphere atmosphere,
            Temperature minTemperature,
            Temperature maxTemperature,
            DoseRate terrestrialRadiation,
            DoseRate cosmicRadiation,
            DoseRate cosmicRadiationOnSurface,
            ResourceLocation marker,
            int tier,
            boolean alwaysDiscovered,
            float gravity
    ) implements CelestialBodyType {
        public static final MapCodec<Planet> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Time.CODEC.fieldOf("day").forGetter(Planet::day),
                Distance.CODEC.fieldOf("radius").forGetter(Planet::radius),
                ResourceKey.codec(Registries.DIMENSION).fieldOf("level").forGetter(Planet::level),
                Atmosphere.CODEC.fieldOf("atmosphere").forGetter(Planet::atmosphere),
                Temperature.CODEC.fieldOf("min_temperature").forGetter(Planet::minTemperature),
                Temperature.CODEC.fieldOf("max_temperature").forGetter(Planet::maxTemperature),
                DoseRate.CODEC.fieldOf("terrestrial_radiation").forGetter(Planet::terrestrialRadiation),
                DoseRate.CODEC.fieldOf("cosmic_radiation").forGetter(Planet::cosmicRadiation),
                DoseRate.CODEC.fieldOf("cosmic_radiation_on_surface").forGetter(Planet::cosmicRadiationOnSurface),
                ResourceLocation.CODEC.fieldOf("marker").forGetter(Planet::marker),
                Codec.INT.fieldOf("tier").forGetter(Planet::tier),
                Codec.BOOL.optionalFieldOf("always_discovered", false).forGetter(Planet::alwaysDiscovered),
                Codec.FLOAT.fieldOf("gravity").forGetter(Planet::gravity)
        ).apply(instance, Planet::new));
        public static final CelestialBodyTypeCodec CODEC = new CelestialBodyTypeCodec(KeyDispatchDataCodec.of(MAP_CODEC).codec());

        @Override
        public CelestialBodyTypeCodec codec() {
            return CODEC;
        }

        @Override
        public Stream<ResourceKey<Level>> levels() {
            return Stream.of(this.level);
        }

        @Override
        public Optional<Boolean> isDiscovered() {
            return Optional.of(this.alwaysDiscovered());
        }

        @Override
        public int tier() {
            return this.tier;
        }

        @Override
        public float gravityOf(VirtualLevelKey level) {
            return this.gravity;
        }

        public boolean tierDebuff() {
            return this.atmosphere != Atmosphere.None;
        }
    }

    /**
     * A star, for example, the sun
     *
     * @param color an RGB hex color as an integer
     * @param radius determines how large it looks from other planets
     *
     * <h2>Example Colors</h2>
     * <ul>
     *     <li>Sun: {@code 0xfdd837}</li>
     * </ul>
     */
    public record Star(
            int color,
            Distance radius
    ) implements CelestialBodyType {
        public static final MapCodec<Star> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.INT.fieldOf("color").forGetter(Star::color),
                Distance.CODEC.fieldOf("radius").forGetter(Star::radius)
        ).apply(instance, Star::new));
        public static final CelestialBodyTypeCodec CODEC = new CelestialBodyTypeCodec(KeyDispatchDataCodec.of(MAP_CODEC).codec());

        @Override
        public CelestialBodyTypeCodec codec() {
            return CODEC;
        }

        @Override
        public Stream<ResourceKey<Level>> levels() {
            return Stream.empty();
        }

        @Override
        public Optional<Boolean> isDiscovered() {
            return Optional.empty();
        }

        @Override
        public int tier() {
            throw new RuntimeException("Not a planet");
        }

        @Override
        public float gravityOf(VirtualLevelKey level) {
            throw new RuntimeException("Not a planet");
        }
    }

    public static void register() {
        REGISTRATE.celestialBodyType("planet", Planet.CODEC);
        REGISTRATE.celestialBodyType("star", Star.CODEC);
    }

    public static void init() {
        ModLoader.get().postEvent(new GTCEuAPI.RegisterEvent<>(RRRegistries.CELESTIAL_BODY_TYPES, CelestialBodyTypeCodec.class));
        register();

        RRRegistries.CELESTIAL_BODY_TYPES.freeze();
    }
}

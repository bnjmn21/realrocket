package bnjmn21.realrocket.api.celestial_body;

import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.api.units.quantities.Distance;
import bnjmn21.realrocket.api.units.quantities.DoseRate;
import bnjmn21.realrocket.api.units.quantities.Temperature;
import bnjmn21.realrocket.api.units.quantities.Time;
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
     * @param atmosphere how breathable the atmosphere is, {@code Optional.Empty} means there is no atmosphere
     * @param minTemperature temperature at midnight
     * @param maxTemperature temperature at daytime
     * @param shieldableRadiationOnSurface nullified when the player isn't in direct sunlight
     * @param unshieldableRadiationOnSurface always applies
     * @param shieldableRadiationInOrbit nullified when the player isn't in direct sunlight
     * @param unshieldableRadiationInOrbit always applies
     * @param marker icon for use in GUIs
     */
    public record Planet(
            Time day,
            Distance radius,
            ResourceKey<Level> level,
            Optional<Breathability> atmosphere,
            Temperature minTemperature,
            Temperature maxTemperature,
            DoseRate shieldableRadiationOnSurface,
            DoseRate unshieldableRadiationOnSurface,
            DoseRate shieldableRadiationInOrbit,
            DoseRate unshieldableRadiationInOrbit,
            ResourceLocation marker
    ) implements CelestialBodyType {
        public static final MapCodec<Planet> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Time.CODEC.fieldOf("day").forGetter(Planet::day),
                Distance.CODEC.fieldOf("radius").forGetter(Planet::radius),
                ResourceKey.codec(Registries.DIMENSION).fieldOf("level").forGetter(Planet::level),
                Breathability.CODEC.optionalFieldOf("atmosphere").forGetter(Planet::atmosphere),
                Temperature.CODEC.fieldOf("min_temperature").forGetter(Planet::minTemperature),
                Temperature.CODEC.fieldOf("max_temperature").forGetter(Planet::maxTemperature),
                DoseRate.CODEC.fieldOf("shieldable_radiation_on_surface").forGetter(Planet::shieldableRadiationOnSurface),
                DoseRate.CODEC.fieldOf("unshieldable_radiation_on_surface").forGetter(Planet::unshieldableRadiationOnSurface),
                DoseRate.CODEC.fieldOf("shieldable_radiation_in_orbit").forGetter(Planet::shieldableRadiationInOrbit),
                DoseRate.CODEC.fieldOf("unshieldable_radiation_in_orbit").forGetter(Planet::unshieldableRadiationInOrbit),
                ResourceLocation.CODEC.fieldOf("marker").forGetter(Planet::marker)
        ).apply(instance, Planet::new));
        public static final CelestialBodyTypeCodec CODEC = new CelestialBodyTypeCodec(KeyDispatchDataCodec.of(MAP_CODEC).codec());

        @Override
        public CelestialBodyTypeCodec codec() {
            return CODEC;
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

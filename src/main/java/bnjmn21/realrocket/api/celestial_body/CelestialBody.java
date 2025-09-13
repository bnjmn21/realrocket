package bnjmn21.realrocket.api.celestial_body;

import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.api.units.quantities.Distance;
import bnjmn21.realrocket.api.units.quantities.Time;
import com.gregtechceu.gtceu.api.data.DimensionMarker;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceKey;

import java.util.Optional;

/**
 * Describes a celestial body that might be a moon, planet, sun or black hole
 * @param body the celestial body
 * @param orbit its orbit around its parent, {@code Optional.Empty} means it has no parent, like for black holes
 * @param day how long one rotation around its axis takes, 0 if it doesn't rotate
 * @param radius determines how large it looks from other planets
 * @param marker icon for use in GUIs
 */
public record CelestialBody(
        CelestialBodyType body,
        Optional<Orbit> orbit,
        Time day,
        Distance radius,
        ResourceKey<DimensionMarker> marker
) {
    /**
     * Describes the celestial body's orbit around its parent
     * @param parent
     * @param minRadius also known as periapsis
     * @param maxRadius also known as apoapsis
     * @param year how long one orbit takes
     * @param angle in radians, for map view, determines the angle of ellipse that describes the orbit. Can be left as 0 for most orbits
     */
    public record Orbit(
            ResourceKey<CelestialBody> parent,
            Distance minRadius,
            Distance maxRadius,
            Time year,
            double angle
    ) {
        public static final MapCodec<Orbit> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ResourceKey.codec(RRRegistries.CELESTIAL_BODIES).fieldOf("parent").forGetter(Orbit::parent),
                Distance.CODEC.fieldOf("min_radius").forGetter(Orbit::maxRadius),
                Distance.CODEC.fieldOf("max_radius").forGetter(Orbit::maxRadius),
                Time.CODEC.fieldOf("year").forGetter(Orbit::year),
                Codec.DOUBLE.fieldOf("angle").orElse(0.0).forGetter(Orbit::angle)
        ).apply(instance, Orbit::new));
    }
}

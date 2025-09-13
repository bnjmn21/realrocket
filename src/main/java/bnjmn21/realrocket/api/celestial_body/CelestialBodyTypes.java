package bnjmn21.realrocket.api.celestial_body;

import bnjmn21.realrocket.api.units.quantities.DoseRate;
import bnjmn21.realrocket.api.units.quantities.Temperature;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class CelestialBodyTypes {
    /**
     * A planet that you can visit.
     *
     * @param level the dimension
     * @param atmosphere how breathable the atmosphere is, {@code Optional.Empty} means there is no atmosphere
     * @param minTemperature temperature at midnight
     * @param maxTemperature temperature at daytime
     * @param shieldableRadiationOnSurface nullified when the player isn't in direct sunlight
     * @param unshieldableRadiationOnSurface always applies
     */
    public record Planet(
            ResourceKey<Level> level,
            Optional<Breathability> atmosphere,
            Temperature minTemperature,
            Temperature maxTemperature,
            DoseRate cosmicRadiation,
            DoseRate terrestrialRadiation,
            DoseRate shieldableRadiationOnSurface,
            DoseRate unshieldableRadiationOnSurface,
            DoseRate shieldableRadiationInOrbit,
            DoseRate unshieldableRadiationInOrbit
    ) {}

    /**
     * A star, for example, the sun
     *
     * <h2>Example Colors</h2>
     * <ul>
     *     <li>Sun: {@code 0xfdd837}</li>
     * </ul>
     */
    public record Star(
            int color
    ) {}

    public static void init() {}
}

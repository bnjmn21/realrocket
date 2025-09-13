package bnjmn21.realrocket.api.planet;

import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 *
 * @param atmosphereDensity in `atm`, 0 = no atmosphere, 1 = earth atmosphere
 */
public record PlanetProperties(
        FluidStack[] atmosphere,
        double atmosphereDensity,
        boolean atmosphereBreathable,
        boolean skylightReachesSurface
) {
    public static PlanetProperties getDefault() {
        return new PlanetProperties(
                new FluidStack[]{},
                0.0,
                false,
                true);
    }
}

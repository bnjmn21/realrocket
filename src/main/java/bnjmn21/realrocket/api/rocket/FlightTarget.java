package bnjmn21.realrocket.api.rocket;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import bnjmn21.realrocket.api.celestial_body.CelestialBody;
import bnjmn21.realrocket.api.celestial_body.VirtualLevelKey;
import bnjmn21.realrocket.api.celestial_body.VirtualLevels;

import java.util.Objects;

public interface FlightTarget {

    int tier(Level level);

    record VirtualLevel(VirtualLevelKey level) implements FlightTarget {

        @Override
        public int tier(Level level) {
            return VirtualLevels.levels(level).get(this.level).tier();
        }
    }

    record NewSpaceStation(ResourceKey<CelestialBody> around) implements FlightTarget {

        @Override
        public int tier(Level level) {
            return Objects.requireNonNull(VirtualLevels.getCbRegistry(level).get(around)).body().tier();
        }
    }
}

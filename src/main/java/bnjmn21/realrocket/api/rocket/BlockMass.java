package bnjmn21.realrocket.api.rocket;

import bnjmn21.realrocket.api.units.Mass;

public interface BlockMass {

    Mass DEFAULT_MASS = Mass.Kilogram.of(200);

    default Mass getMass() {
        return DEFAULT_MASS;
    }
}

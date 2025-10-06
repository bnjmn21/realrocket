package bnjmn21.realrocket.api.rocket;

import bnjmn21.realrocket.api.units.quantities.Mass;

public interface BlockMass {
    Mass DEFAULT_MASS = new Mass.Kilogram(200);

    default Mass getMass() {
        return DEFAULT_MASS;
    }
}
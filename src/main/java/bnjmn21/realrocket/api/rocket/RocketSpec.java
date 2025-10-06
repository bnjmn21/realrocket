package bnjmn21.realrocket.api.rocket;

import bnjmn21.realrocket.api.units.Force;
import bnjmn21.realrocket.api.units.Mass;

public record RocketSpec(Mass emptyMass, EngineData engineData, double thrustEfficiency, long startingFuel) {
    public record EngineData(Force thrust, long mbFuelPerSecond) {}
}

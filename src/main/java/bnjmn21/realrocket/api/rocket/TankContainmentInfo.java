package bnjmn21.realrocket.api.rocket;

/**
 * Information about a fuel type a tank can hold.
 */
public record TankContainmentInfo(FuelType type, long amount) {}

package bnjmn21.realrocket.api.units;

public interface Unit<Base extends Unit<Base>> {
    /**
     * Convert to the base unit, for example, seconds, meters, etc.
     */
    Base toBase();
}

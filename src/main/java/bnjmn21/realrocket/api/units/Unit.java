package bnjmn21.realrocket.api.units;

public interface Unit<Self extends Unit<Self, Base>, Base extends Self> {
    /**
     * Convert to the base unit, for example, seconds, meters, etc.
     */
    Base toBase();
}

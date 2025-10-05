package bnjmn21.realrocket.api.units;

public interface Unit<Self extends Unit<Self, Base>, Base extends Self> {
    /**
     * Convert to the base unit, for example, seconds, meters, etc.
     */
    Base toBase();

    Self add(Self rhs);
    Self sub(Self rhs);
    Self mul(double rhs);
    Self div(double rhs);
    double div(Self rhs);

    double getValue();
}

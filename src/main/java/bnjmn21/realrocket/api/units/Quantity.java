package bnjmn21.realrocket.api.units;

import lombok.Getter;

public abstract class Quantity<Self extends Quantity<Self>> {

    @Getter
    double value;

    Quantity(double value) {
        this.value = value;
    }

    abstract Self copy();

    public Self add(Self rhs) {
        Self lhs = this.copy();
        lhs.value += rhs.value;
        return lhs;
    }

    public Self sub(Self rhs) {
        Self lhs = this.copy();
        lhs.value -= rhs.value;
        return lhs;
    }

    public Self mul(double rhs) {
        Self lhs = this.copy();
        lhs.value *= rhs;
        return lhs;
    }

    public Self div(double rhs) {
        Self lhs = this.copy();
        lhs.value /= rhs;
        return lhs;
    }
}

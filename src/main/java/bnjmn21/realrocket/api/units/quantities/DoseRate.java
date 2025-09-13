package bnjmn21.realrocket.api.units.quantities;

import bnjmn21.realrocket.api.units.DivUnit;

public class DoseRate extends DivUnit<Dose, Time, DoseRate> {
    public DoseRate(Dose dose, Time time) {
        super(dose, time);
    }

    @Override
    public DoseRate toBase() {
        return new DoseRate(this.a, this.b);
    }
}

package bnjmn21.realrocket.api.units.quantities;

import bnjmn21.realrocket.api.units.BaseUnit;
import bnjmn21.realrocket.api.units.Units;
import com.mojang.serialization.Codec;

@SuppressWarnings("unused")
abstract public class Dose extends BaseUnit<Dose, Dose.Sievert> {
    public static final Codec<Dose> CODEC = BaseUnit.createCodec(Dose.class, Units.SIEVERT);

    public Dose(double value) {
        super(value);
    }

    public DoseRate div(Time by) {
        return new DoseRate(this, by);
    }

    public DoseRate perSecond() {
        return this.div(new Time.Second(1));
    }

    public DoseRate perHour() {
        return this.div(new Time.Hour(1));
    }

    public DoseRate perYear() {
        return this.div(new Time.Day(1));
    }

    public static class Sievert extends Dose {
        public Sievert(double value) {
            super(value);
        }

        @Override
        public Sievert toBase() {
            return new Sievert(this.value);
        }
    }

    public static class Millisievert extends Dose {
        public Millisievert(double value) {
            super(value);
        }

        @Override
        public Sievert toBase() {
            return new Sievert(this.value / 1000.0);
        }
    }

    public static class Microsievert extends Dose {
        public Microsievert(double value) {
            super(value);
        }

        @Override
        public Sievert toBase() {
            return new Sievert(this.value / 1000000.0);
        }
    }
}

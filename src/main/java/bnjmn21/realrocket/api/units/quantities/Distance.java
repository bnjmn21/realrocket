package bnjmn21.realrocket.api.units.quantities;

import bnjmn21.realrocket.api.units.BaseUnit;
import bnjmn21.realrocket.api.units.Units;
import com.mojang.serialization.Codec;

abstract public class Distance extends BaseUnit<Distance, Distance.Meter> {
    public static final Codec<Distance> CODEC = BaseUnit.createCodec(Distance.class, Units.METER);

    public Distance(double value) {
        super(value);
    }

    /**
     * Convert to the base unit, in this case meters.
     */
    public abstract Meter toBase();

    /**
     * Gets the value in meters
     */
    public double meters() {
        return this.toBase().value;
    }

    public static class Meter extends Distance {
        public Meter(double value) {
            super(value);
        }

        @Override
        public Meter toBase() {
            return new Meter(this.value);
        }

        @Override
        public double meters() {
            return this.value;
        }
    }

    public static class Kilometer extends Distance {
        public Kilometer(double value) {
            super(value);
        }

        @Override
        public Meter toBase() {
            return new Meter(this.value * 1000);
        }
    }

    public static class AU extends Distance {
        public AU(double value) {
            super(value);
        }

        @Override
        public Meter toBase() {
            return new Meter(this.value * 1.495e11);
        }
    }

    public static class LightYear extends Distance {
        public LightYear(double value) {
            super(value);
        }

        @Override
        public Meter toBase() {
            return new Meter(this.value * 9.467e15);
        }
    }


    public static class Parsec extends Distance {
        public Parsec(double value) {
            super(value);
        }

        @Override
        public Meter toBase() {
            return new Meter(this.value * 3.086e16);
        }
    }
}

package bnjmn21.realrocket.api.units.quantities;

import bnjmn21.realrocket.api.units.BaseUnit;
import bnjmn21.realrocket.api.units.Units;
import com.mojang.serialization.Codec;

@SuppressWarnings("unused")
abstract public class Force extends BaseUnit<Force, Force.Newton> {
    public static final Codec<Force> CODEC = BaseUnit.createCodec(Force.class, Units.NEWTON);

    public Force(double value) {
        super(value);
    }

    /**
     * Gets the value in newtons
     */
    public double newtons() {
        return this.toBase().value;
    }

    /**
     * Gets the value in kilonewtons
     */
    public double kilonewtons() {
        return this.toBase().value / 1000;
    }

    public static class Newton extends Force {
        public Newton(double value) {
            super(value);
        }

        @Override
        public Newton toBase() {
            return new Newton(this.value);
        }
    }

    public static class Kilonewton extends Force {
        public Kilonewton(double value) {
            super(value);
        }

        @Override
        public Newton toBase() {
            return new Newton(this.value * 1000);
        }

        @Override
        public double kilonewtons() {
            return this.value;
        }
    }
}

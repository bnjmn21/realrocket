package bnjmn21.realrocket.api.units.quantities;

import bnjmn21.realrocket.api.units.BaseUnit;
import bnjmn21.realrocket.api.units.Units;
import com.mojang.serialization.Codec;

@SuppressWarnings("unused")
public abstract class Mass extends BaseUnit<Mass, Mass.Kilogram> {
    public static final Codec<Mass> CODEC = BaseUnit.createCodec(Mass.class, Units.KILOGRAM);

    public Mass(double value) {
        super(value);
    }

    /**
     * Gets the value in kilograms
     */
    public double kilogram() {
        return this.toBase().value;
    }

    /**
     * Gets the value in tonnes
     */
    public double tonne() {
        return this.toBase().value / 1000;
    }

    public static class Kilogram extends Mass {
        public Kilogram(double value) {
            super(value);
        }

        @Override
        public Kilogram toBase() {
            return new Kilogram(this.value);
        }
    }

    public static class Tonne extends Mass {
        public Tonne(double value) {
            super(value);
        }

        @Override
        public Kilogram toBase() {
            return new Kilogram(this.value * 1000);
        }
    }
}

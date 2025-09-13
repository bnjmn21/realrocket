package bnjmn21.realrocket.api.units.quantities;

import bnjmn21.realrocket.api.units.BaseUnit;

public abstract class Temperature extends BaseUnit<Temperature.Kelvin> {
    public Temperature(double value) {
        super(value);
    }

    /**
     * Gets the value in kelvin
     */
    public double kelvin() {
        return this.toBase().value;
    }

    /**
     * Gets the value in Celsius
     */
    public double celsius() {
        return this.toBase().value;
    }

    public static class Kelvin extends Temperature {
        public Kelvin(double value) {
            super(value);
        }

        @Override
        public Kelvin toBase() {
            return new Kelvin(this.value);
        }
    }

    public static class Celsius extends Temperature {
        public Celsius(double value) {
            super(value);
        }

        @Override
        public Kelvin toBase() {
            return new Kelvin(this.value + 273.15);
        }
    }

    public static class Fahrenheit extends Temperature {
        public Fahrenheit(double value) {
            super(value);
        }

        @Override
        public Kelvin toBase() {
            return new Kelvin((this.value + 459.67) * (5.0/9.0));
        }
    }
}

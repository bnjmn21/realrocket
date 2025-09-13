package bnjmn21.realrocket.api.units.quantities;

import bnjmn21.realrocket.api.units.BaseUnit;
import bnjmn21.realrocket.api.units.Units;
import com.mojang.serialization.Codec;

public abstract class Time extends BaseUnit<Time.Second> {
    public static final Codec<Time> CODEC = BaseUnit.createCodec(Time.class, Units.SECOND);

    public Time(double value) {
        super(value);
    }

    /**
     * Gets the value in seconds
     */
    public double secs() {
        return this.toBase().value;
    }

    /**
     * Gets the value in milliseconds
     */
    public double millis() {
        return this.toBase().value * 1000;
    }

    /**
     * Gets the value in minecraft ticks
     */
    public double ticks() {
        return this.toBase().value * 20;
    }

    public static class Second extends Time {
        public Second(double value) {
            super(value);
        }

        @Override
        public Second toBase() {
            return new Second(this.value);
        }

        @Override
        public double secs() {
            return this.value;
        }

        @Override
        public double millis() {
            return this.value * 1000;
        }
    }

    public static class Minute extends Time {
        public Minute(double value) {
            super(value);
        }

        @Override
        public Second toBase() {
            return new Second(this.value * 60);
        }
    }

    public static class Hour extends Time {
        public Hour(double value) {
            super(value);
        }

        @Override
        public Second toBase() {
            return new Second(this.value * (60 * 60));
        }
    }

    public static class Day extends Time {
        public Day(double value) {
            super(value);
        }

        @Override
        public Second toBase() {
            return new Second(this.value * (60 * 60 * 24));
        }
    }

    public static class Year extends Time {
        public Year(double value) {
            super(value);
        }

        @Override
        public Second toBase() {
            return new Second(this.value * (60 * 60 * 24 * 365));
        }
    }
}

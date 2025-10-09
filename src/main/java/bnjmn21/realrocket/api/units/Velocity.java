package bnjmn21.realrocket.api.units;

import com.mojang.serialization.Codec;

@SuppressWarnings("unused")
public class Velocity extends Quantity<Velocity> {

    public static final Codec<Velocity> CODEC = Unit.createDivCodec(Velocity::new, Distance.CODEC, Distance.Meter,
            Time.CODEC, Time.Second);
    public static final Unit<Velocity> MetersPerSecond = Unit.base(Velocity::new, "meters_per_second"),
            MetersPerTick = Unit.factor(1.0 / 20, Velocity::new, "meters_per_tick");

    Velocity(double value) {
        super(value);
    }

    public static Velocity of(Distance a, Time b) {
        return new Velocity(a.value / b.value);
    }

    @Override
    Velocity copy() {
        return new Velocity(this.value);
    }
}

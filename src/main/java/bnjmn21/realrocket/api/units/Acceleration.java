package bnjmn21.realrocket.api.units;

import com.mojang.serialization.Codec;

@SuppressWarnings("unused")
public class Acceleration extends Quantity<Acceleration> {

    public static final Codec<Acceleration> CODEC = Unit.createDivCodec(Acceleration::new, Velocity.CODEC,
            Velocity.MetersPerSecond, Time.CODEC, Time.Second);
    public static final Unit<Acceleration> MetersPerSecondSq = Unit.base(Acceleration::new, "meters_per_second_sq"),
            MetersPerTickSq = Unit.factor(1.0 / 20 / 20, Acceleration::new, "meters_per_tick_sq");

    Acceleration(double value) {
        super(value);
    }

    public static Acceleration of(Velocity a, Time b) {
        return new Acceleration(a.value / b.value);
    }

    @Override
    Acceleration copy() {
        return new Acceleration(value);
    }
}

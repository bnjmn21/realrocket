package bnjmn21.realrocket.api.units;

import com.mojang.serialization.Codec;

@SuppressWarnings("unused")
public class Distance extends Quantity<Distance> {
    public static final Runnable INIT = Unit.mkInit(Distance.class);
    public static final Unit<Distance> Meter = Unit.base(Distance::new, "meter"),
            Kilometer = Unit.factor(1e3, Distance::new, "kilometer"),
            AU = Unit.factor(1.495e11, Distance::new, "au"),
            LightYear = Unit.factor(9.467e15, Distance::new, "light_year"),
            Parsec = Unit.factor(3.086e16, Distance::new, "parsec");
    public static final Codec<Distance> CODEC = Unit.createCodec(Distance::new, Meter);

    Distance(double value) {
        super(value);
    }

    @Override
    Distance copy() {
        return new Distance(value);
    }
}

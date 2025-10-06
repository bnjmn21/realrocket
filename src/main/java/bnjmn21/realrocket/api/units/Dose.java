package bnjmn21.realrocket.api.units;

import com.mojang.serialization.Codec;

@SuppressWarnings("unused")
public class Dose extends Quantity<Dose> {
    public static final Runnable INIT = Unit.mkInit(Dose.class);
    public static final Unit<Dose> Sievert = Unit.base(Dose::new, "sievert"),
            Millisievert = Unit.factor(1e-3, Dose::new, "millisievert"),
            Microsievert = Unit.factor(1e-6, Dose::new, "microsievert");
    public static final Codec<Dose> CODEC = Unit.createCodec(Dose::new, Sievert);

    Dose(double value) {
        super(value);
    }

    @Override
    Dose copy() {
        return new Dose(value);
    }
}

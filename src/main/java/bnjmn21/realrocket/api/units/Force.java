package bnjmn21.realrocket.api.units;

import com.mojang.serialization.Codec;

@SuppressWarnings("unused")
public class Force extends Quantity<Force> {
    public static final Runnable INIT = Unit.mkInit(Force.class);
    public static final Unit<Force> Newton = Unit.base(Force::new, "newton"),
            Kilonewton = Unit.factor(1e3, Force::new, "kilonewton");
    public static final Codec<Force> CODEC = Unit.createCodec(Force::new, Newton);

    Force(double value) {
        super(value);
    }

    @Override
    Force copy() {
        return new Force(this.value);
    }
}

package bnjmn21.realrocket.api.units.quantities;

import bnjmn21.realrocket.api.units.DivUnit;
import com.mojang.serialization.Codec;

public class Acceleration extends DivUnit<Velocity, Time, Acceleration> {
    public static final Codec<Acceleration> CODEC = DivUnit.createCodec(Acceleration::new, Velocity.CODEC, Velocity::new, Time.CODEC, Time.Second::new);

    public Acceleration(Velocity a, Time b) {
        super(a, b);
    }

    public Acceleration(double value) {
        super(value);
    }

    public static Acceleration perSecondSq(Distance a) {
        return new Acceleration(new Velocity(a, new Time.Second(1)), new Time.Second(1));
    }

    public static Acceleration perTickSq(Distance a) {
        return new Acceleration(new Velocity(a, new Time.Second(0.05)), new Time.Second(0.05));
    }

    @Override
    public Acceleration toBase() {
        return new Acceleration(this.getValue());
    }
}

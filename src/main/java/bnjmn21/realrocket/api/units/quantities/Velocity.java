package bnjmn21.realrocket.api.units.quantities;

import bnjmn21.realrocket.api.units.DivUnit;
import com.mojang.serialization.Codec;

public class Velocity extends DivUnit<Distance, Time, Velocity> {
    public static final Codec<Velocity> CODEC = DivUnit.createCodec(Velocity::new, Distance.CODEC, Distance.Meter::new, Time.CODEC, Time.Second::new);

    public Velocity(Distance a, Time b) {
        super(a, b);
    }

    protected Velocity(double value) {
        super(value);
    }

    @Override
    public Velocity toBase() {
        return new Velocity(this.getValue());
    }
}

package bnjmn21.realrocket.api.units.quantities;

import bnjmn21.realrocket.api.units.DivUnit;
import com.mojang.serialization.Codec;

public class DoseRate extends DivUnit<Dose, Time, DoseRate> {
    public static final Codec<DoseRate> CODEC = DivUnit.createCodec(DoseRate::new, Dose.CODEC, Time.CODEC);

    public DoseRate(Dose dose, Time time) {
        super(dose, time);
    }

    @Override
    public DoseRate toBase() {
        return new DoseRate(this.a, this.b);
    }
}

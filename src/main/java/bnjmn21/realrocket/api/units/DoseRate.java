package bnjmn21.realrocket.api.units;

import com.mojang.serialization.Codec;

@SuppressWarnings("unused")
public class DoseRate extends Quantity<DoseRate> {
    public static final Codec<DoseRate> CODEC = Unit.createDivCodec(DoseRate::new, Dose.CODEC, Dose.Sievert, Time.CODEC, Time.Second);
    public static final Unit<DoseRate> SievertPerSecond = Unit.base(DoseRate::new, "sievert_per_second");

    DoseRate(double value) {
        super(value);
    }

    public static DoseRate of(Dose a, Time b) {
        return new DoseRate(a.value / b.value);
    }

    @Override
    DoseRate copy() {
        return new DoseRate(this.value);
    }
}

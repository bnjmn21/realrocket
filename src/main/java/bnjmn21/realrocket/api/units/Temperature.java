package bnjmn21.realrocket.api.units;

import com.mojang.serialization.Codec;

@SuppressWarnings("unused")
public class Temperature extends Quantity<Temperature> {
    public static final Runnable INIT = Unit.mkInit(Temperature.class);
    public static final Unit<Temperature> Kelvin = Unit.base(Temperature::new, "kelvin"),
            Celsius = new Unit<>(Temperature::new, b -> b + 273.15, c -> c - 273.15, "celsius");
    public static final Codec<Temperature> CODEC = Unit.createCodec(Temperature::new, Kelvin);

    Temperature(double value) {
        super(value);
    }

    @Override
    Temperature copy() {
        return new Temperature(this.value);
    }
}

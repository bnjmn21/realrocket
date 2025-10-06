package bnjmn21.realrocket.api.units;

import com.mojang.serialization.Codec;

@SuppressWarnings("unused")
public class Time extends Quantity<Time> {
    public static final Runnable INIT = Unit.mkInit(Time.class);
    public static final Unit<Time> Second = Unit.base(Time::new, "second"),
            Millisecond = Unit.factor(1e-3, Time::new, "millisecond"),
            Tick = Unit.factor(1.0/20.0, Time::new, "tick"),
            Minute = Unit.factor(60, Time::new, "minute"),
            Hour = Unit.factor(60*60, Time::new, "hour"),
            Day = Unit.factor(60*60*24, Time::new, "day"),
            Year = Unit.factor(60*60*24*365, Time::new, "year");
    public static final Codec<Time> CODEC = Unit.createCodec(Time::new, Second);

    Time(double value) {
        super(value);
    }

    @Override
    Time copy() {
        return new Time(this.value);
    }
}

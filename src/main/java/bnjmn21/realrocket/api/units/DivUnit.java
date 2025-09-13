package bnjmn21.realrocket.api.units;

import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.util.HolderCodec;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public abstract class DivUnit<A extends Unit<?>, B extends Unit<?>, Self extends DivUnit<A, B, Self>> implements Unit<Self> {
    public A a;
    public B b;

    public DivUnit(A a, B b) {
        this.a = a;
        this.b = b;
    }

    record DivValue(double value, Unit<?> a, Unit<?> b) {
//        static final MapCodec<DivValue<A, B>> CODEC = RecordCodecBuilder.mapCodec((instance) ->
//                instance.group(
//                        Codec.DOUBLE.fieldOf("value").forGetter(DivValue::value),
//                        HolderCodec.create(RRRegistries.UNITS).fieldOf("type").forGetter(DivValue::type)
//                ).apply(instance, DivValue::new));
    }
}

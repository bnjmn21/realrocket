package bnjmn21.realrocket.api.units;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;

import java.util.function.BiFunction;

public abstract class DivUnit<A extends Unit<?>, B extends Unit<?>, Self extends DivUnit<A, B, Self>> implements Unit<Self> {

    public static <A extends Unit<?>, B extends Unit<?>, Self extends DivUnit<A, B, Self>>
            Codec<Self> createCodec(BiFunction<A, B, Self> constructor, Codec<A> a, Codec<B> b) {
        return Codec.pair(a, b.fieldOf("per").codec()).xmap(
                pair -> constructor.apply(pair.getFirst(), pair.getSecond()),
                div -> Pair.of(div.a, div.b)
        );
    }

    public A a;
    public B b;

    public DivUnit(A a, B b) {
        this.a = a;
        this.b = b;
    }
}

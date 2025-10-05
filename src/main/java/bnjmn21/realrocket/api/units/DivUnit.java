package bnjmn21.realrocket.api.units;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class DivUnit<A extends Unit<A, ?>, B extends Unit<B, ?>, Self extends DivUnit<A, B, Self>> implements Unit<Self, Self> {

    public static <A extends Unit<A, ?>, B extends Unit<B, ?>, Self extends DivUnit<A, B, Self>>
            Codec<Self> createCodec(BiFunction<A, B, Self> constructor, Codec<A> a, Function<Double, A> aBase, Codec<B> b, Function<Double, B> bBase) {
        return Codec.pair(a, b.fieldOf("per").codec()).xmap(
                pair -> constructor.apply(pair.getFirst(), pair.getSecond()),
                div -> Pair.of(aBase.apply(div.value), bBase.apply(1.0))
        );
    }

    double value;

    public DivUnit(A a, B b) {
        this.value = a.toBase().getValue() / b.toBase().getValue();
    }

    protected DivUnit(double value) {
        this.value = value;
    }

    @Override
    public Self add(Self rhs) {
        Self base = this.toBase();
        base.value += rhs.value;
        return base;
    }

    @Override
    public Self sub(Self rhs) {
        Self base = this.toBase();
        base.value -= rhs.value;
        return base;
    }

    @Override
    public Self mul(double rhs) {
        Self base = this.toBase();
        base.value *= rhs;
        return base;
    }

    @Override
    public Self div(double rhs) {
        Self base = this.toBase();
        base.value /= rhs;
        return base;
    }

    @Override
    public double div(Self other) {
        return this.value / other.value;
    }

    @Override
    public double getValue() {
        return value;
    }
}

package bnjmn21.realrocket.api.units;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.util.serialization.ByNameCodec;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

public class Unit<T extends Quantity<T>> {
    DoubleUnaryOperator fromBase;
    DoubleUnaryOperator toBase;
    DoubleFunction<T> constructor;
    String name;

    public Unit(DoubleFunction<T> constructor, DoubleUnaryOperator fromBase, DoubleUnaryOperator toBase, String name) {
        this.constructor = constructor;
        this.fromBase = fromBase;
        this.toBase = toBase;
        this.name = name;
    }

    public T of(double value) {
        return this.constructor.apply(this.toBase.applyAsDouble(value));
    }

    public double get(T value) {
        return this.fromBase.applyAsDouble(value.getValue());
    }

    public static <T extends Quantity<T>> Unit<T> factor(double factor, DoubleFunction<T> constructor, String name) {
        return new Unit<>(constructor, v -> v / factor, v -> v * factor, name);
    }

    public static <T extends Quantity<T>> Unit<T> base(DoubleFunction<T> constructor, String name) {
        return new Unit<>(constructor, v -> v, v -> v, name);
    }

    public static Runnable mkInit(Class<? extends Quantity<?>> clazz, String modId) {
        return () -> {
            for (Field field : clazz.getFields()) {
                if (Modifier.isStatic(field.getModifiers()) && Unit.class.isAssignableFrom(field.getType())) {
                    Unit<?> val;
                    try {
                        val = (Unit<?>) field.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    RRRegistries.UNITS.register(new ResourceLocation(modId, val.name), val);
                }
            }
        };
    }

    public static Runnable mkInit(Class<? extends Quantity<?>> clazz) {
        return mkInit(clazz, RealRocket.MOD_ID);
    }

    public static<E extends Quantity<E>> Codec<E> createCodec(DoubleFunction<E> constructor, Unit<E> baseUnit) {
        return new UnitCodec<>(constructor, baseUnit);
    }

    record UnitCodec<E extends Quantity<E>>(DoubleFunction<E> constructor, Unit<E> baseUnit) implements Codec<E> {
        @Override
        public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> ops, T input) {
            return UnitValue.CODEC.decode(ops, input).flatMap(unitValueResult -> {
                UnitValue unitValue = unitValueResult.getFirst();
                //noinspection unchecked
                return DataResult.success(Pair.of((E) unitValue.type.of(unitValue.value), unitValueResult.getSecond()), Lifecycle.stable());
            });
        }

        @Override
        public <T> DataResult<T> encode(E input, DynamicOps<T> ops, T prefix) {
            return UnitValue.CODEC.encode(new UnitValue(input.value, this.baseUnit), ops, prefix);
        }
    }

    record UnitValue(double value, Unit<?> type) {
        static final MapCodec<UnitValue> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                        Codec.DOUBLE.fieldOf("value").orElse(1.0).forGetter(UnitValue::value),
                        new ByNameCodec<>(RRRegistries.UNITS).fieldOf("type").forGetter(UnitValue::type)
                ).apply(instance, UnitValue::new));

        static final Codec<UnitValue> CODEC = Codec.either(new ByNameCodec<>(RRRegistries.UNITS), MAP_CODEC.codec())
                .xmap(
                        either -> either.map(
                                v -> new UnitValue(1.0, v),
                                Function.identity()
                        ),
                        Either::right
                );
    }

    public static <A extends Quantity<A>, B extends Quantity<B>, T extends Quantity<T>> Codec<T> createDivCodec(
            DoubleFunction<T> constructor, Codec<A> aCodec, Unit<A> aBase, Codec<B> bCodec, Unit<B> bBase
    ) {
        return Codec.pair(aCodec, bCodec.fieldOf("per").codec()).xmap(
                pair -> constructor.apply(pair.getFirst().value / pair.getSecond().value),
                div -> Pair.of(aBase.of(div.value), bBase.of(1))
        );
    }
}

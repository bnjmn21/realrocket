package bnjmn21.realrocket.api.units;

import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.api.units.quantities.Time;
import bnjmn21.realrocket.util.serialization.HolderCodec;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.Holder;

import java.util.function.Function;

abstract public class BaseUnit<Self extends BaseUnit<Self, Base>, Base extends Self> implements Unit<Self, Base> {
    /**
     * The internal value of the unit. You probably don't want to use this if you don't know what unit you're working
     * with since, for example, the value in {@link Time.Second} is different from the value in {@link Time.Minute}.
     */
    public double value;

    public BaseUnit(double value) {
        this.value = value;
    }

    public Self add(Self rhs) {
        Base base = this.toBase();
        base.value += rhs.toBase().value;
        return base;
    }

    public Self sub(Self rhs) {
        Base base = this.toBase();
        base.value -= rhs.toBase().value;
        return base;
    }

    public Self mul(double rhs) {
        Base base = this.toBase();
        base.value *= rhs;
        return base;
    }

    public Self div(double rhs) {
        Base base = this.toBase();
        base.value /= rhs;
        return base;
    }

    public static <T extends BaseUnit<T, Base>, Base extends T> Codec<T> createCodec(
            Class<T> unit,
            RegistryEntry<BaseUnitType> baseUnit
    ) {
        return new UnitCodec<>(unit, baseUnit);
    }

    record UnitCodec<E extends BaseUnit<E, Base>, Base extends E>(Class<E> unit,
                                                                  RegistryEntry<BaseUnitType> baseUnit) implements Codec<E> {
        @Override
            public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> ops, T input) {
                return UnitValue.CODEC.decode(ops, input).flatMap(unitValueResult -> {
                    UnitValue unitValue = unitValueResult.getFirst();
                    return unitValue.type.get().tryConstruct(unitValue.value, this.unit)
                            .map(res -> DataResult.success(
                                    Pair.of(res, unitValueResult.getSecond()),
                                    Lifecycle.stable()))
                            .orElseGet(() -> DataResult.error(
                                    () -> "Incorrect unit \"" + unitValue.type + "\", expected " + this.unit.getName(),
                                    Lifecycle.stable()));
                });
            }

            @Override
            public <T> DataResult<T> encode(E input, DynamicOps<T> ops, T prefix) {
                Holder<BaseUnitType> baseUnitHolder = baseUnit.getHolder().orElseThrow();
                if (baseUnitHolder instanceof Holder.Reference<BaseUnitType> ref) {
                    return UnitValue.CODEC.encode(new UnitValue(input.toBase().value, ref), ops, prefix);
                } else {
                    throw new RuntimeException("Expected a Holder.Reference (this shouldn't happen)");
                }
            }
        }

    record UnitValue(double value, Holder.Reference<BaseUnitType> type) {
        static final MapCodec<UnitValue> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                        Codec.DOUBLE.fieldOf("value").orElse(1.0).forGetter(UnitValue::value),
                        HolderCodec.create(RRRegistries.UNITS).fieldOf("type").forGetter(UnitValue::type)
                ).apply(instance, UnitValue::new));

        static final Codec<UnitValue> CODEC = Codec.either(HolderCodec.create(RRRegistries.UNITS), MAP_CODEC.codec())
                .xmap(
                        either -> either.map(
                                v -> new UnitValue(1.0, v),
                                Function.identity()
                        ),
                        Either::right
                );
    }
}

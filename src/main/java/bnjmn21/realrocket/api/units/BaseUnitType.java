package bnjmn21.realrocket.api.units;

import lombok.Getter;

import java.util.Optional;
import java.util.function.Function;

public class BaseUnitType {
    final Function<Double, ? extends BaseUnit<?>> constructor;
    @Getter
    final Class<? extends BaseUnit<?>> type;

    public <T extends BaseUnit<?>> BaseUnitType(Function<Double, T> constructor, Class<T> type) {
        this.constructor = constructor;
        this.type = type;
    }

    public <T extends BaseUnit<?>> Optional<T> tryConstruct(double value, Class<T> unit) {
        BaseUnit<?> instance = this.constructor.apply(value);
        if (unit.isAssignableFrom(instance.getClass())) {
            //noinspection unchecked
            return Optional.of((T) instance);
        } else {
            return Optional.empty();
        }
    }

}

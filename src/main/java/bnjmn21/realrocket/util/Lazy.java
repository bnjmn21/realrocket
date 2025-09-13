package bnjmn21.realrocket.util;

import com.mojang.datafixers.util.Either;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * A {@link Supplier} that caches the values after the first time it is executed.
 */
public class Lazy<T> implements Supplier<T> {
    public Either<Supplier<T>, T> value;

    public Lazy(Supplier<T> supplier) {
        this.value = Either.left(supplier);
    }

    @Override
    public T get() {
        Optional<Supplier<T>> supplier = this.value.left();
        if (supplier.isPresent()) {
            T value = supplier.get().get();
            this.value = Either.right(value);
            return value;
        } else {
            //noinspection OptionalGetWithoutIsPresent
            return this.value.right().get();
        }
    }
}

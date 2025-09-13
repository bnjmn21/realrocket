package bnjmn21.realrocket.util.serialization;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;

import java.util.HashMap;
import java.util.Map;

public class EnumCodec<E extends Enum<E> & GetName> implements Codec<E> {
    Map<String, E> values;

    public EnumCodec(Class<E> type) {
        this.values = new HashMap<>();
        for (E entry : type.getEnumConstants()) {
            this.values.put(entry.getName(), entry);
        }
    }

    @Override
    public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> ops, T input) {
        return Codec.STRING.decode(ops, input).flatMap(strResult -> {
            E value = this.values.get(strResult.getFirst());
            if (value == null) {
                return DataResult.error(() -> "\"" + strResult.getFirst() + "\" is not a valid value", Lifecycle.stable());
            }

            return DataResult.success(Pair.of(value, strResult.getSecond()), Lifecycle.stable());
        });
    }

    @Override
    public <T> DataResult<T> encode(E input, DynamicOps<T> ops, T prefix) {
        return values.entrySet().stream()
                .filter(v -> v.getValue() == input)
                .map(Map.Entry::getKey)
                .findFirst()
                .map(v -> Codec.STRING.encode(v, ops, prefix))
                .orElseGet(() -> DataResult.error(() -> "unreachable", Lifecycle.stable()));
    }
}

package bnjmn21.realrocket.util.serialization;

import com.gregtechceu.gtceu.api.registry.GTRegistry;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.resources.ResourceLocation;

public class ByNameCodec<E> implements Codec<E> {
    GTRegistry.RL<E> registry;

    public ByNameCodec(GTRegistry.RL<E> registry) {
        this.registry = registry;
    }

    @Override
    public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> ops, T input) {
        DataResult<Pair<ResourceLocation, T>> keyDecodeResult = ResourceLocation.CODEC.decode(ops, input);
        if (keyDecodeResult.result().isEmpty()) {
            return DataResult.error(() -> "Expected resource location");
        }

       ResourceLocation key = keyDecodeResult.result().get().getFirst();

        if (!this.registry.containKey(key)) {
            return DataResult.error(() -> key + " doesn't exist in registry " + registry.toString(), Lifecycle.stable());
        }

        return DataResult.success(new Pair<>(this.registry.get(key), input), Lifecycle.stable());
    }

    @Override
    public <T> DataResult<T> encode(E input, DynamicOps<T> ops, T prefix) {
        return ResourceLocation.CODEC.encode(this.registry.getKey(input), ops, prefix);
    }
}

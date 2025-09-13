package bnjmn21.realrocket.util.serialization;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class HolderCodec<E> implements Codec<Holder.Reference<E>> {
    private final ResourceKey<? extends Registry<E>> registryKey;

    public static <T> HolderCodec<T> create(ResourceKey<? extends Registry<T>> registryKey) {
        return new HolderCodec<>(registryKey);
    }

    public HolderCodec(ResourceKey<? extends Registry<E>> registryKey) {
        this.registryKey = registryKey;
    }

    public <T> DataResult<T> encode(Holder.Reference<E> input, DynamicOps<T> ops, T prefix) {
        if (ops instanceof RegistryOps<?> registryOps) {
            Optional<HolderOwner<E>> registry = registryOps.owner(this.registryKey);
            if (registry.isPresent()) {
                if (!input.canSerializeIn(registry.get())) {
                    return DataResult.error(() -> "Element " + input + " is not valid in current registry set");
                }

                return ResourceLocation.CODEC.encode(input.key().location(), ops, prefix);
            }
        }

        throw new RuntimeException("HolderCodec requires RegistryOps");
    }

    public <T> DataResult<Pair<Holder.Reference<E>, T>> decode(DynamicOps<T> dynamicOps, T object) {
        if (dynamicOps instanceof RegistryOps<?> registryOps) {
            Optional<HolderGetter<E>> optRegistry = registryOps.getter(this.registryKey);
            if (optRegistry.isEmpty()) {
                return DataResult.error(() -> "Registry does not exist: " + this.registryKey);
            }

            HolderGetter<E> registry = optRegistry.get();
            DataResult<Pair<ResourceLocation, T>> keyDecodeResult = ResourceLocation.CODEC.decode(dynamicOps, object);
            if (keyDecodeResult.result().isEmpty()) {
                return DataResult.error(() -> "Expected resource location");
            }

            Pair<ResourceLocation, T> keyDecode = keyDecodeResult.result().get();
            ResourceKey<E> key = ResourceKey.create(this.registryKey, keyDecode.getFirst());
            return registry
                    .get(key)
                    .map(v -> DataResult.success(v, Lifecycle.stable()))
                    .orElseGet(() -> DataResult.error(() -> "Failed to get element" + key))
                    .map(value -> Pair.of(value, keyDecode.getSecond()))
                    .setLifecycle(Lifecycle.stable());
        }

        throw new RuntimeException("HolderCodec requires RegistryOps");
    }

    public String toString() {
        return "HolderCodec[" + this.registryKey + "]";
    }
}

package bnjmn21.realrocket.api.celestial_body;

import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.util.HolderCodec;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.util.KeyDispatchDataCodec;

import java.util.function.Function;

public interface CelestialBodyType {
    public static final Codec<CelestialBodyType> CODEC = HolderCodec.create(RRRegistries.CELESTIAL_BODY_TYPES)
            .xmap(Holder::get, CelestialBodyType::cannotBeEncoded)
            .dispatch(CelestialBodyType::cannotBeEncoded, Function.identity());

    KeyDispatchDataCodec<? extends CelestialBodyType> codec();

    private static <R, A1> R cannotBeEncoded(A1 a1) {
        throw new UnsupportedOperationException("Cannot be encoded");
    }
}

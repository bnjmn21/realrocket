package bnjmn21.realrocket.api.celestial_body;

import net.minecraft.util.KeyDispatchDataCodec;

public interface CelestialBodyType {
    KeyDispatchDataCodec<? extends CelestialBodyType> codec();
}

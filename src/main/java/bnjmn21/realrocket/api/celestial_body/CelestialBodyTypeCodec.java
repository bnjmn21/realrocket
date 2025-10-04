package bnjmn21.realrocket.api.celestial_body;

import com.mojang.serialization.Codec;

public record CelestialBodyTypeCodec(Codec<? extends CelestialBodyType> codec) {}

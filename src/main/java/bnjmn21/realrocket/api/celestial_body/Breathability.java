package bnjmn21.realrocket.api.celestial_body;

import bnjmn21.realrocket.util.serialization.EnumCodec;
import bnjmn21.realrocket.util.serialization.GetName;
import com.mojang.serialization.Codec;
import lombok.Getter;

public enum Breathability implements GetName {
    /**
     * Not breathable
     */
    Unbreathable("unbreathable"),

    /**
     * Toxic, but can be filtered to be breathable
     */
    Filterable("filterable"),

    /**
     * Just like earth
     */
    Breathable("breathable");

    @Getter
    final String name;

    Breathability(String name) {
        this.name = name;
    }

    public static final Codec<Breathability> CODEC = new EnumCodec<>(Breathability.class);
}

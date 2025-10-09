package bnjmn21.realrocket.api.celestial_body;

import bnjmn21.realrocket.util.serialization.EnumCodec;
import bnjmn21.realrocket.util.serialization.GetName;
import com.mojang.serialization.Codec;
import lombok.Getter;

public enum Atmosphere implements GetName {

    /**
     * None
     */
    None("none"),

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

    Atmosphere(String name) {
        this.name = name;
    }

    public static final Codec<Atmosphere> CODEC = new EnumCodec<>(Atmosphere.class);
}

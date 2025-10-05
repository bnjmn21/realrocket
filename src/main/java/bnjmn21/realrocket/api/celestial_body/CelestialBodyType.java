package bnjmn21.realrocket.api.celestial_body;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.stream.Stream;

public interface CelestialBodyType {
    CelestialBodyTypeCodec codec();
    Stream<ResourceKey<Level>> levels();
    Optional<Boolean> isDiscovered();
    int tier();
}

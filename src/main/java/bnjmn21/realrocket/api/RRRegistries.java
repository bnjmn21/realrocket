package bnjmn21.realrocket.api;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.api.celestial_body.CelestialBody;
import bnjmn21.realrocket.api.celestial_body.CelestialBodyType;
import bnjmn21.realrocket.api.celestial_body.CelestialBodyTypes;
import bnjmn21.realrocket.api.units.Units;
import bnjmn21.realrocket.api.units.BaseUnitType;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.RegistryBuilder;

public class RRRegistries {
    public static final RRRegistrate REGISTRATE = RRRegistrate.create(RealRocket.MOD_ID);
    public static final ResourceKey<Registry<BaseUnitType>> UNITS = REGISTRATE.makeRegistry("units", RegistryBuilder::new);
    public static final ResourceKey<Registry<CelestialBody>> CELESTIAL_BODIES = REGISTRATE.makeRegistry("celestial_body", RegistryBuilder::new);
    public static final ResourceKey<Registry<Codec<? extends CelestialBodyType>>> CELESTIAL_BODY_TYPES = REGISTRATE.makeRegistry("celestial_body_type", RegistryBuilder::new);

    static {
        Units.init();
        CelestialBodyTypes.init();
    }
}

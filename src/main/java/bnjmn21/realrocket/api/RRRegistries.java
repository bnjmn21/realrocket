package bnjmn21.realrocket.api;

import com.gregtechceu.gtceu.api.registry.GTRegistry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.api.celestial_body.CelestialBody;
import bnjmn21.realrocket.api.celestial_body.CelestialBodyTypeCodec;
import bnjmn21.realrocket.api.celestial_body.CelestialBodyTypes;
import bnjmn21.realrocket.api.rocket.Seat;
import bnjmn21.realrocket.api.units.Unit;
import bnjmn21.realrocket.api.units.Units;

public class RRRegistries {

    public static final RRRegistrate REGISTRATE = RRRegistrate.create(RealRocket.MOD_ID);
    public static final GTRegistry.RL<Unit<?>> UNITS = new GTRegistry.RL<>(RealRocket.id("unit"));
    public static final GTRegistry.RL<CelestialBodyTypeCodec> CELESTIAL_BODY_TYPES = new GTRegistry.RL<>(
            RealRocket.id("celestial_body_type"));
    public static final GTRegistry.RL<ResourceLocation> ENGINES = new GTRegistry.RL<>(RealRocket.id("engine"));
    public static final GTRegistry.RL<Seat> SEATS = new GTRegistry.RL<>(RealRocket.id("seat"));

    public static final ResourceKey<Registry<CelestialBody>> CELESTIAL_BODIES = REGISTRATE
            .makeDatapackRegistry("celestial_body", CelestialBody.CODEC, CelestialBody.CODEC);

    public static void init() {
        UNITS.unfreeze();
        CELESTIAL_BODY_TYPES.unfreeze();
        ENGINES.unfreeze();
        SEATS.unfreeze();

        Units.init();
        CelestialBodyTypes.init();
    }
}

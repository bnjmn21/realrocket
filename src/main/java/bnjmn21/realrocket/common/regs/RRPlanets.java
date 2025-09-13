package bnjmn21.realrocket.common.regs;

import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.api.planet.Planet;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.resources.ResourceLocation;

public class RRPlanets {
    public static final RegistryEntry<Planet> MOON = RRRegistries.REGISTRATE.planet("moon")
            .register();

    public static void init() {

    }
}

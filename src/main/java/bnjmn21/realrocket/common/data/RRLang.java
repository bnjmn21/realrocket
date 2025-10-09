package bnjmn21.realrocket.common.data;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.api.RRRegistrate;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;
import static net.minecraft.world.level.Level.OVERWORLD;

public class RRLang {

    public static final RRRegistrate.Translatable TOOLTIP_TIER = REGISTRATE.addRawLangTemplate("realrocket.rocket.tier",
            "Tier: %s", c -> c.withStyle(ChatFormatting.WHITE));

    public static final RRRegistrate.Translatable THRUST = REGISTRATE.addRawLangTemplate("realrocket.recipe.thrust",
            "Thrust: %sN", c -> c);

    public static final MutableComponent CANT_MIX_ENGINES = REGISTRATE.addRawLang("realrocket.rocket.cant_mix_engines",
            "Mixing engine types in one rocket is not allowed");
    public static final MutableComponent MULTIPLE_FLIGHT_COMPUTERS = REGISTRATE.addRawLang(
            "realrocket.rocket.multiple_flight_computers", "A rocket cannot have multiple flight computers");

    public static final String LEVEL_LANG = "realrocket.level";

    public static void init() {
        REGISTRATE.addLang(LEVEL_LANG, OVERWORLD.location(), "Earth");
        REGISTRATE.addLang(LEVEL_LANG, RealRocket.id("moon"), "Moon");
    }
}

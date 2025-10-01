package bnjmn21.realrocket.common.data;

import bnjmn21.realrocket.api.RRRegistrate;
import net.minecraft.ChatFormatting;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

public class RRLang {
    public static final RRRegistrate.Translatable TOOLTIP_TIER =
            REGISTRATE.addRawLangTemplate("realrocket.general.tier", "Tier: %s", c -> c.withStyle(ChatFormatting.WHITE));

    public static final RRRegistrate.Translatable THRUST =
            REGISTRATE.addRawLangTemplate("realrocket.recipe.thrust", "Thrust: %sN", c -> c);

    public static void init() {}
}

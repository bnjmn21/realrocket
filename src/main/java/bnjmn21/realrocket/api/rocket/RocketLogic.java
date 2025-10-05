package bnjmn21.realrocket.api.rocket;

import bnjmn21.realrocket.api.celestial_body.CelestialBodyTypes;
import bnjmn21.realrocket.api.celestial_body.VirtualLevelKey;
import bnjmn21.realrocket.api.celestial_body.VirtualLevels;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

public class RocketLogic {
    public static final ChatFormatting[] TIER_COLORS = new ChatFormatting[] {
            ChatFormatting.GRAY,
            ChatFormatting.DARK_PURPLE,
            ChatFormatting.DARK_BLUE,
            ChatFormatting.LIGHT_PURPLE,
            ChatFormatting.RED,
            ChatFormatting.DARK_AQUA,
            ChatFormatting.DARK_RED,
            ChatFormatting.GREEN,
            ChatFormatting.DARK_GREEN,
            ChatFormatting.YELLOW,
            ChatFormatting.DARK_BLUE,
            ChatFormatting.GOLD,
            ChatFormatting.AQUA,
    };

    public static int requiredRocketTier(Level level, VirtualLevelKey from, FlightTarget to) {
        if (to instanceof FlightTarget.VirtualLevel toLevel) {
            if (toLevel.level().equals(from)) {
                return 0;
            }
        }
        int tier = to.tier(level);
        if (from instanceof VirtualLevelKey.Planet fromSurface) {
            if (VirtualLevels.planetLevels(level).get(fromSurface).body() instanceof CelestialBodyTypes.Planet fromPlanet) {
                if (fromPlanet.tierDebuff()) {
                    tier++;
                }
            }
        }
        return tier;
    }
}

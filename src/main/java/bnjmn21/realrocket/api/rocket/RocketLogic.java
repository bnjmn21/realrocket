package bnjmn21.realrocket.api.rocket;

import bnjmn21.realrocket.api.celestial_body.CelestialBodyTypes;
import bnjmn21.realrocket.api.celestial_body.VirtualLevelKey;
import bnjmn21.realrocket.api.celestial_body.VirtualLevels;
import bnjmn21.realrocket.api.units.quantities.Acceleration;
import bnjmn21.realrocket.api.units.quantities.Distance;
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
        if (hasTierDebuff(level, from)) {
            tier++;
        }
        return tier;
    }

    public static boolean hasTierDebuff(Level level, VirtualLevelKey from) {
        if (from instanceof VirtualLevelKey.Planet fromSurface) {
            if (VirtualLevels.planetLevels(level).get(fromSurface).body() instanceof CelestialBodyTypes.Planet fromPlanet) {
                return fromPlanet.tierDebuff();
            }
        }
        return false;
    }

    static final Acceleration EARTH_GRAVITY = Acceleration.perSecondSq(new Distance.Meter(10));
    static final Acceleration MIN_TAKEOFF_ACCELERATION = Acceleration.perSecondSq(new Distance.Meter(1));
    static final Acceleration LANDING_DECELERATION = Acceleration.perSecondSq(new Distance.Meter(2));

    public static Acceleration liftoffAccelerationRequired(Level level, VirtualLevelKey from) {
        Acceleration gravity = EARTH_GRAVITY.mul(VirtualLevels.levels(level).get(from).gravity());
        return gravity.add(MIN_TAKEOFF_ACCELERATION);
    }

    public static Acceleration landingAccelerationRequired(Level level, VirtualLevelKey to) {
        Acceleration gravity = EARTH_GRAVITY.mul(VirtualLevels.levels(level).get(to).gravity());
        return gravity.add(LANDING_DECELERATION);
    }
}

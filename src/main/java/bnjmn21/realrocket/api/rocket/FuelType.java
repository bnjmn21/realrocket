package bnjmn21.realrocket.api.rocket;

import bnjmn21.realrocket.common.data.RRTags;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

/**
 * A fuel type, for example {@link FuelType#OXIDIZER}.
 */
public interface FuelType {
    LiquidFuel OXIDIZER = new LiquidFuel(RRTags.ROCKET_FUEL_OXIDIZER, RRTags.OXIDIZER_LANG.copy().withStyle(ChatFormatting.DARK_AQUA));
    LiquidFuel PROPELLANT = new LiquidFuel(RRTags.ROCKET_FUEL_PROPELLANT, RRTags.PROPELLANT_LANG.copy().withStyle(ChatFormatting.RED));

    MutableComponent name();

    class LiquidFuel implements FuelType {
        final TagKey<Fluid> tag;
        final MutableComponent name;

        public LiquidFuel(TagKey<Fluid> tag, MutableComponent name) {
            this.tag = tag;
            this.name = name;
        }

        @Override
        public MutableComponent name() {
            return name;
        }
    }
}

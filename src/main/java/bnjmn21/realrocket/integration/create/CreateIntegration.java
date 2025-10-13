package bnjmn21.realrocket.integration.create;

import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.api.rocket.Seat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;

public class CreateIntegration {

    public static boolean shouldRun() {
        return ModList.get().isLoaded("create");
    }

    public static void registerSeats() {
        for (DyeColor color : DyeColor.values()) {
            RRRegistries.SEATS.register(new ResourceLocation("create", color.getName() + "_seat"),
                    new Seat(new Vec3(0.5, 0.5, 0.5)));
        }
    }
}

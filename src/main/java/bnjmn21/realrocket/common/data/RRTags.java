package bnjmn21.realrocket.common.data;

import bnjmn21.realrocket.RealRocket;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

public class RRTags {
    public static final TagKey<Block>
            ROCKET_MOTOR = TagKey.create(Registries.BLOCK, RealRocket.id("rocket_motor")),
            TANK = TagKey.create(Registries.BLOCK, RealRocket.id("tank")),
            LAUNCHPAD_SUPPORT = TagKey.create(Registries.BLOCK, RealRocket.id("launchpad_support")),
            MINEABLE_WRENCH = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", "mineable/wrench"));

    public static final TagKey<Fluid>
            ROCKET_FUEL = TagKey.create(Registries.FLUID, RealRocket.id("rocket_fuel")),
            ROCKET_FUEL_OXIDIZER = TagKey.create(Registries.FLUID, RealRocket.id("rocket_fuel/oxidizer")),
            ROCKET_FUEL_PROPELLANT = TagKey.create(Registries.FLUID, RealRocket.id("rocket_fuel/propellant"));

    public static final MutableComponent
            OXIDIZER_LANG = REGISTRATE.addLang("tag", RealRocket.id("fluid/rocket_fuel/oxidizer"), "Oxidizer"),
            PROPELLANT_LANG = REGISTRATE.addLang("tag", RealRocket.id("fluid/rocket_fuel/propellant"), "Propellant");


    public static void init() {
        REGISTRATE.addFluidTags(prov -> {
            prov.addTag(ROCKET_FUEL).add(GTMaterials.Oxygen.getFluid(FluidStorageKeys.LIQUID));
            prov.addTag(ROCKET_FUEL_OXIDIZER).add(GTMaterials.Oxygen.getFluid(FluidStorageKeys.LIQUID));
        });
    }
}

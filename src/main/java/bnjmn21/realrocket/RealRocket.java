package bnjmn21.realrocket;

import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.common.content.Moon;
import bnjmn21.realrocket.common.content.RocketFuels;
import bnjmn21.realrocket.common.content.RocketParts;
import bnjmn21.realrocket.common.data.*;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(RealRocket.MOD_ID)
public class RealRocket {
    public static final String
            MOD_ID = "realrocket",
            NAME = "Realistic Rocketry";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public RealRocket() {
        RealRocket.init();
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(this);
        bus.addGenericListener(MachineDefinition.class, this::registerMachines);
        bus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
    }

    public static void init() {
        RRCreativeModeTabs.init();
        RRItems.init();
        RRBlocks.init();
        RRLang.init();
        RRTags.init();
        RocketFuels.init();
        RocketParts.init();
        Moon.init();

        RRRegistries.REGISTRATE.registerRegistrate();
    }

    @SubscribeEvent
    public void registerMaterials(MaterialEvent event) {
        RRMaterials.init();
    }

    @SubscribeEvent
    public void modifyMaterials(PostMaterialEvent event) {
        RRMaterials.modifyMaterials();
    }

    public void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        RRMachines.init();
    }

    public void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
        RRRecipeTypes.init();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}

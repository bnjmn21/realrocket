package bnjmn21.realrocket;

import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.common.content.Moon;
import bnjmn21.realrocket.common.regs.*;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
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
    }

    public static void init() {
        RRCreativeModeTabs.init();
        RRItems.init();
        RRPlanets.init();
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

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}

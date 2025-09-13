package bnjmn21.realrocket.common.regs;

import bnjmn21.realrocket.RealRocket;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

public class RRCreativeModeTabs {
    public static RegistryEntry<CreativeModeTab> RR = REGISTRATE.defaultCreativeTab(RealRocket.MOD_ID,
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(RealRocket.MOD_ID, REGISTRATE))
                            .icon(Items.COBBLESTONE::getDefaultInstance)
                            .title(Component.literal("Realistic Rocketry"))
                            .build())
            .register();

    public static void init() {

    }
}

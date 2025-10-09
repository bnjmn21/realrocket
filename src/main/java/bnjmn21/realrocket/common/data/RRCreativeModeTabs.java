package bnjmn21.realrocket.common.data;

import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;

import bnjmn21.realrocket.RealRocket;
import com.tterrag.registrate.util.entry.RegistryEntry;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

public class RRCreativeModeTabs {

    public static RegistryEntry<CreativeModeTab> PLANETS = REGISTRATE.defaultCreativeTab(
            "planets",
            builder -> builder
                    .displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator("planets", REGISTRATE))
                    .icon(Items.COBBLESTONE::getDefaultInstance)
                    .title(REGISTRATE.addLang("itemGroup", RealRocket.id("planets"), RealRocket.NAME + " Planets"))
                    .build())
            .register();

    public static RegistryEntry<CreativeModeTab> RR = REGISTRATE.defaultCreativeTab(
            "main",
            builder -> builder
                    .displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator("planets", REGISTRATE))
                    .icon(Items.COBBLESTONE::getDefaultInstance)
                    .title(REGISTRATE.addLang("itemGroup", RealRocket.id("main"), RealRocket.NAME))
                    .build())
            .register();

    public static void init() {}
}

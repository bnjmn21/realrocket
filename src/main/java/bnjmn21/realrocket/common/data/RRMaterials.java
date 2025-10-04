package bnjmn21.realrocket.common.data;

import bnjmn21.realrocket.common.content.RocketFuels;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.OreProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import static bnjmn21.realrocket.RealRocket.id;
import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;
import static bnjmn21.realrocket.common.RRValues.BASE_MATERIAL_HARVEST_LEVEL;

public class RRMaterials {
    public static void init() {
        RocketFuels.init();
    }

    public static void modifyMaterials() {
        GTMaterials.PotassiumFeldspar.setProperty(PropertyKey.ORE, new OreProperty());
    }

    //#region moon
    public static final Material Pyroxene = new Material.Builder(id("pyroxene"))
            .gem()
            .ore()
            .color(0x164C1A)
            .components(
                    GTMaterials.Magnesium, 1,
                    GTMaterials.Iron, 1,
                    GTMaterials.SiliconDioxide, 2,
                    GTMaterials.Oxygen, 2
            )
            .register();

    public static final Material Anorthite = new Material.Builder(id("anorthite"))
            .gem()
            .ore()
            .color(0xbdb87e)
            .components(
                    GTMaterials.Calcium, 1,
                    GTMaterials.Aluminium, 2,
                    GTMaterials.Silicon, 2,
                    GTMaterials.Oxygen, 8
            )
            .buildAndRegister();

    public static final Material MoonDust = new Material.Builder(id("moon"))
            .dust(BASE_MATERIAL_HARVEST_LEVEL)
            .color(0x909090)
            .buildAndRegister();

    public static final Material BasalticMoonDust = new Material.Builder(id("basaltic_moon"))
            .dust(BASE_MATERIAL_HARVEST_LEVEL)
            .color(0x5f5f5f)
            .buildAndRegister();

    public static final Material ElementalMoonDust = new Material.Builder(id("elemental_moon_dust"))
            .dust(BASE_MATERIAL_HARVEST_LEVEL + 2)
            .color(0x555555)
            .components(
                    GTMaterials.Iron, 4,
                    GTMaterials.Titanium, 2,
                    GTMaterials.Hydrogen, 4,
                    GTMaterials.Helium, 2,
                    GTMaterials.Tritium, 1
            )
            .buildAndRegister();

    static {
        REGISTRATE.addLang("material", RRMaterials.Pyroxene.getResourceLocation(), "Pyroxene");
        REGISTRATE.addLang("material", RRMaterials.MoonDust.getResourceLocation(), "Moon");
        REGISTRATE.addLang("material", RRMaterials.BasalticMoonDust.getResourceLocation(), "Basaltic Moon");
        REGISTRATE.addLang("material", RRMaterials.ElementalMoonDust.getResourceLocation(), "Elemental Moon");
        REGISTRATE.addLang("material", RRMaterials.Anorthite.getResourceLocation(), "Anorthite");
    }
    //#endregion

    //#region rocket fuels
    public static Material Kerosene;

    public static Material RP1;
    //#endregion
}

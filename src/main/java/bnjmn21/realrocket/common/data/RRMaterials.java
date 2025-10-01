package bnjmn21.realrocket.common.data;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.OreProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import static bnjmn21.realrocket.RealRocket.id;
import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;
import static bnjmn21.realrocket.common.RRValues.BASE_MATERIAL_HARVEST_LEVEL;
import static com.gregtechceu.gtceu.api.GTValues.*;

public class RRMaterials {
    public static void init() {}

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
    public static final Material Kerosene = new Material.Builder(id("kerosene"))
            .liquid(new FluidBuilder().customStill()).flags(MaterialFlags.FLAMMABLE, MaterialFlags.EXPLOSIVE)
            .buildAndRegister();

    public static final Material RP1 = new Material.Builder(id("rp1"))
            .liquid(new FluidBuilder().customStill()).flags(MaterialFlags.FLAMMABLE, MaterialFlags.EXPLOSIVE)
            .buildAndRegister();

    static {
        REGISTRATE.addLang("material", RRMaterials.Kerosene.getResourceLocation(), "Kerosene");
        REGISTRATE.addLang("material", RRMaterials.RP1.getResourceLocation(), "RP-1");
        REGISTRATE.addRecipes(prov -> {
            GTRecipeTypes.COMBUSTION_GENERATOR_FUELS.recipeBuilder(id("kerosene"))
                    .inputFluids(Kerosene.getFluid(1))
                    .duration(15)
                    .EUt(-V[LV])
                    .save(prov);
            GTRecipeTypes.COMBUSTION_GENERATOR_FUELS.recipeBuilder(id("rp1"))
                    .inputFluids(RP1.getFluid(1))
                    .duration(20)
                    .EUt(-V[LV])
                    .save(prov);
            GTRecipeTypes.DISTILLERY_RECIPES.recipeBuilder(id("kerosene"))
                    .inputFluids(GTMaterials.Diesel.getFluid(1000))
                    .outputFluids(Kerosene.getFluid(1000))
                    .circuitMeta(1)
                    .duration(200)
                    .EUt(V[MV])
                    .save(prov);
            GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder(id("rp1"))
                    .inputFluids(Kerosene.getFluid(2000))
                    .inputFluids(GTMaterials.Hydrogen.getFluid(100))
                    .outputFluids(RP1.getFluid(2000))
                    .outputFluids(GTMaterials.HydrogenSulfide.getFluid(50))
                    .duration(160)
                    .EUt(V[HV])
                    .save(prov);
        });
    }
    //#endregion
}

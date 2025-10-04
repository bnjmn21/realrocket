package bnjmn21.realrocket.common.content;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.api.sets.DustLayerBlockSet;
import bnjmn21.realrocket.api.sets.PlanetStoneBlockSet;
import bnjmn21.realrocket.common.data.RRCreativeModeTabs;
import bnjmn21.realrocket.common.data.RRMaterials;
import bnjmn21.realrocket.util.Lazy;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.data.worldgen.GTLayerPattern;
import com.gregtechceu.gtceu.api.data.worldgen.IWorldGenLayer;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.MapColor;

import java.util.Set;

import static bnjmn21.realrocket.RealRocket.id;
import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;
import static com.gregtechceu.gtceu.api.GTValues.*;

public class Moon {
    static {
        REGISTRATE.creativeModeTab(() -> RRCreativeModeTabs.PLANETS);
    }

    public static final PlanetStoneBlockSet MOON_STONE = new PlanetStoneBlockSet(
            REGISTRATE,
            "moon",
            "Moon",
            MapColor.COLOR_LIGHT_GRAY
    );

    public static final DustLayerBlockSet MOON_DUST = new DustLayerBlockSet(
            REGISTRATE,
            "moon_dust",
            "Moon Dust",
            MapColor.COLOR_LIGHT_GRAY,
            () -> RRMaterials.MoonDust
    );

    public static final DustLayerBlockSet BASALTIC_MOON_DUST = new DustLayerBlockSet(
            REGISTRATE,
            "basaltic_moon_dust",
            "Basaltic Moon Dust",
            MapColor.STONE,
            () -> RRMaterials.BasalticMoonDust
    );

    public static final Lazy<IWorldGenLayer> MOON_ORE_LAYER = REGISTRATE.addSimpleWorldGenLayer(
            "moon",
            () -> new BlockMatchTest(MOON_STONE.normal.get()), Set.of(RealRocket.id("moon"))
    );

    public static final TagKey<Block> MOON_ORE_REPLACEABLES = TagKey.create(Registries.BLOCK, RealRocket.id("moon_ore_replaceables"));
    public static RuleTest[] MOON_ORE_RULES = new RuleTest[]{new TagMatchTest(MOON_ORE_REPLACEABLES)};

    static {
        REGISTRATE.dimensionMarker(ResourceKey.create(Registries.DIMENSION, RealRocket.id("moon")), 1);

        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, tags -> {
            tags.addTag(MOON_ORE_REPLACEABLES).add(MOON_STONE.normal.get());
        });

        REGISTRATE.addOreVein("ilmenite_vein_moon", vein -> vein
                .clusterSize(30).density(0.4f).weight(40)
                .layer(MOON_ORE_LAYER.get())
                .heightRangeUniform(40, 80)
                .biomes("#realrocket:is_moon")
                .layeredVeinGenerator(gen -> gen
                        .withLayerPattern(() -> GTLayerPattern.builder(MOON_ORE_RULES)
                                .layer(l -> l.weight(1).mat(GTMaterials.Ilmenite).size(1, 3))
                                .layer(l -> l.weight(1).mat(GTMaterials.Olivine).mat(GTMaterials.PotassiumFeldspar).size(1, 1))
                                .build())
                )
        );
        REGISTRATE.addRawLang("gtceu.jei.ore_vein.ilmenite_vein_moon", "Moon Ilmenite Vein");
        REGISTRATE.addOreVein("anorthite_vein_moon", vein -> vein
                .clusterSize(30).density(0.3f).weight(40)
                .layer(MOON_ORE_LAYER.get())
                .heightRangeUniform(40, 60)
                .biomes("#realrocket:is_moon")
                .layeredVeinGenerator(gen -> gen
                        .withLayerPattern(() -> GTLayerPattern.builder(MOON_ORE_RULES)
                                .layer(l -> l.weight(1).mat(RRMaterials.Anorthite).size(1, 3))
                                .layer(l -> l.weight(1).mat(RRMaterials.Pyroxene).size(1, 1))
                                .build())
                )
        );
        REGISTRATE.addRawLang("gtceu.jei.ore_vein.anorthite_vein_moon", "Moon Anorthite Vein");
        REGISTRATE.addOreVein("monazite_vein_moon", vein -> vein
                .clusterSize(20).density(0.3f).weight(30)
                .layer(MOON_ORE_LAYER.get())
                .heightRangeUniform(30, 50)
                .biomes("#realrocket:is_moon")
                .layeredVeinGenerator(gen -> gen
                        .withLayerPattern(() -> GTLayerPattern.builder(MOON_ORE_RULES)
                                .layer(l -> l.weight(1).mat(GTMaterials.Monazite).size(1, 3))
                                .layer(l -> l.weight(1).mat(GTMaterials.Neodymium).mat(GTMaterials.Bastnasite).size(1, 1))
                                .build())
                )
        );
        REGISTRATE.addRawLang("gtceu.jei.ore_vein.monazite_vein_moon", "Moon Monazite Vein");
        REGISTRATE.addOreVein("uraninite_vein_moon", vein -> vein
                .clusterSize(30).density(0.2f).weight(20)
                .layer(MOON_ORE_LAYER.get())
                .heightRangeUniform(10, 40)
                .biomes("#realrocket:is_moon")
                .layeredVeinGenerator(gen -> gen
                        .withLayerPattern(() -> GTLayerPattern.builder(MOON_ORE_RULES)
                                .layer(l -> l.weight(1).mat(GTMaterials.Uraninite).size(1, 2))
                                .layer(l -> l.weight(1).mat(GTMaterials.Thorium).size(1, 1))
                                .build())
                )
        );
        REGISTRATE.addRawLang("gtceu.jei.ore_vein.uraninite_vein_moon", "Moon Uraninite Vein");

        REGISTRATE.addRecipes(cons -> {
            GTRecipeTypes.MACERATOR_RECIPES.recipeBuilder(id("moon_dust_block_maceration"))
                    .inputItems(Moon.MOON_DUST.block.asItem())
                    .outputItems(TagPrefix.dust, RRMaterials.MoonDust)
                    .EUt(VA[ULV])
                    .duration(20)
                    .save(cons);
            GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder(id("moon_dust_seperation"))
                    .inputItems(TagPrefix.dust, RRMaterials.MoonDust)
                    .chancedOutput(TagPrefix.dust, RRMaterials.ElementalMoonDust, 1500, 0)
                    .chancedOutput(TagPrefix.dust, GTMaterials.PotassiumFeldspar, 3000, 0)
                    .chancedOutput(TagPrefix.dust, RRMaterials.Pyroxene, 3000, 0)
                    .chancedOutput(TagPrefix.dust, GTMaterials.Stone, 2500, 0)
                    .EUt(VA[HV])
                    .duration(20*20)
                    .save(cons);
            GTRecipeTypes.MACERATOR_RECIPES.recipeBuilder(id("basaltic_moon_dust_block_maceration"))
                    .inputItems(Moon.BASALTIC_MOON_DUST.block.asItem())
                    .outputItems(TagPrefix.dust, RRMaterials.BasalticMoonDust)
                    .EUt(VA[ULV])
                    .duration(20)
                    .save(cons);
            GTRecipeTypes.CENTRIFUGE_RECIPES.recipeBuilder(id("basaltic_moon_dust_seperation"))
                    .inputItems(TagPrefix.dust, RRMaterials.BasalticMoonDust)
                    .chancedOutput(TagPrefix.dust, RRMaterials.ElementalMoonDust, 1500, 0)
                    .chancedOutput(TagPrefix.dust, GTMaterials.BasalticMineralSand, 2000, 0)
                    .chancedOutput(TagPrefix.dust, GTMaterials.PotassiumFeldspar, 3000, 0)
                    .chancedOutput(TagPrefix.dust, RRMaterials.Pyroxene, 3000, 0)
                    .chancedOutput(TagPrefix.dust, GTMaterials.Stone, 2500, 0)
                    .EUt(VA[HV])
                    .duration(20*20)
                    .save(cons);
        }); // TODO incorrect prob
    }

    public static void init() {
    }
}

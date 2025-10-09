package bnjmn21.realrocket.common.data;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.common.block.*;
import bnjmn21.realrocket.common.content.Models;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;
import static com.gregtechceu.gtceu.api.GTValues.ULV;
import static com.gregtechceu.gtceu.api.GTValues.VA;

@SuppressWarnings({ "unused", "Convert2MethodRef" })
public class RRBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> RRCreativeModeTabs.RR);
    }

    public static final BlockEntry<HalfTransparentBlock> STEEL_GRATING = REGISTRATE
            .block("steel_grating", HalfTransparentBlock::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Steel Grating")
            .properties(p -> p.noOcclusion().isValidSpawn((a, b, c, d) -> false))
            .blockstate(Models::orientableVerticalTranslucent)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, RRTags.MINEABLE_WRENCH)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> REINFORCED_CONCRETE = REGISTRATE.block("reinforced_concrete", Block::new)
            .initialProperties(() -> GTBlocks.LIGHT_CONCRETE.get())
            .lang("Reinforced Concrete")
            .blockstate(Models::randomRotated)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, RRTags.LAUNCHPAD_SUPPORT)
            .simpleItem()
            .register();

    public static final BlockEntry<RocketDesigner> ROCKET_DESIGNER = REGISTRATE
            .block("rocket_designer", RocketDesigner::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Rocket Designer")
            .properties(p -> p.noOcclusion())
            .blockstate((ctx, prov) -> {
                prov.horizontalBlock(ctx.getEntry(),
                        prov.models().getExistingFile(RealRocket.id("block/rocket_designer")));
            })
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, RRTags.MINEABLE_WRENCH)
            .simpleItem()
            .register();

    public static final BlockEntityEntry<RocketDesignerEntity> ROCKET_DESIGNER_BE = REGISTRATE
            .<RocketDesignerEntity>blockEntity(
                    "rocket_designer", (type, pos, state) -> new RocketDesignerEntity(type, pos, state))
            .validBlock(ROCKET_DESIGNER)
            .register();

    static {
        REGISTRATE.addRecipes(cons -> {
            GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(RealRocket.id("steel_grating"))
                    .inputItems(TagPrefix.rod, GTMaterials.Steel, 4)
                    .outputItems(STEEL_GRATING.asItem())
                    .circuitMeta(5)
                    .EUt(VA[ULV])
                    .duration(20 * 5)
                    .save(cons);

            GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(RealRocket.id("reinforced_concrete"))
                    .inputItems(TagPrefix.rod, GTMaterials.Steel)
                    .inputFluids(GTMaterials.Concrete.getFluid(144))
                    .outputItems(REINFORCED_CONCRETE.asItem())
                    .EUt(VA[ULV])
                    .duration(20 * 5)
                    .save(cons);
        });
    }

    public static BlockEntry<EngineBlock> BASIC_ROCKET_MOTOR;
    public static BlockEntry<TankBlock> BASIC_FUEL_TANK;
    public static BlockEntry<BoosterTankBlock> BOOSTER_TANK;
    public static BlockEntry<BoosterNozzleBlock> BOOSTER_NOZZLE;
    public static BlockEntry<SeatBlock> SEAT;

    public static void init() {}
}

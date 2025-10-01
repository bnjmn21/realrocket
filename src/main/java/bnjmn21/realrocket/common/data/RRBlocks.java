package bnjmn21.realrocket.common.data;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.api.rocket.FuelType;
import bnjmn21.realrocket.api.rocket.TankContainmentInfo;
import bnjmn21.realrocket.common.block.BoosterNozzleBlock;
import bnjmn21.realrocket.common.block.BoosterTankBlock;
import bnjmn21.realrocket.common.block.EngineBlock;
import bnjmn21.realrocket.common.block.TankBlock;
import bnjmn21.realrocket.common.content.Models;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;
import static com.gregtechceu.gtceu.api.GTValues.ULV;
import static com.gregtechceu.gtceu.api.GTValues.VA;

@SuppressWarnings({"unused", "Convert2MethodRef"})
public class RRBlocks {
    static {
        REGISTRATE.creativeModeTab(() -> RRCreativeModeTabs.RR);
    }

    public static final BlockEntry<HalfTransparentBlock> STEEL_GRATING = REGISTRATE.block("steel_grating", HalfTransparentBlock::new)
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

    // region rocket parts

    public static final BlockEntry<EngineBlock> BASIC_ROCKET_MOTOR = REGISTRATE.block("basic_rocket_motor", EngineBlock.factory(1,
                    () -> RRRecipeTypes.BASIC_ROCKET_MOTOR,
                    REGISTRATE.addLang("block", RealRocket.id("basic_rocket_motor"),
                            "lore", "“To every action, there is always opposed an equal reaction”")
                            .withStyle(ChatFormatting.GRAY))
            )
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Basic Rocket Motor")
            .properties(p -> p.noOcclusion())
            .blockstate(Models::simpleBlockWithExistingModel)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, RRTags.MINEABLE_WRENCH, RRTags.ROCKET_MOTOR)
            .simpleItem()
            .register();

    public static final BlockEntry<TankBlock> BASIC_FUEL_TANK = REGISTRATE.block("basic_fuel_tank", TankBlock.factory(DyeColor.LIGHT_GRAY,
                            new TankContainmentInfo(FuelType.OXIDIZER, 4000),
                            new TankContainmentInfo(FuelType.PROPELLANT, 2000)
                    ))
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .color(() -> () -> TankBlock::tintColor)
            .lang("Basic Fuel Tank")
            .blockstate(TankBlock::blockModel)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, RRTags.MINEABLE_WRENCH, RRTags.TANK)
            .item()
            .color(() -> () -> TankBlock::itemTintColor)
            .build()
            .register();

    public static final BlockEntry<BoosterTankBlock> BOOSTER_TANK = REGISTRATE.block("booster_tank", BoosterTankBlock::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Booster Tank")
            .blockstate((ctx, prov) -> {})
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, RRTags.MINEABLE_WRENCH)
            .simpleItem()
            .register();

    public static final BlockEntry<BoosterNozzleBlock> BOOSTER_NOZZLE = REGISTRATE.block("booster_nozzle", BoosterNozzleBlock::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Booster Nozzle")
            .properties(p -> p.noOcclusion())
            .blockstate(BoosterNozzleBlock::blockModel)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, RRTags.MINEABLE_WRENCH)
            .simpleItem()
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

    public static void init() {

    }

}

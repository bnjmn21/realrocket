package bnjmn21.realrocket.common.content;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.api.rocket.FuelType;
import bnjmn21.realrocket.api.rocket.TankContainmentInfo;
import bnjmn21.realrocket.common.block.BoosterNozzleBlock;
import bnjmn21.realrocket.common.block.BoosterTankBlock;
import bnjmn21.realrocket.common.block.EngineBlock;
import bnjmn21.realrocket.common.block.TankBlock;
import bnjmn21.realrocket.common.data.RRRecipeTypes;
import bnjmn21.realrocket.common.data.RRTags;
import net.minecraft.ChatFormatting;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;
import static bnjmn21.realrocket.common.data.RRBlocks.*;

public class RocketParts {
    public static void init() {
        BASIC_ROCKET_MOTOR = REGISTRATE.block("basic_rocket_motor", EngineBlock.factory(1,
                        () -> RRRecipeTypes.BASIC_ROCKET_MOTOR,
                        REGISTRATE.addLang("block", RealRocket.id("basic_rocket_motor"),
                                        "lore", "“To every action, there is always opposed an equal reaction”")
                                .withStyle(ChatFormatting.GRAY))
                )
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .lang("Basic Rocket Motor")
                .properties(BlockBehaviour.Properties::noOcclusion)
                .blockstate(Models::simpleBlockWithExistingModel)
                .tag(BlockTags.MINEABLE_WITH_PICKAXE, RRTags.MINEABLE_WRENCH, RRTags.ROCKET_MOTOR)
                .simpleItem()
                .register();
        REGISTRATE.simple(RRRegistries.ENGINES, () -> BASIC_ROCKET_MOTOR::get);

        BASIC_FUEL_TANK = REGISTRATE.block("basic_fuel_tank", TankBlock.factory(DyeColor.LIGHT_GRAY,
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

        BOOSTER_TANK = REGISTRATE.block("booster_tank", BoosterTankBlock::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .lang("Booster Tank")
                .blockstate((ctx, prov) -> {})
                .tag(BlockTags.MINEABLE_WITH_PICKAXE, RRTags.MINEABLE_WRENCH)
                .simpleItem()
                .register();

        BOOSTER_NOZZLE = REGISTRATE.block("booster_nozzle", BoosterNozzleBlock::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .lang("Booster Nozzle")
                .properties(BlockBehaviour.Properties::noOcclusion)
                .blockstate(BoosterNozzleBlock::blockModel)
                .tag(BlockTags.MINEABLE_WITH_PICKAXE, RRTags.MINEABLE_WRENCH)
                .simpleItem()
                .register();
    }
}

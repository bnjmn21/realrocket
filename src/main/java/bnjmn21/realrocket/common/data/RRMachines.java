package bnjmn21.realrocket.common.data;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.common.RRValues;
import bnjmn21.realrocket.common.machine.multiblock.LaunchPadMachine;
import bnjmn21.realrocket.util.PatternBuilder;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;

import java.util.ArrayList;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

@SuppressWarnings("unused")
public class RRMachines {

    public static final MultiblockMachineDefinition LAUNCH_PAD = REGISTRATE
            .multiblock("launch_pad", LaunchPadMachine::new)
            .langValue("Launch Pad")
            .rotationState(RotationState.NON_Y_AXIS)
            .allowFlip(false)
            .allowExtendedFacing(false)
            .tier(RRValues.V1)
            .pattern(def -> PatternBuilder.BlockPatternBuilder.create(
                    builder -> LaunchPadMachine.pattern(1, 3, 5, def.get(), builder)))
            .shapeInfos(def -> {
                ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
                shapeInfo.add(PatternBuilder.ShapeInfoBuilder.create(
                        builder -> LaunchPadMachine.pattern(1, 3, 5, def.get(), builder)
                ));
                shapeInfo.add(PatternBuilder.ShapeInfoBuilder.create(
                        builder -> LaunchPadMachine.pattern(2, 5, 8, def.get(), builder)
                ));
                shapeInfo.add(PatternBuilder.ShapeInfoBuilder.create(
                        builder -> LaunchPadMachine.pattern(3, 7, 11, def.get(), builder)
                ));
                shapeInfo.add(PatternBuilder.ShapeInfoBuilder.create(
                        builder -> LaunchPadMachine.pattern(4, 9, 15, def.get(), builder)
                ));
                return shapeInfo;
            })
            .workableCasingModel(
                    RealRocket.id("block/reinforced_concrete"),
                    GTCEu.id("block/multiblock/assembly_line")
            )
            .register();

//    public static final MachineDefinition BASIC_ROCKET_MOTOR = REGISTRATE.machine()

    public static void init() {
        LaunchPadMachine.init();
    }
}

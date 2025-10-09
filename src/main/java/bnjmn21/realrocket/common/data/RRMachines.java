package bnjmn21.realrocket.common.data;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.common.RRValues;
import bnjmn21.realrocket.common.machine.multiblock.LaunchPadMachine;
import bnjmn21.realrocket.util.PatternBuilder;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.data.recipe.misc.MetaTileEntityLoader;

import java.util.ArrayList;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.registerSimpleMachines;
import static com.gregtechceu.gtceu.data.recipe.GTCraftingComponents.*;

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
                        builder -> LaunchPadMachine.pattern(1, 3, 5, def.get(), builder)));
                shapeInfo.add(PatternBuilder.ShapeInfoBuilder.create(
                        builder -> LaunchPadMachine.pattern(2, 5, 8, def.get(), builder)));
                shapeInfo.add(PatternBuilder.ShapeInfoBuilder.create(
                        builder -> LaunchPadMachine.pattern(3, 7, 11, def.get(), builder)));
                shapeInfo.add(PatternBuilder.ShapeInfoBuilder.create(
                        builder -> LaunchPadMachine.pattern(4, 9, 15, def.get(), builder)));
                return shapeInfo;
            })
            .workableCasingModel(
                    RealRocket.id("block/reinforced_concrete"),
                    GTCEu.id("block/multiblock/assembly_line"))
            .register();

    public static final MachineDefinition[] PRINTER = registerSimpleMachines(REGISTRATE, "printer",
            RRRecipeTypes.PRINTER);

    static {
        REGISTRATE.addRawLang("realrocket.machine.lv_printer.tooltip", "§7Selectively melting materials");
        REGISTRATE.addRawLang("realrocket.machine.mv_printer.tooltip", "§7Selectively melting materials");
        REGISTRATE.addRawLang("realrocket.machine.hv_printer.tooltip", "§7Selectively melting materials");
        REGISTRATE.addRawLang("realrocket.machine.ev_printer.tooltip", "§7Selectively melting materials");
        REGISTRATE.addRawLang("realrocket.machine.iv_printer.tooltip", "§7Metal annihilator 1000");
        REGISTRATE.addRawLang("realrocket.machine.luv_printer.tooltip", "§7Metal annihilator 2000");
        REGISTRATE.addRawLang("realrocket.machine.zpm_printer.tooltip", "§7Metal annihilator 3000");
        REGISTRATE.addRawLang("realrocket.machine.uv_printer.tooltip", "§7nm-precision laser");
        REGISTRATE.addRecipes(prov -> {
            MetaTileEntityLoader.registerMachineRecipe(prov, PRINTER, "MEM", "BHG", "CCW",
                    'M', MOTOR, 'E', EMITTER, 'B', CONVEYOR, 'H', HULL,
                    'G', GTBlocks.CASING_TEMPERED_GLASS.asItem(), 'C', CIRCUIT, 'W', CABLE);
        });
    }

    public static void init() {
        LaunchPadMachine.init();
    }
}

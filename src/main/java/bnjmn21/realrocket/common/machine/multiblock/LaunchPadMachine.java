package bnjmn21.realrocket.common.machine.multiblock;

import com.gregtechceu.gtceu.api.block.IMachineBlock;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.BlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import bnjmn21.realrocket.common.data.RRBlocks;
import bnjmn21.realrocket.common.data.RRTags;
import bnjmn21.realrocket.util.PatternBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

public class LaunchPadMachine extends MultiblockControllerMachine implements IDisplayUIMachine {

    public static final MutableComponent INVALID_STRUCTURE_TIP, MODE, MODE_BUILD, MODE_LAUNCH;
    static {
        REGISTRATE.setLangPrefix("realrocket.multiblock.launch_pad");
        INVALID_STRUCTURE_TIP = REGISTRATE.addPrefixedLang("invalid_structure_tip",
                "Note: the underside of the launch pad must be clear of blocks, including grass, flowers, etc.");
        MODE = REGISTRATE.addPrefixedLang("mode", "Mode");
        MODE_BUILD = REGISTRATE.addPrefixedLang("mode.build", "BUILD")
                .withStyle(ChatFormatting.YELLOW)
                .withStyle(ChatFormatting.ITALIC);
        MODE_LAUNCH = REGISTRATE.addPrefixedLang("mode.launch", "LAUNCH")
                .withStyle(ChatFormatting.GREEN)
                .withStyle(ChatFormatting.ITALIC);
    }

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(LaunchPadMachine.class,
            MultiblockControllerMachine.MANAGED_FIELD_HOLDER);

    public static final int MIN_SIZE = 3;
    public static final int MAX_SIZE = 15;
    public static final int MIN_HEIGHT = 4;
    public static final int MAX_HEIGHT = 31;

    @Persisted
    protected int halfXSize = 0, ySize = 0, height = 0;
    @Persisted
    protected Mode launchMode = Mode.Build;

    protected enum Mode {
        Build,
        Launch
    }

    public LaunchPadMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        if (!this.isFormed) {
            MutableComponent base = Component.translatable("gtceu.multiblock.invalid_structure")
                    .withStyle(ChatFormatting.RED);
            Component hover = Component.translatable("gtceu.multiblock.invalid_structure.tooltip")
                    .withStyle(ChatFormatting.GRAY);
            textList.add(base
                    .withStyle(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover))));
            textList.add(INVALID_STRUCTURE_TIP.withStyle(ChatFormatting.GRAY));
            return;
        }

        switch (this.launchMode) {
            case Build -> textList.add(Component.empty().append(MODE).append(": ").append(MODE_BUILD));
            case Launch -> textList.add(Component.empty().append(MODE).append(": ").append(MODE_LAUNCH));
        }

        // textList.add(ComponentPanelWidget.withButton(
        // REGISTRATE.addPrefixedLang("build", "")
        // .withStyle(ChatFormatting.GREEN), "build_rocket"));
    }

    /**
     * Determines structure size ({@code halfXSize}, {@code ySize}, {@code height})
     *
     * @return whether the structure passed basic checks
     */
    boolean updateStructureDimensions() {
        Level world = getLevel();

        if (world == null) {
            return false;
        }

        Direction front = getFrontFacing();
        Direction back = front.getOpposite();
        Direction left = front.getCounterClockWise();

        // search for blocks left of the controller to get x size,
        // then search backwards to get y size,
        // then go to the tower and search up to get height

        // get the first steel grating block behind the controller
        BlockPos.MutableBlockPos currentPos = getPos().relative(back).relative(left).mutable();

        final int halfMaxSize = (MAX_SIZE - 1) / 2;
        final int halfMinSize = (MIN_SIZE - 1) / 2;
        int halfXSize = 0;
        for (int i = 0; i < halfMaxSize; i++) {
            if (!isBuildSurface(world.getBlockState(currentPos))) {
                break;
            }
            halfXSize += 1;
            currentPos.move(left);
        }
        if (halfXSize < halfMinSize) {
            return false;
        }

        currentPos = getPos().relative(back).mutable();
        int ySize = 0;
        for (int i = 0; i < MAX_SIZE; i++) {
            if (!isBuildSurface(world.getBlockState(currentPos))) {
                break;
            }
            ySize += 1;
            currentPos.move(back);
        }
        if (ySize < MIN_SIZE) {
            return false;
        }

        // go to the tower
        currentPos = getPos().relative(back, ySize + 1).relative(Direction.UP).mutable();
        int height = 0;
        for (int i = 0; i < MAX_HEIGHT; i++) {
            if (!isTowerBlock(world.getBlockState(currentPos))) {
                break;
            }
            height += 1;
            currentPos.move(Direction.UP);
        }
        if (height < MIN_HEIGHT) {
            return false;
        }

        this.halfXSize = halfXSize;
        this.ySize = ySize;
        this.height = height;

        return true;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isBuildSurface(BlockState block) {
        return block.is(RRBlocks.STEEL_GRATING.get());
    }

    public static boolean isTowerBlock(BlockState block) {
        return block.is(Objects.requireNonNull(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.StainlessSteel)));
    }

    @Override
    public BlockPattern getPattern() {
        if (!updateStructureDimensions() || halfXSize == 0 || ySize == 0 || height == 0) {
            // return the default structure, even if there is no valid size found.
            // this means auto-build will still work, and prevents terminal crashes.
            this.halfXSize = (MIN_SIZE - 1) / 2;
            this.ySize = MIN_SIZE;
            this.height = MIN_HEIGHT;
        }

        return PatternBuilder.BlockPatternBuilder
                .create(builder -> pattern(this.halfXSize, this.ySize, this.height, getDefinition().get(), builder));
    }

    public static void pattern(int halfXSize, int ySize, int height, IMachineBlock ctrlBlock, PatternBuilder builder) {
        final int structureWidth = halfXSize * 2 + 3;
        final int halfStructureWidth = halfXSize + 1;
        final int buildSurfaceWidth = halfXSize * 2 + 1;
        final int structureHeight = height + 2;

        final String air = "A";
        final String concrete = "C";
        final String controller = "M";
        final String buildSurface = "S";
        final String waterInput = "W";
        final String pipeBlock = "P";
        final String tower = "T";

        String any = " ".repeat(structureWidth);

        String[] frontAisle = new String[structureHeight];
        String[] centerAisle = new String[structureHeight];
        String[] backAisle = new String[structureHeight];

        Arrays.fill(frontAisle, any);
        Arrays.fill(centerAisle, any);
        Arrays.fill(backAisle, " ".repeat(halfStructureWidth) + tower + " ".repeat(halfStructureWidth));

        frontAisle[0] = concrete.repeat(structureWidth);
        frontAisle[1] = concrete.repeat(halfStructureWidth) + controller + concrete.repeat(halfStructureWidth);
        centerAisle[0] = air.repeat(structureWidth);
        centerAisle[1] = concrete + buildSurface.repeat(buildSurfaceWidth) + concrete;
        backAisle[0] = concrete.repeat(structureWidth);
        backAisle[1] = waterInput + concrete.repeat(buildSurfaceWidth) + waterInput;
        backAisle[2] = pipeBlock + " ".repeat(halfXSize) + tower + " ".repeat(halfXSize) + pipeBlock;

        builder.aisle(backAisle)
                .repeatedAisle(ySize, centerAisle)
                .aisle(frontAisle)
                .where(' ', Predicates.any(), Blocks.AIR)
                .where(air, Predicates.air(), Blocks.AIR)
                .where(concrete, Predicates.blockTag(RRTags.LAUNCHPAD_SUPPORT), RRBlocks.REINFORCED_CONCRETE.get())
                .where(controller, Predicates.controller(Predicates.blocks(ctrlBlock)), ctrlBlock, Direction.SOUTH)
                .where(buildSurface, RRBlocks.STEEL_GRATING.get())
                .where(waterInput, Predicates.abilities(PartAbility.IMPORT_FLUIDS_1X),
                        GTMachines.FLUID_IMPORT_HATCH[0].get(), Direction.NORTH)
                .where(pipeBlock, GTBlocks.CASING_STEEL_PIPE.get())
                .where(tower, ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.StainlessSteel));
    }

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    public static void init() {}
}

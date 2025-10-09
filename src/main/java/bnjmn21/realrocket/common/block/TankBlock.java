package bnjmn21.realrocket.common.block;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.api.RRRegistrate;
import bnjmn21.realrocket.api.rocket.Tank;
import bnjmn21.realrocket.api.rocket.TankContainmentInfo;

import com.lowdragmc.lowdraglib.client.model.custommodel.ICTMPredicate;
import com.lowdragmc.lowdraglib.utils.FacadeBlockAndTintGetter;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

public class TankBlock extends Block implements ICTMPredicate, Tank {

    static final MutableComponent PAINTABLE_TOOLTIP = REGISTRATE
            .addRawLang("realrocket.general.tank.paintable", "Can be painted with Spray Cans")
            .withStyle(ChatFormatting.GRAY);
    static final MutableComponent CAN_HOLD_TOOLTIP = REGISTRATE.addRawLang("realrocket.general.tank.can_hold",
            "Can hold:");
    static final RRRegistrate.Translatable CAN_HOLD_ITEM = REGISTRATE
            .addRawLangTemplate("realrocket.general.tank.can_hold_item", "- %1$s: %2$s mB", c -> c);
    static final Property<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);

    final DyeColor defaultColor;
    final TankContainmentInfo[] canHold;

    protected TankBlock(Properties properties, DyeColor defaultColor, TankContainmentInfo[] canHold) {
        super(properties);
        this.defaultColor = defaultColor;
        this.registerDefaultState(this.defaultBlockState().setValue(COLOR, defaultColor));
        this.canHold = canHold;
    }

    public static NonNullFunction<Properties, TankBlock> factory(DyeColor defaultColor,
                                                                 TankContainmentInfo... canHold) {
        return props -> new TankBlock(props, defaultColor, canHold);
    }

    @Override
    public TankContainmentInfo[] canHold() {
        return canHold;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip,
                                @NotNull TooltipFlag flag) {
        tooltip.add(PAINTABLE_TOOLTIP);
        tooltip.add(CAN_HOLD_TOOLTIP);
        for (TankContainmentInfo info : this.canHold) {
            tooltip.add(CAN_HOLD_ITEM.apply(info.type().name(), String.valueOf(info.amount())));
        }
    }

    @SuppressWarnings("unused")
    public static int tintColor(BlockState state, BlockAndTintGetter reader, BlockPos pos, int tintIndex) {
        if (tintIndex == 0) {
            for (Property<?> property : state.getProperties()) {
                if (property.getValueClass() == DyeColor.class && state.getValue(property) instanceof DyeColor color) {
                    return color.getMapColor().col;
                }
            }
        }
        return -1;
    }

    public static int itemTintColor(ItemStack itemStack, int tintIndex) {
        if (tintIndex == 0) {
            if (itemStack.getItem() instanceof BlockItem blockItem) {
                if (blockItem.getBlock() instanceof TankBlock tankBlock) {
                    return tankBlock.defaultColor.getMapColor().col;
                }
            }
        }
        return -1;
    }

    public static void blockModel(DataGenContext<Block, ? extends Block> ctx, RegistrateBlockstateProvider prov) {
        prov.simpleBlock(ctx.getEntry(), prov.models().getExistingFile(RealRocket.id("block/" + ctx.getName())));
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLOR);
    }

    // CTM should ignore paint color
    @Override
    public boolean isConnected(BlockAndTintGetter level, BlockState state, BlockPos pos, BlockState sourceState,
                               BlockPos sourcePos, Direction side) {
        return FacadeBlockAndTintGetter.getAppearance(sourceState, level, sourcePos, side, state, pos).is(this);
    }
}

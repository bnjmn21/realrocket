package bnjmn21.realrocket.common.block;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.api.rocket.Booster;
import bnjmn21.realrocket.common.data.RRLang;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

public class BoosterNozzleBlock extends Block implements Booster {
    static VoxelShape AABB = Block.box(1, 0, 1, 15, 16, 15);

    static final MutableComponent TOOLTIP =
            REGISTRATE.addLang("block", RealRocket.id("booster_nozzle"), "tooltip", "Needs 3 or more Booster Tanks placed on top")
                    .withStyle(ChatFormatting.GRAY);
    static final MutableComponent TOOLTIP_TIER_CONDITION =
            REGISTRATE.addLang("block", RealRocket.id("booster_nozzle"), "tooltip.tier_condition", "When rocket has 2 boosters:")
                    .withStyle(ChatFormatting.GRAY);

    public BoosterNozzleBlock(Properties properties) {
        super(properties);
    }

    public static void blockModel(DataGenContext<Block, ? extends Block> ctx, RegistrateBlockstateProvider prov) {
        prov.simpleBlock(ctx.getEntry(), prov.models().getExistingFile(RealRocket.id("block/" + ctx.getName())));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return AABB;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(TOOLTIP);
        tooltip.add(TOOLTIP_TIER_CONDITION);
        tooltip.add(RRLang.TOOLTIP_TIER.apply("+1").withStyle(ChatFormatting.ITALIC));
    }
}

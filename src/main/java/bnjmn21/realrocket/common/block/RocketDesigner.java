package bnjmn21.realrocket.common.block;

import bnjmn21.realrocket.common.data.RRBlocks;

import com.lowdragmc.lowdraglib.syncdata.blockentity.IAutoPersistBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RocketDesigner extends BaseEntityBlock {

    static DirectionProperty FACING = DirectionProperty.create("facing", Direction.NORTH, Direction.EAST,
            Direction.SOUTH, Direction.WEST);

    public RocketDesigner(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RocketDesignerEntity(RRBlocks.ROCKET_DESIGNER_BE.get(), pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        ItemStack itemStack = super.getCloneItemStack(level, pos, state);
        if (level.getBlockEntity(pos) instanceof IAutoPersistBlockEntity dropSave) {
            dropSave.saveManagedPersistentData(itemStack.getOrCreateTag(), true);
        }
        return itemStack;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        // noinspection OptionalGetWithoutIsPresent
        Direction dir = Arrays.stream(context.getNearestLookingDirections())
                .filter(d -> d == Direction.NORTH || d == Direction.EAST || d == Direction.SOUTH || d == Direction.WEST)
                .findFirst().get();
        return this.defaultBlockState().setValue(FACING, dir);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity player,
                            ItemStack pStack) {
        if (!pLevel.isClientSide) {
            if (pLevel.getBlockEntity(pPos) instanceof IAutoPersistBlockEntity dropSave) {
                CompoundTag tag = pStack.getTag();
                if (tag != null) {
                    dropSave.loadManagedPersistentData(tag);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                          InteractionHand hand, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof RocketDesignerEntity be) {
            be.onPlayerUse(player);
        } else {
            throw new RuntimeException("unreachable");
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}

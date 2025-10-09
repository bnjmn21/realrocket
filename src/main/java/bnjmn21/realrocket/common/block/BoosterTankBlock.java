package bnjmn21.realrocket.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import bnjmn21.realrocket.api.rocket.BlockMass;
import bnjmn21.realrocket.api.units.Mass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BoosterTankBlock extends Block implements BlockMass {

    static BooleanProperty AFT = BooleanProperty.create("aft");
    static BooleanProperty NOSE = BooleanProperty.create("nose");
    static BooleanProperty CONNECT_NORTH = BooleanProperty.create("connect_north");
    static BooleanProperty CONNECT_EAST = BooleanProperty.create("connect_east");
    static BooleanProperty CONNECT_SOUTH = BooleanProperty.create("connect_south");
    static BooleanProperty CONNECT_WEST = BooleanProperty.create("connect_west");
    static VoxelShape AABB = Block.box(1, 0, 1, 15, 16, 15);

    public BoosterTankBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(AFT, false)
                .setValue(NOSE, false)
                .setValue(CONNECT_NORTH, false)
                .setValue(CONNECT_EAST, false)
                .setValue(CONNECT_SOUTH, false)
                .setValue(CONNECT_WEST, false));
    }

    @Override
    public Mass getMass() {
        return Mass.Tonne.of(1);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        LevelReader level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return this.getBlockState(level, pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState updateShape(
                                           @NotNull BlockState state, @NotNull Direction direction,
                                           @NotNull BlockState neighborState,
                                           @NotNull LevelAccessor level, @NotNull BlockPos pos,
                                           @NotNull BlockPos neighborPos) {
        return this.getBlockState(level, pos);
    }

    public @NotNull BlockState getBlockState(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos) {
        BlockState state = this.defaultBlockState();
        state = state.setValue(NOSE, level.getBlockState(pos.relative(Direction.UP)).isAir());
        state = state.setValue(AFT, !level.getBlockState(pos.relative(Direction.DOWN)).is(this));
        if (state.getValue(NOSE) || state.getValue(AFT)) {
            state = state.setValue(CONNECT_NORTH, !level.getBlockState(pos.relative(Direction.NORTH)).isAir());
            state = state.setValue(CONNECT_EAST, !level.getBlockState(pos.relative(Direction.EAST)).isAir());
            state = state.setValue(CONNECT_SOUTH, !level.getBlockState(pos.relative(Direction.SOUTH)).isAir());
            state = state.setValue(CONNECT_WEST, !level.getBlockState(pos.relative(Direction.WEST)).isAir());
        }
        return state;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos,
                                        @NotNull CollisionContext context) {
        return AABB;
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AFT, NOSE, CONNECT_NORTH, CONNECT_EAST, CONNECT_SOUTH, CONNECT_WEST);
    }
}

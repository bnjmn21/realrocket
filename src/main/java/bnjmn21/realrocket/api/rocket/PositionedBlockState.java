package bnjmn21.realrocket.api.rocket;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.level.block.state.BlockState;

public record PositionedBlockState(BlockPos pos, BlockState state) {
    public static PositionedBlockState readFromTag(CompoundTag tag) {
        BlockPos pos = NbtUtils.readBlockPos(tag.getCompound("p"));
        @SuppressWarnings("deprecation") // ForgeRegistries has no `asLookup()`
        BlockState state = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tag.getCompound("s"));
        return new PositionedBlockState(pos, state);
    }

    public CompoundTag writeToTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("p", NbtUtils.writeBlockPos(this.pos));
        tag.put("s", NbtUtils.writeBlockState(this.state));
        return tag;
    }

    public void write(FriendlyByteBuf buf) {
        EntityDataSerializers.BLOCK_POS.write(buf, this.pos);
        EntityDataSerializers.BLOCK_STATE.write(buf, this.state);
    }

    public static PositionedBlockState read(FriendlyByteBuf buf) {
        BlockPos pos = EntityDataSerializers.BLOCK_POS.read(buf);
        BlockState state = EntityDataSerializers.BLOCK_STATE.read(buf);
        return new PositionedBlockState(pos, state);
    }

    public PositionedBlockState copy() {
        return new PositionedBlockState(this.pos, this.state);
    }
}

package bnjmn21.realrocket.common.data;

import bnjmn21.realrocket.api.rocket.PositionedBlockState;
import bnjmn21.realrocket.common.entity.RocketEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RREntityDataSerializers {
    public static final EntityDataSerializer<PositionedBlockState> POSITIONED_BLOCK_STATE = new EntityDataSerializer<>() {
        @Override
        public void write(FriendlyByteBuf buffer, PositionedBlockState value) {
            value.write(buffer);
        }

        @Override
        public PositionedBlockState read(FriendlyByteBuf buffer) {
            return PositionedBlockState.read(buffer);
        }

        @Override
        public PositionedBlockState copy(PositionedBlockState value) {
            return value.copy();
        }
    };

    public static final EntityDataSerializer<List<PositionedBlockState>> POSITIONED_BLOCK_STATE_LIST = new EntityDataSerializer<>() {
        @Override
        public void write(FriendlyByteBuf buffer, List<PositionedBlockState> value) {
            buffer.writeCollection(value, POSITIONED_BLOCK_STATE::write);
        }

        @Override
        public List<PositionedBlockState> read(FriendlyByteBuf buffer) {
            return buffer.readList(POSITIONED_BLOCK_STATE::read);
        }

        @Override
        public List<PositionedBlockState> copy(List<PositionedBlockState> value) {
            return new ArrayList<>(value);
        }
    };

    public static final EntityDataSerializer<RocketEntity.FlightPhase> FLIGHT_PHASE = new EntityDataSerializer<>() {
        @Override
        public void write(FriendlyByteBuf buffer, RocketEntity.FlightPhase value) {
            buffer.writeEnum(value);
        }

        @Override
        public RocketEntity.FlightPhase read(FriendlyByteBuf buffer) {
            return buffer.readEnum(RocketEntity.FlightPhase.class);
        }

        @Override
        public RocketEntity.FlightPhase copy(RocketEntity.FlightPhase value) {
            return value;
        }
    };

    public static void init() {
        EntityDataSerializers.registerSerializer(POSITIONED_BLOCK_STATE);
        EntityDataSerializers.registerSerializer(POSITIONED_BLOCK_STATE_LIST);
        EntityDataSerializers.registerSerializer(FLIGHT_PHASE);
    }
}

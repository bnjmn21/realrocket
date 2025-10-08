package bnjmn21.realrocket.api.rocket;

import bnjmn21.realrocket.api.units.Mass;
import bnjmn21.realrocket.common.data.RRLang;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;

@MethodsReturnNonnullByDefault
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
public class RocketBuilder {
    ArrayList<PositionedBlockState> blocks = new ArrayList<>();
    Mass mass = Mass.Kilogram.of(0);
    Vec3 cg = Vec3.ZERO;
    int engines = 0;
    Vec3 ct = Vec3.ZERO;
    @Nullable Engine engineType;
    BlockPos.MutableBlockPos aabbStart = new BlockPos.MutableBlockPos(0, 0, 0);
    BlockPos.MutableBlockPos aabbEnd = new BlockPos.MutableBlockPos(0, 0, 0);
    @Nullable FlightComputer flightComputer;
    ArrayList<Vec3> seats;

    RocketBuilder() {}

    public void addBlock(BlockPos pos, BlockState block) throws AddError {
        blocks.add(new PositionedBlockState(pos, block));
        updateAabb(pos);
        if (block.getBlock() instanceof BlockMass blockMass) {
            addMass(pos, blockMass.getMass());
        } else {
            addMass(pos, BlockMass.DEFAULT_MASS);
        }
        if (block.getBlock() instanceof Engine engine) {
            if (this.engineType == null) {
                this.engineType = engine;
            } else if (this.engineType != engine) {
                throw new AddError(RRLang.CANT_MIX_ENGINES);
            }
            addEngine(pos);
        }
        if (block.getBlock() instanceof FlightComputer computer) {
            if (this.flightComputer == null) {
                this.flightComputer = computer;
            } else {
                throw new AddError(RRLang.MULTIPLE_FLIGHT_COMPUTERS);
            }
        }
    }

    private void addMass(BlockPos pos, Mass mass) {
        if (Mass.Kilogram.get(this.mass) == 0) {
            this.mass = mass;
            this.cg = pos.getCenter();
        } else {
            double preMass = Mass.Kilogram.get(mass);
            this.mass = this.mass.add(mass);
            double postMassInv = 1.0 / Mass.Kilogram.get(this.mass);
            this.cg = this.cg.multiply(preMass, preMass, preMass)
                    .add(pos.getCenter())
                    .multiply(postMassInv, postMassInv, postMassInv);
        }
    }

    private void addEngine(BlockPos pos) {
        if (this.engines == 0) {
            this.engines = 1;
            this.ct = pos.getCenter();
        } else {
            double preEngines = this.engines;
            this.engines++;
            double postEnginesInv = 1.0 / this.engines;
            this.ct = this.ct.multiply(preEngines, preEngines, preEngines)
                    .add(pos.getCenter())
                    .multiply(postEnginesInv, postEnginesInv, postEnginesInv);
        }
    }

    private void updateAabb(BlockPos pos) {
        if (pos.getX() < this.aabbStart.getX()) {
            this.aabbStart.setX(pos.getX());
        }
        if (pos.getY() < this.aabbStart.getY()) {
            this.aabbStart.setY(pos.getY());
        }
        if (pos.getZ() < this.aabbStart.getZ()) {
            this.aabbStart.setZ(pos.getZ());
        }
        if (pos.getX() > this.aabbEnd.getX()) {
            this.aabbEnd.setX(pos.getX());
        }
        if (pos.getY() > this.aabbEnd.getY()) {
            this.aabbEnd.setY(pos.getY());
        }
        if (pos.getZ() > this.aabbEnd.getZ()) {
            this.aabbEnd.setZ(pos.getZ());
        }
    }

    public static class AddError extends RuntimeException {
        public final Component translation;

        public AddError(Component message) {
            super(message.toString());
            this.translation = message;
        }

        public Component component() {
            return this.translation;
        }
    }
}

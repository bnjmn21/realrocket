package bnjmn21.realrocket.api.rocket;

import bnjmn21.realrocket.api.units.quantities.Mass;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class RocketBuilder {
    Mass mass;
    Vec3 cg;
    int engines;
    Vec3 ct;
    Engine engineType;
    ArrayList<PositionedBlockState> blocks;

    RocketBuilder() {

    }
}

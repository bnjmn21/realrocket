package bnjmn21.realrocket.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;

/**
 * A density function that generates random craters.
 * Multiple of these can be layered with different parameters to create moon-like landscapes.<br>
 * Note that this is an expensive function,
 * so adding a {@code "minecraft:flatcache"} and {@code "minecraft:interpolated"}
 * is recommended.<br>
 * Minimum: {@code -sqrt(maxRadius) * depthFactor}<br>
 * Maximum: {@code 0}
 *
 * @param minRadius minimum radius of the craters
 * @param maxRadius maximum radius of the craters
 * @param distance average distance between craters
 * @param depthFactor additional factor
 * @param noise the noise used for determining crater position and radius
 */
public record RandomCraters(
        double minRadius,
        double maxRadius,
        int distance,
        double depthFactor,
        NoiseHolder noise
) implements DensityFunction {
    private static final MapCodec<RandomCraters> DATA_CODEC =
            RecordCodecBuilder.mapCodec((instance) -> instance
                    .group(
                            Codec.DOUBLE.fieldOf("min_radius")
                                    .forGetter(RandomCraters::minRadius),
                            Codec.DOUBLE.fieldOf("max_radius")
                                    .forGetter(RandomCraters::maxRadius),
                            Codec.INT.fieldOf("distance")
                                    .forGetter(RandomCraters::distance),
                            Codec.DOUBLE.fieldOf("depth_factor")
                                    .forGetter(RandomCraters::depthFactor),
                            NoiseHolder.CODEC.fieldOf("noise")
                                    .forGetter(RandomCraters::noise)
                    ).apply(instance, RandomCraters::new));
    public static final KeyDispatchDataCodec<RandomCraters> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

    private double craterXOffset(double craterX, double craterZ) {
        return this.noise.getValue(craterX, 0, craterZ);
    }

    private double craterZOffset(double craterX, double craterZ) {
        return this.noise.getValue(craterX, 10000, craterZ);
    }

    private double craterRadius(double craterX, double craterZ) {
        return this.noise.getValue(craterX, 20000, craterZ);
    }

    private double valueForCrater(double posX, double posZ, double craterX, double craterZ) {
        final double halfDistance = this.distance / 2.0;
        final double randomizedCraterX = craterX + (craterXOffset(craterX, craterZ) * halfDistance);
        final double randomizedCraterZ = craterZ + (craterZOffset(craterX, craterZ) * halfDistance);
        final double distSq = Mth.lengthSquared(posX - randomizedCraterX, posZ - randomizedCraterZ);
        final double radius = Math.pow(craterRadius(craterX, craterZ), 2) * (this.maxRadius - this.minRadius) + this.minRadius;
        return Math.min(distSq * (1.0 / Math.pow(radius, 2)) - 1.0, 0) * Math.sqrt(radius);
    }

    @Override
    public double compute(@NotNull FunctionContext context) {
        final double halfDistance = this.distance / 2.0;
        final double x = context.blockX();
        final double z = context.blockZ();
        final double craterDistanceX = Mth.positiveModulo(x, this.distance);
        final double craterDistanceZ = Mth.positiveModulo(z, this.distance);
        final double craterX = x - craterDistanceX + halfDistance;
        final double craterZ = z - craterDistanceZ + halfDistance;
        double res = valueForCrater(x, z, craterX, craterZ);
        res = Math.min(res, valueForCrater(x, z, craterX + this.distance, craterZ));
        res = Math.min(res, valueForCrater(x, z, craterX, craterZ + this.distance));
        res = Math.min(res, valueForCrater(x, z, craterX + this.distance, craterZ + this.distance));
        return res * this.depthFactor;
    }

    @Override
    public void fillArray(double @NotNull [] array, ContextProvider contextProvider) {
        contextProvider.fillAllDirectly(array, this);
    }

    @Override
    public @NotNull DensityFunction mapAll(Visitor visitor) {
        return visitor.apply(new RandomCraters(
                this.minRadius,
                this.maxRadius,
                this.distance,
                this.depthFactor,
                visitor.visitNoise(this.noise)
        ));
    }

    @Override
    public double minValue() {
        return -(Math.sqrt(this.maxRadius) * this.depthFactor);
    }

    @Override
    public double maxValue() {
        return 0;
    }

    @Override
    public @NotNull KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}

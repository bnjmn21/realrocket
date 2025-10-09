package bnjmn21.realrocket.api.celestial_body;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.common.data.RRLang;

import java.util.Objects;

/**
 * A virtual level can be a planet {@link Planet}, or a space station {@link Space}.
 * <p>
 * </p>
 * Instead of creating a new level for every space station, we put all space stations into one "realrocket:space"
 * level. Each {@link Space} is a section of that level.
 * Planets are just normal levels.
 */
public interface VirtualLevelKey {

    ResourceKey<Level> SPACE = ResourceKey.create(Registries.DIMENSION, RealRocket.id("space"));

    boolean isIn(ResourceKey<Level> level, BlockPos pos);

    Component name();

    record Planet(ResourceKey<Level> level) implements VirtualLevelKey {

        @Override
        public boolean isIn(ResourceKey<Level> level, BlockPos pos) {
            return this.level.equals(level);
        }

        @Override
        public Component name() {
            return Component.translatable(level.location().toLanguageKey(RRLang.LEVEL_LANG));
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Planet planet)) return false;
            return Objects.equals(level, planet.level);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(level) + TYPE_HASH;
        }

        private static final int TYPE_HASH = Objects.hashCode("Planet");
    }

    record Space(int x, int z, int xSize, int zSize) implements VirtualLevelKey {

        @Override
        public boolean isIn(ResourceKey<Level> level, BlockPos pos) {
            if (!level.equals(SPACE)) {
                return false;
            }
            return pos.getX() >= this.x && pos.getZ() >= this.z && pos.getX() < this.x + this.xSize &&
                    pos.getZ() < this.z + this.zSize;
        }

        @Override
        public Component name() {
            return Component.literal("NYI!");
        }
    }
}

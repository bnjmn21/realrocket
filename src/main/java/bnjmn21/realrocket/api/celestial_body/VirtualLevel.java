package bnjmn21.realrocket.api.celestial_body;

public interface VirtualLevel {
    float gravity();

    record Planet(CelestialBody planet, VirtualLevelKey level) implements VirtualLevel {
        @Override
        public float gravity() {
            return this.planet.body().gravityOf(level);
        }
    }

    record Space(CelestialBody parent, int x1, int z1, int x2, int z2) implements VirtualLevel {
        @Override
        public float gravity() {
            return 0;
        }
    }
}

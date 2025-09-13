package bnjmn21.realrocket.api.planet;

public class Planet {
    public final PlanetProperties properties;

    public Planet() {
        this(PlanetProperties.getDefault());
    }

    public Planet(PlanetProperties properties) {
        this.properties = properties;
    }
}

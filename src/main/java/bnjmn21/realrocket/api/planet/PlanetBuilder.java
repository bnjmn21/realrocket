package bnjmn21.realrocket.api.planet;

import bnjmn21.realrocket.api.RRRegistries;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PlanetBuilder<P> extends AbstractBuilder<Planet, Planet, P, PlanetBuilder<P>> {
    private final Supplier<Planet> factory;

    public PlanetBuilder(Registrate owner, P parent, String name, BuilderCallback callback, Supplier<Planet> factory) {
        super(owner, parent, name, callback, RRRegistries.PLANETS);
        this.factory = factory;
    }


    @Override
    protected @NonnullType @NotNull Planet createEntry() {
        return factory.get();
    }
}

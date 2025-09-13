package bnjmn21.realrocket.api.units;

import bnjmn21.realrocket.api.RRRegistries;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonnullType;
import org.jetbrains.annotations.NotNull;

public class BaseUnitBuilder<P> extends AbstractBuilder<BaseUnitType, BaseUnitType, P, BaseUnitBuilder<P>> {
    final BaseUnitType value;

    public BaseUnitBuilder(Registrate owner, P parent, String name, BuilderCallback callback, BaseUnitType value) {
        super(owner, parent, name, callback, RRRegistries.UNITS);
        this.value = value;
    }

    @Override
    protected @NonnullType @NotNull BaseUnitType createEntry() {
        return this.value;
    }
}

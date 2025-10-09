package bnjmn21.realrocket.api.units;

@SuppressWarnings("unused")
public class Mass extends Quantity<Mass> {

    public static final Runnable INIT = Unit.mkInit(Mass.class);
    public static final Unit<Mass> Kilogram = Unit.base(Mass::new, "kilogram"),
            Tonne = Unit.factor(1e3, Mass::new, "tonne");

    Mass(double value) {
        super(value);
    }

    @Override
    Mass copy() {
        return new Mass(this.value);
    }
}

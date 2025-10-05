package bnjmn21.realrocket.api.units;

import bnjmn21.realrocket.api.RRRegistries;
import bnjmn21.realrocket.api.units.quantities.*;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import net.minecraftforge.fml.ModLoader;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

@SuppressWarnings("unused")
public class Units {
    public static BaseUnitType SECOND;
    public static BaseUnitType METER;
    public static BaseUnitType KELVIN;
    public static BaseUnitType SIEVERT;
    public static BaseUnitType KILOGRAM;
    public static BaseUnitType NEWTON;

    public static void register() {
        SECOND = REGISTRATE.baseUnit("second", Time.Second::new, Time.Second.class);
        REGISTRATE.baseUnit("minute", Time.Minute::new, Time.Minute.class);
        REGISTRATE.baseUnit("hour", Time.Hour::new, Time.Hour.class);
        REGISTRATE.baseUnit("day", Time.Day::new, Time.Day.class);
        REGISTRATE.baseUnit("year", Time.Year::new, Time.Year.class);

        METER = REGISTRATE.baseUnit("meter", Distance.Meter::new, Distance.Meter.class);
        REGISTRATE.baseUnit("kilometer", Distance.Kilometer::new, Distance.Kilometer.class);
        REGISTRATE.baseUnit("au", Distance.AU::new, Distance.AU.class);
        REGISTRATE.baseUnit("light_year", Distance.LightYear::new, Distance.LightYear.class);
        REGISTRATE.baseUnit("parsec", Distance.Parsec::new, Distance.Parsec.class);

        KELVIN = REGISTRATE.baseUnit("kelvin", Temperature.Kelvin::new, Temperature.Kelvin.class);
        REGISTRATE.baseUnit("celsius", Temperature.Celsius::new, Temperature.Celsius.class);
        REGISTRATE.baseUnit("fahrenheit", Temperature.Fahrenheit::new, Temperature.Fahrenheit.class);

        SIEVERT = REGISTRATE.baseUnit("sievert", Dose.Sievert::new, Dose.Sievert.class);
        REGISTRATE.baseUnit("millisievert", Dose.Millisievert::new, Dose.Millisievert.class);
        REGISTRATE.baseUnit("microsievert", Dose.Microsievert::new, Dose.Microsievert.class);

        KILOGRAM = REGISTRATE.baseUnit("kilogram", Mass.Kilogram::new, Mass.Kilogram.class);
        REGISTRATE.baseUnit("tonne", Mass.Tonne::new, Mass.Tonne.class);

        NEWTON = REGISTRATE.baseUnit("newton", Force.Newton::new, Force.Newton.class);
        REGISTRATE.baseUnit("kilonewton", Force.Kilonewton::new, Force.Kilonewton.class);
    }

    public static void init() {
        ModLoader.get().postEvent(new GTCEuAPI.RegisterEvent<>(RRRegistries.UNITS, BaseUnitType.class));
        register();

        RRRegistries.UNITS.freeze();
    }
}

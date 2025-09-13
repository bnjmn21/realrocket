package bnjmn21.realrocket.api.units;

import bnjmn21.realrocket.api.units.quantities.Distance;
import bnjmn21.realrocket.api.units.quantities.Dose;
import bnjmn21.realrocket.api.units.quantities.Temperature;
import bnjmn21.realrocket.api.units.quantities.Time;
import com.tterrag.registrate.util.entry.RegistryEntry;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

@SuppressWarnings("unused")
public class Units {
    public static void init() {}

    public static final RegistryEntry<BaseUnitType> SECOND = REGISTRATE.baseUnit("second", new BaseUnitType(Time.Second::new, Time.Second.class)).register();
    public static final RegistryEntry<BaseUnitType> MINUTE = REGISTRATE.baseUnit("minute", new BaseUnitType(Time.Minute::new, Time.Minute.class)).register();
    public static final RegistryEntry<BaseUnitType> HOUR = REGISTRATE.baseUnit("hour", new BaseUnitType(Time.Hour::new, Time.Hour.class)).register();
    public static final RegistryEntry<BaseUnitType> DAY = REGISTRATE.baseUnit("day", new BaseUnitType(Time.Day::new, Time.Day.class)).register();
    public static final RegistryEntry<BaseUnitType> YEAR = REGISTRATE.baseUnit("year", new BaseUnitType(Time.Year::new, Time.Year.class)).register();

    public static final RegistryEntry<BaseUnitType> METER = REGISTRATE.baseUnit("meter", new BaseUnitType(Distance.Meter::new, Distance.Meter.class)).register();
    public static final RegistryEntry<BaseUnitType> KILOMETER = REGISTRATE.baseUnit("kilometer", new BaseUnitType(Distance.Kilometer::new, Distance.Kilometer.class)).register();
    public static final RegistryEntry<BaseUnitType> AU = REGISTRATE.baseUnit("au", new BaseUnitType(Distance.AU::new, Distance.AU.class)).register();
    public static final RegistryEntry<BaseUnitType> LIGHT_YEAR = REGISTRATE.baseUnit("light_year", new BaseUnitType(Distance.LightYear::new, Distance.LightYear.class)).register();
    public static final RegistryEntry<BaseUnitType> PARSEC = REGISTRATE.baseUnit("parsec", new BaseUnitType(Distance.Parsec::new, Distance.Parsec.class)).register();

    public static final RegistryEntry<BaseUnitType> KELVIN = REGISTRATE.baseUnit("kelvin", new BaseUnitType(Temperature.Kelvin::new, Temperature.Kelvin.class)).register();
    public static final RegistryEntry<BaseUnitType> CELSIUS = REGISTRATE.baseUnit("celsius", new BaseUnitType(Temperature.Celsius::new, Temperature.Celsius.class)).register();
    public static final RegistryEntry<BaseUnitType> FAHRENHEIT = REGISTRATE.baseUnit("fahrenheit", new BaseUnitType(Temperature.Fahrenheit::new, Temperature.Fahrenheit.class)).register();

    public static final RegistryEntry<BaseUnitType> SIEVERT = REGISTRATE.baseUnit("sievert", new BaseUnitType(Dose.Sievert::new, Dose.Sievert.class)).register();
    public static final RegistryEntry<BaseUnitType> MILLISIEVERT = REGISTRATE.baseUnit("millisievert", new BaseUnitType(Dose.Millisievert::new, Dose.Millisievert.class)).register();
    public static final RegistryEntry<BaseUnitType> MICROSIEVERT = REGISTRATE.baseUnit("microsievert", new BaseUnitType(Dose.Microsievert::new, Dose.Microsievert.class)).register();
}

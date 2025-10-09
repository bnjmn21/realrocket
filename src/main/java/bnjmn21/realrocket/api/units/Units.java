package bnjmn21.realrocket.api.units;

import bnjmn21.realrocket.api.RRRegistries;

public class Units {

    public static void init() {
        Distance.INIT.run();
        Dose.INIT.run();
        Force.INIT.run();
        Mass.INIT.run();
        Time.INIT.run();
        Temperature.INIT.run();
        RRRegistries.UNITS.freeze();
    }
}

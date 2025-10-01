package bnjmn21.realrocket.integration.xei;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

public class XeiPlugin {
    public static void register(XeiRegistry registry) {
        REGISTRATE.registerXei(registry);
    }
}

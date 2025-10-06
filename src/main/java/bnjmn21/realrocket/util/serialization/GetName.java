package bnjmn21.realrocket.util.serialization;

import java.util.Arrays;

public interface GetName {
    String getName();

    static <T extends GetName> T fromName(Class<T> clazz, String value) {
        for (T constant : clazz.getEnumConstants()) {
            if (constant.getName().equals(value)) {
                return constant;
            }
        }
        String[] allowedValues = Arrays.stream(clazz.getEnumConstants())
                .map(GetName::getName)
                .toArray(String[]::new);

        throw new RuntimeException("Invalid enum variant. Got " + value + ", must be any of " + Arrays.toString(allowedValues));
    }
}

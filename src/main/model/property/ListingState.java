package src.main.model.property;

import java.util.Arrays;

public enum ListingState {
    ACTIVE,
    RENTED,
    CANCELLED,
    SUSPENDED,
    REGISTERED;

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
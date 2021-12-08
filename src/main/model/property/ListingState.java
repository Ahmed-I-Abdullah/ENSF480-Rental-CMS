package src.main.model.property;

import java.util.Arrays;

public enum ListingState {
    ACTIVE, //when property is paid and live for renters to see
    RENTED, //when a renter has purchased the property
    CANCELLED, //when property is no longer avaialble for purchase (because of landlord or manager)
    SUSPENDED, //property is suspended by manager
    REGISTERED; //when property is created but not paid for

    //PROMSIES: returns the enums as a string array
    //REQUIRES: the enum class
    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
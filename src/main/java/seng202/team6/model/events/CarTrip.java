package seng202.team6.model.events;

public class CarTrip extends Event {
    CarTrip(Integer D, Integer M, Integer Y, String T, String N, String OCity,
            String OCountry, String DCity, String DCountry) {
        super(D, M, Y, T, N, OCity, OCountry, DCity, DCountry);
    }
}

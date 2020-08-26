package seng202.team6.model.events;

public class CarTrip extends Event {
    String OriginCity;
    String OriginCountry;
    String DestinatioCity;
    String DestinationCountry;

    CarTrip(Integer D, Integer M, Integer Y, String T, String N, String OCity,
            String OCountry, String DCity, String DCountry) {
        super(D, M, Y, T, N);
        OriginCity = OCity;
        OriginCountry = OCountry;
        DestinatioCity = DCity;
        DestinationCountry = DCountry;

    }
}

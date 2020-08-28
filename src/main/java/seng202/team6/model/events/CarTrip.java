package seng202.team6.model.events;

/**
 * This class is for car trip plans
 */
public class CarTrip extends Event {
    String OriginCity;
    String OriginCountry;
    String DestinatioCity;
    String DestinationCountry;

    /**
     *
     * @param D Any integer from 1 to 31
     * @param M Any integer from 1 to 12
     * @param Y Any integer from 2000 to 2099
     * @param T Any String with descriptive title
     * @param N Any String with additional information about the event
     * @param OCity Origin City
     * @param OCountry Origin Country
     * @param DCity Destination City
     * @param DCountry Destination Country
     */
    CarTrip(int D, int M, int Y, String T, String N, String OCity,
            String OCountry, String DCity, String DCountry) {
        super(D, M, Y, T, N);
        OriginCity = OCity;
        OriginCountry = OCountry;
        DestinatioCity = DCity;
        DestinationCountry = DCountry;
    }
}

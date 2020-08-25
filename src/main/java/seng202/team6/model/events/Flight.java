package seng202.team6.model.events;

public class Flight extends Event {
    String Airline;
    String OriginAirport;
    String DestinationAirport;

    Flight(Integer D, Integer M, Integer Y, String T, String N, String OCity,
           String OCountry, String DCity, String DCountry, String newAirline, String OAirport, String DAirport) {
        super(D, M, Y, T, N, OCity, OCountry, DCity, DCountry);
        Airline = newAirline;
        OriginAirport = OAirport;
        DestinationAirport = DAirport;
    }
}

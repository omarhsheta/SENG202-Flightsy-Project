package seng202.team6.model.events;

public class Event {
    Integer Day;
    Integer Month;
    Integer Year;
    String Title;
    String Notes;
    String OriginCity;
    String OriginCountry;
    String DestinatioCity;
    String DestinationCountry;

    Event(Integer D, Integer M, Integer Y, String T, String N, String OCity,
          String OCountry, String DCity, String DCountry) {
        Day = D;
        Month = M;
        Year = Y;
        Title = T;
        Notes = N;
        OriginCity = OCity;
        OriginCountry = OCountry;
        DestinatioCity = DCity;
        DestinationCountry = DCountry;
    }
}

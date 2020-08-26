package seng202.team6.model.events;

public class General extends Event {

    String City;
    String Country;

    General(int D, int M, int Y, String T, String N, String nCity, String nCountry) {
        super(D, M, Y, T, N);
        City = nCity;
        Country = nCountry;
    }
}

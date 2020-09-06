package seng202.team6.model.events;

/**
 * This class is for more general events
 */
public class General extends Event {

    String City;
    String Country;

    /**
     * Constructor for the General Event class
     * @param D Any integer from 1 to 31
     * @param M Any integer from 1 to 12
     * @param Y Any integer from 2000 to 2099
     * @param T Any String with descriptive title
     * @param N Any String with additional information about the event
     * @param nCity The city where the general event is taking place
     * @param nCountry The country where the general event is taking place
     */
    public General(int D, int M, int Y, String T, String N, String nCity, String nCountry) {
        super(D, M, Y, T, N);
        City = nCity;
        Country = nCountry;
    }
}

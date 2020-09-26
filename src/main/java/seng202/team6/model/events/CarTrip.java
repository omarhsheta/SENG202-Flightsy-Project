package seng202.team6.model.events;

import javafx.scene.layout.Pane;

/**
 * This class is for car trip plans
 */
public class CarTrip extends Event {
    String OriginCity;
    String OriginCountry;
    String DestinationCity;
    String DestinationCountry;

    /**
     * Constructor for the subclass CarTrip
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
    CarTrip(int D, int M, int Y, int newHour, int newMinute,  String T, String N, String OCity,
            String OCountry, String DCity, String DCountry) {
        super(D, M, Y, newHour, newMinute, T, N);
        OriginCity = OCity;
        OriginCountry = OCountry;
        DestinationCity = DCity;
        DestinationCountry = DCountry;
    }

    /**
     *
     * @return The origin city
     */
    public String getOCity() {
        return OriginCity;
    }

    /**
     *
     * @return The destination city
     */
    public String getDCity() {
        return DestinationCity;
    }

    /**
     * A method that makes a Pane for the car trip event
     * @return Pane object to be added to the holiday GUI
     */
    @Override
    public Pane toPane() {
        Pane returnPane = null;
        //Create pane for General event
        //Look at the Flight.java toPane() method for details on how to do this
        return returnPane;
    }
}

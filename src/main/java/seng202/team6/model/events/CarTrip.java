package seng202.team6.model.events;

import javafx.scene.layout.Pane;
import javafx.util.Pair;
import seng202.team6.gui.controller.holidayview.eventbuttons.DriveBtnController;
import seng202.team6.gui.helper.NodeHelper;
import java.time.format.DateTimeFormatter;

/**
 * This class is for car trip plans
 */
public class CarTrip extends Event {
    String OriginCity;
    String OriginCountry;
    String DestinationCity;
    String DestinationCountry;

    private final String subFolder = "holidayview/eventbuttons";
    private final String DriveButtonComponent = "DriveBtn";

    /**
     * Constructor for the subclass CarTrip
     * @param D Any integer from 1 to 31
     * @param M Any integer from 1 to 12
     * @param Y Any integer from 2000 to 2099
     * @param title Any String with descriptive title
     * @param note Any String with additional information about the event
     * @param OCity Origin City
     * @param OCountry Origin Country
     * @param DCity Destination City
     * @param DCountry Destination Country
     */
    public CarTrip(int D, int M, int Y, int newHour, int newMinute,  String title, String note, String OCity,
            String OCountry, String DCity, String DCountry) {
        super(D, M, Y, newHour, newMinute, title, note);
        OriginCity = OCity;
        OriginCountry = OCountry;
        DestinationCity = DCity;
        DestinationCountry = DCountry;
        Title = title;
        Notes = note;
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
     * @return The origin country
     */
    public String getOCountry() {
        return OriginCountry;
    }

    /**
     *
     * @return The destination city
     */
    public String getDCity() {
        return DestinationCity;
    }

    /**
     *
     * @return The destination country
     */
    public String getDCountry() {
        return DestinationCountry;
    }

    /**
     * A method that makes a Pane for the cartrip event
     * @return Pane object to be added to the holiday GUI
     */
    @Override
    public Pane toPane() {

        Pane newCarTripPane = null;
        try {
            Pair<Pane, DriveBtnController> pair = NodeHelper.LoadNode(subFolder, DriveButtonComponent);
            newCarTripPane = pair.getKey();
            DriveBtnController btnController = pair.getValue();

            String timePattern = "hh:mm a";
            String datePattern = "dd/MM/yyyy";
            btnController.setData(this.Title,
                    dateTime.format(DateTimeFormatter.ofPattern(timePattern)),
                    dateTime.format(DateTimeFormatter.ofPattern(datePattern)),
                    this.DestinationCity, this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newCarTripPane;
    }
}

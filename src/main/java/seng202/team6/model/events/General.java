package seng202.team6.model.events;

import javafx.scene.layout.Pane;
import javafx.util.Pair;
import seng202.team6.gui.controller.holidayview.eventbuttons.GeneralBtnController;
import seng202.team6.gui.helper.NodeHelper;

import java.time.format.DateTimeFormatter;

/**
 * This class is for more general events
 */
public class General extends Event {

    String City;
    String Country;

    private final String subFolder = "holidayview/eventbuttons";
    private final String ButtonComponent = "GeneralBtn";

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
    public General(int D, int M, int Y, int newHour, int newMinute, String T, String N, String nCity, String nCountry) {
        super(D, M, Y, newHour, newMinute, T, N);
        City = nCity;
        Country = nCountry;
    }

    /**
     * A method that makes a Pane for the General event
     * @return Pane object to be added to the holiday GUI
     */
    @Override
    public Pane toPane() {

        Pane newGeneralEventPane = null;
        try {
            Pair<Pane, GeneralBtnController> pair = NodeHelper.LoadNode(subFolder, ButtonComponent);
            newGeneralEventPane = pair.getKey();
            GeneralBtnController btnController = pair.getValue();

            String timePattern = "hh:mm a";
            String datePattern = "dd/MM/yyyy";
            btnController.setData(this.Title,
                    dateTime.format(DateTimeFormatter.ofPattern(timePattern)),
                    dateTime.format(DateTimeFormatter.ofPattern(datePattern)), this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newGeneralEventPane;
    }
}

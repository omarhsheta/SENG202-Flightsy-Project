package seng202.team6.gui.controller.holidayview.eventbuttons;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team6.gui.controller.holidayview.FlightEventInformationController;
import seng202.team6.gui.controller.routefinder.ResultController;
import seng202.team6.gui.helper.NodeHelper;
import seng202.team6.model.entities.Route;
import seng202.team6.model.events.CarTrip;
import seng202.team6.model.events.Flight;

public class DriveBtnController extends ResultController {

    @FXML
    Text Title, DesCity;

    @FXML
    Label Time, Date;

    CarTrip carTrip;

//    private final String subFolder = "eventbuttons";
//    private final String viewFlightInfoComponent = "DriveBtn";

    /**
     * Sets the data of the FXML component as well as links the component to a CarTrip object.
     * @param title
     * @param time
     * @param date
     * @param dCity
     * @param carTrip
     */
    public void setData(String title, String time, String date, String dCity, CarTrip carTrip) {
        Title.setText(title);
        Time.setText(time);
        Date.setText(date);
        DesCity.setText(dCity);

        this.carTrip = carTrip;
    }


}

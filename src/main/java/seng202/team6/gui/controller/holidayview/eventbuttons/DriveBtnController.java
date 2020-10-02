package seng202.team6.gui.controller.holidayview.eventbuttons;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import seng202.team6.gui.controller.routefinder.ResultController;
import seng202.team6.model.events.CarTrip;

public class DriveBtnController extends ResultController {

    @FXML
    Text Title, DesCity;

    @FXML
    Label Time, Date;

    @FXML
    Button DeleteBtn;

    CarTrip carTrip;

    /**
     * Sets the data of the FXML component as well as links the component to a CarTrip object.
     * @param title CarTrip title
     * @param time CarTrip Time of departure
     * @param date CarTrip Date
     * @param dCity CarTrip Departure city
     * @param carTrip CarTrip object
     */
    public void setData(String title, String time, String date, String dCity, CarTrip carTrip) {
        Title.setText(title);
        Time.setText(time);
        Date.setText(date);
        DesCity.setText(dCity);

        this.carTrip = carTrip;
    }
}

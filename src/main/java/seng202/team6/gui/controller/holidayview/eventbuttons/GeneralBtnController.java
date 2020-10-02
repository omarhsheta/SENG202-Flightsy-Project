package seng202.team6.gui.controller.holidayview.eventbuttons;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import seng202.team6.gui.controller.routefinder.ResultController;
import seng202.team6.model.events.General;

public class GeneralBtnController extends ResultController {

    @FXML
    Text Title;

    @FXML
    Label Time, Date;

    @FXML
    Button DeleteBtn;

    General generalEvent;

    /**
     * Sets the data of the FXML component as well as links the component to a General event object.
     * @param title General title
     * @param time General Time of departure
     * @param date General Date
     * @param generalEvent General object
     */
    public void setData(String title, String time, String date, General generalEvent) {
        Title.setText(title);
        Time.setText(time);
        Date.setText(date);

        this.generalEvent = generalEvent;
    }
}

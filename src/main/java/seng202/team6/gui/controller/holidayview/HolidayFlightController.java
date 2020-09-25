package seng202.team6.gui.controller.holidayview;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import seng202.team6.gui.controller.routefinder.ResultController;
import seng202.team6.model.entities.Route;

import java.util.ArrayList;
import java.util.Date;

public class HolidayFlightController extends ResultController {

    @FXML
    Text deptIATA;
    @FXML
    Text destIATA;

    @FXML
    Text deptTime;
    @FXML
    Text arrivalTime;

    @FXML
    Text deptDate;
    @FXML
    Text arrivalDate;

    Route route;

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setData(String sTime, String dTime, Date sDate, Date dDate) {
        deptIATA.setText(route.getSourceAirport());
        destIATA.setText(route.getDestinationAirport());

        deptTime.setText(sTime);
        arrivalTime.setText(dTime);

        deptDate.setText(sDate.toString());
        arrivalDate.setText(dDate.toString());
    }
}

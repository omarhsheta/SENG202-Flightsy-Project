package seng202.team6.gui.controller.holidayview;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team6.gui.controller.routefinder.FlightInformationController;
import seng202.team6.gui.controller.routefinder.ResultController;
import seng202.team6.gui.helper.NodeHelper;
import seng202.team6.model.entities.Route;
import seng202.team6.model.events.Flight;

import java.time.LocalDateTime;
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
    Flight flight;

    private final String subFolder = "holidayview";
    private final String viewFlightInfoComponent = "flighteventinformation";


    public void setData(String sTime, String dTime, String sDate, String dDate, Flight flight, Route route) {
        deptIATA.setText(route.getSourceAirport());
        destIATA.setText(route.getDestinationAirport());

        deptTime.setText(sTime);
        arrivalTime.setText(dTime);

        deptDate.setText(sDate);
        arrivalDate.setText(dDate);
        this.route = route;
        this.flight = flight;
    }

    /**
     * Called when the user wants to view information about the route in question.
     * This method creates a new stage and shows it to the user displaying all relevant information about the route.
     */
    @FXML
    private void OnViewInfoButtonClicked() {
        try {
            Pair<BorderPane, FlightEventInformationController> pair = NodeHelper.LoadNode(subFolder, viewFlightInfoComponent);
            BorderPane infoBorderPane = pair.getKey();
            FlightEventInformationController flightEventInformationController = pair.getValue();
            flightEventInformationController.setRoute(route);
            flightEventInformationController.setFlight(flight);
            Scene viewFlightInfoScene = new Scene(infoBorderPane);
            Stage newStage = new Stage();
            newStage.setTitle(String.format("%s to %s", route.getSourceAirport(), route.getDestinationAirport()));
            newStage.setScene(viewFlightInfoScene);
            newStage.show();
            flightEventInformationController.setStage(newStage);
            flightEventInformationController.populateInfo();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

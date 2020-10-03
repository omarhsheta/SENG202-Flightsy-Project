package seng202.team6.gui.controller.holidayview.eventbuttons;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team6.gui.controller.routefinder.ResultController;
import seng202.team6.gui.helper.NodeHelper;
import seng202.team6.model.entities.Route;
import seng202.team6.model.events.Flight;

public class FlightBtnController extends ResultController {

    @FXML
    Text Des, Src;

    @FXML
    Label SrcTime, SrcDate, DesTime;

    Flight flightEvent;
    Route flightRoute;

    private final String subFolder = "holidayview/eventbuttons";
    private final String viewFlightInfoComponent = "flighteventinformation";

    /**
     * Sets the data of the FXML component as well as links the component to a Flight event object.
     * @param srcTime Flight Source time
     * @param srcDate Flight Source date
     * @param desTime Flight destination time
     * @param flightEvent Flight object
     */
    public void setData(String srcTime, String srcDate, String desTime, Flight flightEvent,
                        Route route) {
        Des.setText(route.getDestinationAirport());
        Src.setText(route.getSourceAirport());
        SrcTime.setText(srcTime);
        SrcDate.setText(srcDate);
        DesTime.setText(desTime);

        this.flightEvent = flightEvent;
        this.flightRoute = route;
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
            flightEventInformationController.setRoute(this.flightRoute);
            flightEventInformationController.setFlight(this.flightEvent);
            Scene viewFlightInfoScene = new Scene(infoBorderPane);
            viewFlightInfoScene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
            Stage newStage = new Stage();
            newStage.setTitle(String.format("%s to %s", this.flightRoute.getSourceAirport(), this.flightRoute.getDestinationAirport()));
            newStage.setScene(viewFlightInfoScene);
            newStage.show();
            flightEventInformationController.setStage(newStage);
            flightEventInformationController.populateInfo();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}

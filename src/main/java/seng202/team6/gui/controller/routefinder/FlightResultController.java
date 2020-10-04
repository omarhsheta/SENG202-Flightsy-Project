package seng202.team6.gui.controller.routefinder;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import seng202.team6.gui.helper.NodeHelper;
import seng202.team6.model.data.DataExportHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;

import java.util.ArrayList;

public class FlightResultController extends ResultController {

    @FXML
    private Label flight;

    @FXML
    private Label distance;

    @FXML
    private Label airline;

    private Route route;

    private final String subFolder = "routefinder";
    private final String flightAddToHolidayComponent = "addflighttoholiday";
    private final String viewFlightInfoComponent = "flightinformation";

    /**
     * Set flight info
     * @param resultRoute Route object
     */
    public void SetFlight(Route resultRoute) {
        this.route = resultRoute;
        flight.setText(String.format("%s to %s", resultRoute.GetSourceAirport(), resultRoute.GetDestinationAirport()));
        distance.setText(String.format("Distance: %.2f", GetDistance()));
        airline.setText(GetAirlineName());
    }

    private String GetAirlineName() {
        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter(String.format("ID_AIRLINE = %s", route.GetAirlineID()), null));
        String airlineName;
        try {
            Airline routeAirline = DataExportHandler.GetInstance().FetchAirlines(filters).get(0);
            airlineName = routeAirline.GetName();
        } catch (Exception e) {
            airlineName = null;
        }

        return airlineName;
    }

    /**
     * Obtains the distance between two airports
     * @return double value of the distance between two airports
     */
    private double GetDistance() {
        ArrayList<Airport> airports = GetAirports();
        // Only ever two airports as route only has source/destination
        if (airports.size() != 2) {
            return -1;
        }
        return airports.get(0).GetDistance(airports.get(1));
    }

    /**
     * Obtains a filtered array of Airports
     * @return ArrayList instance with Airport.java objects
     */
    private ArrayList<Airport> GetAirports() {
        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter(String.format("ID_AIRPORT = %d", route.GetSourceAirportID()), "OR"));
        filters.add(new Filter(String.format("ID_AIRPORT = %d", route.GetDestinationAirportID()), null));
        return DataExportHandler.GetInstance().FetchAirports(filters);
    }

    /**
     * Called when the user wants to view the route in question.
     * The method draws a line between the source and destination airports.
     */
    @FXML
    private void OnViewButtonClicked()
    {
        if (mapHelper == null) {
            return;
        }

        mapHelper.ClearAll();
        ArrayList<Airport> airports = GetAirports();
        mapHelper.DrawAirportMarks(airports);
        mapHelper.DrawLineBetween(airports);
    }

    /**
     * Called when the user wants to view information about the route in question.
     * This method creates a new stage and shows it to the user displaying all relevant information about the route.
     */
    @FXML
    private void OnViewInfoButtonClicked() {
        try {
            Pair<BorderPane, FlightInformationController> pair = NodeHelper.LoadNode(subFolder, viewFlightInfoComponent);
            BorderPane infoBorderPane = pair.getKey();
            FlightInformationController flightInfoController = pair.getValue();
            flightInfoController.setRoute(route);
            Scene viewFlightInfoScene = new Scene(infoBorderPane);
            Stage newStage = new Stage();
            newStage.setTitle(String.format("%s to %s", route.GetSourceAirport(), route.GetDestinationAirport()));
            newStage.setScene(viewFlightInfoScene);
            newStage.show();
            flightInfoController.setStage(newStage);
            flightInfoController.populateInfo();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Called when the user wants to add the floght to their holiday
     * This method creates a new stage and shows it to the user asking them to select a departure and arrival time for the flight.
     */
    @FXML
    private void OnAddHolidayButtonClicked() {
        try {
            Pair<BorderPane, AddToHolidayController> pair = NodeHelper.LoadNode(subFolder, flightAddToHolidayComponent);
            BorderPane addHolidayBorderPane = pair.getKey();
            AddToHolidayController addToHolidayController = pair.getValue();
            addToHolidayController.setRoute(route);
            Scene addToHolidayScene = new Scene(addHolidayBorderPane);
            Stage newStage = new Stage();
            newStage.setTitle("Add to Holiday");
            newStage.setScene(addToHolidayScene);
            newStage.show();
            addToHolidayController.setStage(newStage);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

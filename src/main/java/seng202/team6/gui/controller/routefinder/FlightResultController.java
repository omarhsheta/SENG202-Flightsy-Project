package seng202.team6.gui.controller.routefinder;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import seng202.team6.gui.controller.MapController;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;

import java.util.ArrayList;

public class FlightResultController extends ResultController {

    @FXML
    private Label flight;

    @FXML
    private Label distance;

    @FXML
    private Button showButton;

    private Route route;

    /**
     * Set flight info
     * @param resultRoute Route object
     */
    public void SetFlight(Route resultRoute) {
        this.route = resultRoute;
        flight.setText(String.format("%s to %s", resultRoute.getSourceAirport(), resultRoute.getDestinationAirport()));
        distance.setText(String.format("Distance: %.2f", GetDistance()));
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
        filters.add(new Filter(String.format("ID_AIRPORT = %d", route.getSourceAirportID()), "OR"));
        filters.add(new Filter(String.format("ID_AIRPORT = %d", route.getDestinationAirportID()), null));
        return DataHandler.GetInstance().FetchAirports(filters);
    }

    @FXML
    private void OnButtonClicked()
    {
        if (mapController == null) {
            return;
        }

        mapController.ClearAll();
        ArrayList<Airport> airports = GetAirports();
        mapController.DrawAirportMarks(airports);
        mapController.DrawLineBetween(airports);
    }
}

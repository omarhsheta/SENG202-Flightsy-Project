package seng202.team6.gui.controller.routefinder;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;

import java.util.ArrayList;


public class FlightInformationController {

    @FXML
    Text titleInfo;
    @FXML
    Text airportInfo;
    @FXML
    Text locationInfo;
    @FXML
    Text distanceInfo;
    @FXML
    Text aircraftInfo;


    @FXML
    Button closeButton;


    private Route route;

    private Stage stage;

    DataHandler dataHandler;

    Airport originAirport;
    Airport destAirport;
    double distance;

    /**
     * Called when this FXML page is loaded
     */
    public void initialize() {
        dataHandler = DataHandler.GetInstance();
    }

    /**
     * Called to populate the flightinformation FXML file with information about the flight.
     */
    public void populateInfo() {
        setAirports();
        distance = originAirport.GetDistance(destAirport);

        titleInfo.setText(String.format("Information about flight: %s to %s", originAirport.getIATA(), destAirport.getIATA()));
        airportInfo.setText(String.format("%s to %s", originAirport.getName(), destAirport.getName()));
        locationInfo.setText(String.format("%s, %s to %s, %s", originAirport.getCity(), originAirport.getCountry(), destAirport.getCity(), destAirport.getCountry()));
        distanceInfo.setText(String.format("Distance: %.2fkm", distance));
        aircraftInfo.setText(String.format("Aircraft: %s", route.getEquipment()));
    }

    /**
     * Sets the variable route to the Route object supplied
     * @param route Route object supplied
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * Uses the route to get the source and destination airport objects. It does this by using the route objects
     * sourceAirportID and destinationAirportID to query the database and get the airports.
     */
    private void setAirports() {
        Filter originFilter = new Filter(String.format("ID_AIRPORT = %d", route.getSourceAirportID()), null);
        ArrayList<Filter> originFilters = new ArrayList<>();
        originFilters.add(originFilter);
        ArrayList<Airport> originAirportList = dataHandler.FetchAirports(originFilters);

        Filter destFilter = new Filter(String.format("ID_AIRPORT = %d", route.getDestinationAirportID()), null);
        ArrayList<Filter> destFilters = new ArrayList<>();
        destFilters.add(destFilter);
        ArrayList<Airport> destAirportList = dataHandler.FetchAirports(destFilters);

        // Only ever one airport as the filter is for the primary key, unless something went wrong...
        if (originAirportList.size() == 1 && destAirportList.size() == 1)
        {
            this.originAirport = originAirportList.get(0);
            this.destAirport = destAirportList.get(0);
        }
    }

    /**
     * sets the Stage variable stage to the supplied Stage.
     * @param newStage Stage object supplied
     */
    public void setStage(Stage newStage) {
        stage = newStage;
    }

    /**
     * Called when the user wants to close the window, calls stage.close().
     */
    @FXML
    private void closeWindow() {
        stage.close();
    }
}

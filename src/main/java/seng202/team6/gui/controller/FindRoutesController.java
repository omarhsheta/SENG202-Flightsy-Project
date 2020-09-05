package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Pair;
import seng202.team6.gui.components.FilterTextField;
import seng202.team6.gui.components.RouteViewButton;
import seng202.team6.gui.controller.routefinder.AirportResultController;
import seng202.team6.gui.controller.routefinder.FlightResultController;
import seng202.team6.gui.controller.routefinder.ResultController;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;

import javax.xml.catalog.Catalog;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FindRoutesController implements Initializable
{
    @FXML
    private WebView webView;

    private WebEngine webEngine;
    private final String mapHTML = "/map/main.html";

    @FXML
    private VBox resultsPane;

    //Airport filtering
    @FXML
    private VBox airportFilterBox;
    private ArrayList<FilterTextField> airportFilterTextFields;
    private final String airportResultComponent = "airportresult";

    //Flight filtering
    @FXML
    private VBox flightFilterOriginBox;
    @FXML
    private VBox flightFilterDestinationBox;
    @FXML
    private Slider maxStopsSlider;
    private ArrayList<FilterTextField> flightFilterOriginTextFields;
    private ArrayList<FilterTextField> flightFilterDestinationTextFields;
    private final String flightResultComponent = "flightresult";

    //Route filtering
    @FXML
    private VBox routeFilterBox;

    private MapController controller;

    /**
     * Initialize trip window component
     * @param url Url
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        webEngine.load(getClass().getResource(mapHTML).toExternalForm());

        controller = new MapController(webEngine);
        ResultController.SetMap(controller);

        //Grab all text filter components
        airportFilterTextFields = GetAllNodes(airportFilterBox);
        flightFilterOriginTextFields = GetAllNodes(flightFilterOriginBox);
        flightFilterDestinationTextFields = GetAllNodes(flightFilterDestinationBox);
    }

    /**
     * Create new node from FXML file
     * @param fxmlLocation Location of FXML file
     * @return FXML Component
     * @throws IOException IOException if file not found
     */
    private <T, U> Pair<T, U> LoadNode(String fxmlLocation) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        T node = loader.load(getClass().getResource("/routefinder/" + fxmlLocation + ".fxml").openStream());
        U controller = loader.getController();
        return new Pair<>(node, controller);
    }

    /**
     * Grab all filter box nodes from VBox
     * @param box Component to get all child filter text fields from
     * @return ArrayList of filter text fields
     */
    private ArrayList<FilterTextField> GetAllNodes(VBox box) {
        ArrayList<FilterTextField> nodes = new ArrayList<>();
        for (Node node : box.getChildren()) {
            if (node != null && node.getClass() == FilterTextField.class) {
                nodes.add((FilterTextField) node);
            }
        }
        return nodes;
    }

    /**
     * Draw airport marks based on filters
     */
    @FXML
    private void OnAirportFilterButtonClicked() {
        resultsPane.getChildren().clear();

        ArrayList<Filter> filters = FilterTextField.ExtractFilters(this.airportFilterTextFields);
        ArrayList<Airport> airports = DataHandler.GetInstance().FetchAirports(filters);

        try {
            for (Airport airport : airports) {
                Pair<BorderPane, AirportResultController> pair = LoadNode(airportResultComponent);
                resultsPane.getChildren().add(pair.getKey());

                AirportResultController resultController = pair.getValue();
                resultController.SetAirport(airport);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        controller.ClearAll();
        controller.DrawAirportMarks(airports);
    }

    /**
     * Get all routes between source and destination airports
     */
    @FXML
    private void OnFlightFilterButtonClicked() {
        resultsPane.getChildren().clear();
        ArrayList<Filter> originFilters = FilterTextField.ExtractFilters(this.flightFilterOriginTextFields);
        ArrayList<Filter> destinationFilters = FilterTextField.ExtractFilters(this.flightFilterDestinationTextFields);

        ArrayList<Airport> sourceAirports = DataHandler.GetInstance().FetchAirports(originFilters);
        ArrayList<Airport> destinationAirports = DataHandler.GetInstance().FetchAirports(destinationFilters);

        ArrayList<Route> routes = DataHandler.GetInstance().FetchRoutes(sourceAirports, destinationAirports);

        try {
            for (Route route : routes) {
                Pair<BorderPane, FlightResultController> pair = LoadNode(flightResultComponent);
                resultsPane.getChildren().add(pair.getKey());
                FlightResultController resultController = pair.getValue();
                resultController.SetFlight(route);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void OnRouteFilterButtonClicked() {

    }
}

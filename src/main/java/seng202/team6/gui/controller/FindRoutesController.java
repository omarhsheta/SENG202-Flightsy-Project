package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team6.gui.components.FilterTextField;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Route;

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


    //Flight filtering
    @FXML
    private VBox flightFilterOriginBox;
    @FXML
    private VBox flightFilterDestinationBox;
    @FXML
    private Slider maxStopsSlider;
    private ArrayList<FilterTextField> flightFilterOriginTextFields;
    private ArrayList<FilterTextField> flightFilterDestinationTextFields;

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

        //Grab all text filter components
        airportFilterTextFields = GetAllNodes(airportFilterBox);
        flightFilterOriginTextFields = GetAllNodes(flightFilterOriginBox);
        flightFilterDestinationTextFields = GetAllNodes(flightFilterDestinationBox);
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
     * Extract filters from filter text GUI components
     * @param filterFields List of filter GUI components
     * @return List of filters
     */
    private ArrayList<Filter> ExtractFilters(ArrayList<FilterTextField> filterFields) {
        ArrayList<Filter> filters = new ArrayList<>();
        for (FilterTextField box : filterFields) {
            if (!box.getText().equals("")) {
                String filterString = String.format(box.GetFilter(), box.getText());
                filters.add(new Filter(filterString, "AND"));
            }
        }
        return filters;
    }

    /**
     * Draw airport marks based on filters
     */
    @FXML
    private void OnAirportFilterButtonClicked() {
        ArrayList<Filter> filters = ExtractFilters(this.airportFilterTextFields);
        controller.ClearAll();
        controller.DrawAirportMarks(DataHandler.GetInstance().FetchAirports(filters));
    }

    /**
     * Get all routes between source and destination airports
     */
    @FXML
    private void OnFlightFilterButtonClicked() {
        ArrayList<Filter> originFilters = ExtractFilters(this.flightFilterOriginTextFields);
        ArrayList<Filter> destinationFilters = ExtractFilters(this.flightFilterDestinationTextFields);

        ArrayList<Route> routes;
        ArrayList<Airport> sourceAirports;
        ArrayList<Airport> destinationAirports;

        sourceAirports = DataHandler.GetInstance().FetchAirports(originFilters);
        destinationAirports = DataHandler.GetInstance().FetchAirports(destinationFilters);
        routes = DataHandler.GetInstance().FetchRoutes(sourceAirports, destinationAirports);

        //VERY TEMPORARY TO TEST
        resultsPane.getChildren().clear();
        for (Route route : routes) {
            TextArea text = new TextArea();
            text.setText(String.format("%s --> %s", route.GetSourceAirport(), route.GetDestinationAirport()));
            resultsPane.getChildren().add(text);
        }
        //END TEMPORARY
    }

    @FXML
    private void OnRouteFilterButtonClicked() {

    }
}

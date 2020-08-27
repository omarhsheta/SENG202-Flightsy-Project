package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import seng202.team6.gui.components.FilterTextField;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Route;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import java.util.ArrayList;

public class DataViewerController implements Initializable
{
    @FXML
    private TableView airport_table;


    @FXML
    Button airportFilterButton;
    @FXML
    private Pane airportFilterPane;
    private final ArrayList<FilterTextField> filterAirportTextFields = new ArrayList<>();

    @FXML
    Button airlineFilterButton;
    @FXML
    private Pane airlineFilterPane;
    private final ArrayList<FilterTextField> filterAirlineTextFields = new ArrayList<>();

    @FXML
    Button routeFilterButton;
    @FXML
    private Pane routeFilterPane;
    private final ArrayList<FilterTextField> filterRouteTextFields = new ArrayList<>();

    @FXML
    Button flightFilterButton;
    @FXML
    private Pane flightFilterPane;
    private final ArrayList<FilterTextField> filterFlightTextFields = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Grab all airport text filter components
        for (Node node : airportFilterPane.getChildren()) {
            if (node != null && node.getClass() == FilterTextField.class) {
                filterAirportTextFields.add((FilterTextField) node);
            }
        }

        //Grab all airline text filter components
        for (Node node : airlineFilterPane.getChildren()) {
            if (node != null && node.getClass() == FilterTextField.class) {
                filterAirlineTextFields.add((FilterTextField) node);
            }
        }

        //Grab all airline text filter components
        for (Node node : routeFilterPane.getChildren()) {
            if (node != null && node.getClass() == FilterTextField.class) {
                filterRouteTextFields.add((FilterTextField) node);
            }
        }

//        //Grab all airline text filter components
//        for (Node node : flightFilterPane.getChildren()) {
//            if (node != null && node.getClass() == FilterTextField.class) {
//                filterFlightTextFields.add((FilterTextField) node);
//            }
//        }

        airportFilterButton.setOnMouseClicked(x -> FilterTable("Airport", filterAirportTextFields));
        airlineFilterButton.setOnMouseClicked(x -> FilterTable("Airline", filterAirlineTextFields));
        routeFilterButton.setOnMouseClicked(x -> FilterTable("Route", filterRouteTextFields));
//        flightFilterButton.setOnMouseClicked(x -> FilterTable("Flight", filterFlightTextFields));
    }

    private void FillAirports() {
    }


    private void FilterTable(String table, ArrayList<FilterTextField> filterTextFields) {
        DataHandler dataHandler = DataHandler.GetInstance();

        ArrayList<Filter> filters = new ArrayList<>();

        for (FilterTextField box : filterTextFields) {
            if (!box.getText().equals("")) {
                String filterString = String.format(box.GetFilter(), box.getText());
                filters.add(new Filter(filterString, "AND"));
            }

        }

        try {
            if (table == "Airport") {
                ArrayList<Airport> filteredAirports = dataHandler.FetchAirports(filters);
                for(Airport airport: filteredAirports) {
                    System.out.println(String.format("%s      %s", airport.GetName(), airport.GetCountry()));
                }
            } else if (table == "Airline") {
                ArrayList<Airline> filteredAirlines = dataHandler.FetchAirlines(filters);
                for(Airline airline: filteredAirlines) {
                    System.out.println(String.format("%s      %s", airline.GetName(), airline.GetCountry()));
                }
            } else if (table == "Route") {
                ArrayList<Route> filteredRoutes = dataHandler.FetchRoutes(filters);
                for(Route route: filteredRoutes) {
                    System.out.println(String.format("%s      %s", route.GetSourceAirport(), route.GetDestinationAirport()));
                }
            } else if (table == "Flight") {
                System.out.println("Not Implemented");
            }
        } catch (Exception ignored) {
        }
    }
}

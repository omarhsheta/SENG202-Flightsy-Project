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

    private DataHandler dataHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataHandler = DataHandler.GetInstance();

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
    }

    private void FillAirports() {
    }

    /**
     * FXML button action that takes place when the Filter button is clicked on the Airports data view.
     * This function takes the filters from the GetFilters method and gets the filtered Airport ArrayList from the DataHandler.
     * Then it inputs the data into the data viewer table.
     */
    @FXML
    private void OnAirportFilterButtonClicked() {
        ArrayList<Filter> filters =  GetFilters(filterAirportTextFields);

        try {
            ArrayList<Airport> filteredAirports = dataHandler.FetchAirports(filters);
            //For testing only
            for(Airport airport: filteredAirports) {
                System.out.println(String.format("%s      %s", airport.GetName(), airport.GetCountry()));
            }
        }
        catch (Exception ignored) {
        }

        //Input into table here
    }

    /**
     * FXML button action that takes place when the Filter button is clicked on the Airlines data view.
     * This function takes the filters from the GetFilters method and gets the filtered Airline ArrayList from the DataHandler.
     * Then it inputs the data into the data viewer table.
     */
    @FXML
    private void OnAirlineFilterButtonClicked() {
        ArrayList<Filter> filters =  GetFilters(filterAirlineTextFields);

        try {
            ArrayList<Airline> filteredAirlines = dataHandler.FetchAirlines(filters);
            //For testing only
            for(Airline airline: filteredAirlines) {
                System.out.println(String.format("%s      %s", airline.GetName(), airline.GetCountry()));
            }
        }
        catch (Exception ignored) {
        }

        //Input into table here
    }

    /**
     * FXML button action that takes place when the Filter button is clicked on the Routes data view.
     * This function takes the filters from the GetFilters method and gets the filtered Route ArrayList from the DataHandler.
     * Then it inputs the data into the data viewer table.
     */
    @FXML
    private void OnRouteFilterButtonClicked() {
        ArrayList<Filter> filters =  GetFilters(filterRouteTextFields);

        try {
            ArrayList<Route> filteredRoutes = dataHandler.FetchRoutes(filters);
            //For testing only
            for(Route route: filteredRoutes) {
                System.out.println(String.format("%s      %s", route.GetSourceAirport(), route.GetDestinationAirport()));
            }
        }
        catch (Exception ignored) {
        }

        //Input into table here
    }

    /**
     * GetFilters method that takes a parameter <code>filterTextFields</code> which is an ArrayList of FilterTextField objects,
     * and takes the filter formatting and text from the object. It then creates a Filter object from this and adds the
     * filter to an ArrayList of Filter objects and returns the ArrayList.
     * @param filterTextFields An ArrayList of FilterTextFields
     * @return An ArrayList of Filter objects.
     */
    private ArrayList<Filter> GetFilters(ArrayList<FilterTextField> filterTextFields) {
        ArrayList<Filter> filters = new ArrayList<>();

        for (FilterTextField box : filterTextFields) {
            if (!box.getText().equals("")) {
                String filterString = String.format(box.GetFilter(), box.getText());
                filters.add(new Filter(filterString, "AND"));
            }
        }
        return filters;
    }
}

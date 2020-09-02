package seng202.team6.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import seng202.team6.gui.components.FilterTextField;
import seng202.team6.model.data.CSVLoader;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Route;
import seng202.team6.model.entities.RoutePath;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

import java.util.ArrayList;

public class DataViewerController implements Initializable
{
    @FXML
    private BorderPane borderPane;

    @FXML
    private MenuItem rowImportMenuItem;

    @FXML
    private MenuItem fileImportMenuItem;

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

    private CSVLoader csvLoader;
    private DataHandler dataHandler;

    @FXML
    ChoiceBox sortChoiceBox;

    public DataViewerController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataHandler = DataHandler.GetInstance();
        csvLoader = new CSVLoader();
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

        String sortValue = (String) sortChoiceBox.getValue();

        int numRowsLeftOut = 0;

        try {
            ArrayList<Airport> filteredAirports = new ArrayList<>();
            if (sortValue.equals("Sort by most routes")) {
                filteredAirports = dataHandler.FetchAirports(filters, "DESC");
                numRowsLeftOut = dataHandler.FetchAirports(filters).size() - filteredAirports.size();

            } else if (sortValue.equals("Sort by least routes")) {
                filteredAirports = dataHandler.FetchAirports(filters, "ASC");
                numRowsLeftOut = filteredAirports.size() - dataHandler.FetchAirports(filters).size();
            } else {
                filteredAirports = dataHandler.FetchAirports(filters);
            }
            //For testing only
            for(Airport airport: filteredAirports) {
                System.out.println(String.format("%s      %s", airport.GetName(), airport.GetCountry()));
            }
        }
        catch (Exception ignored) {
        }

        if (numRowsLeftOut != 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING, String.format("%d airports left out as they have no routes departing", numRowsLeftOut), ButtonType.OK);
            alert.showAndWait();
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


    /**
     * Opens native file explorer for user to select a CSV file to import to the database
     * @return File object of the selected file for import
     */
    public File SelectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV File", "*.csv"),
                new FileChooser.ExtensionFilter("DAT File", "*.dat"),
                new FileChooser.ExtensionFilter("Text File", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(borderPane.getScene().getWindow());

        return selectedFile;
    }


    /**
     * Gets Airport data file, passes to CSVLoader to get Airport objects,
     * then passes objects to DataHandler to add to database
     */
    @FXML
    public void AirportFileImport() {
        File selectedFile = SelectFile();
        if (selectedFile != null){
            ArrayList<Airport> airports = csvLoader.GetCSVAirportList(selectedFile.getAbsolutePath());
            if (airports.size() != 0) {
                try {
                    dataHandler.InsertAirports(airports);
                } catch (SQLException exception) {
                    //Do nothing
                }
            }
        }
    }


    /**
     * Gets Airline data file, passes to CSVLoader to get Airline objects,
     * then passes objects to DataHandler to add to database
     */
    @FXML
    public void AirlineFileImport() {
        File selectedFile = SelectFile();
        if (selectedFile != null){
            ArrayList<Airline> airlines = csvLoader.GetCSVAirlineList(selectedFile.getAbsolutePath());
            if (airlines.size() != 0) {
                try {
                    dataHandler.InsertAirlines(airlines);
                } catch (SQLException exception) {
                    //Do nothing
                }
            }
        }
    }


    /**
     * Gets Route data file, passes to CSVLoader to get Route objects,
     * then passes objects to DataHandler to add to database
     */
    @FXML
    public void RouteFileImport() {
        File selectedFile = SelectFile();
        if (selectedFile != null) {
            ArrayList<Route> routes = csvLoader.GetCSVRouteList(selectedFile.getAbsolutePath());
            if (routes.size() != 0) {
                try {
                    dataHandler.InsertRoutes(routes);
                } catch (SQLException exception) {
                    //Do nothing
                }
            }
        }
    }


    /**
     * Gets Flight data file, passes to CSVLoader to get RoutePath object,
     * then passes object to DataHandler to add to database
     */
    //TODO create method in DataHandler to add RoutePath object to database
    @FXML
    public void FlightFileImport() {
//        File selectedFile = SelectFile();
//        RoutePath routePath = csvLoader.GetCSVRoutePath(selectedFile.getAbsolutePath());
//        if (routePath != null) {
//            try {
//                dataHandler.InsertRoutePath(routePath);
//            } catch (SQLException exception) {
//                //Do nothing
//            }
//        }
    }


    @FXML
    public void OpenPopUp() throws IOException {
        final Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource("addrow.fxml")
                )
        );
        Scene dialogScene = new Scene(root);
        popUp.setScene(dialogScene);
        popUp.show();
    }
}

package seng202.team6.gui.controller.dataviewer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import seng202.team6.gui.components.FilterTextField;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airline;
import seng202.team6.model.entities.Airport;

import javax.xml.crypto.Data;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AirportTabController implements Initializable
{
    @FXML
    private TableView<Airport> airportTable;

    @FXML
    private TableColumn<Airport, ?> columnID;
    @FXML
    private TableColumn<Airport, ?> columnName;
    @FXML
    private TableColumn<Airport, ?> columnCity;
    @FXML
    private TableColumn<Airport, ?> columnCountry;
    @FXML
    private TableColumn<Airport, ?> columnIATA;
    @FXML
    private TableColumn<Airport, ?> columnICAO;
    @FXML
    private TableColumn<Airport, ?> columnLat;
    @FXML
    private TableColumn<Airport, ?> columnLng;
    @FXML
    private TableColumn<Airport, ?> columnAlt;
    @FXML
    private TableColumn<Airport, ?> columnTZ;
    @FXML
    private TableColumn<Airport, ?> columnDST;

    @FXML
    private Button airportFilterButton;

    @FXML
    private Pane airportFilterPane;
    private final ArrayList<FilterTextField> filterAirportTextFields = new ArrayList<>();

    DataHandler dataHandler;

    @FXML
    ChoiceBox sortChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataHandler = DataHandler.GetInstance();

        //Grab all airport text filter components
        for (Node node : airportFilterPane.getChildren()) {
            if (node != null && node.getClass() == FilterTextField.class) {
                filterAirportTextFields.add((FilterTextField) node);
            }
        }

        ObservableList<TableColumn<Airport, ?>> columns = airportTable.getColumns();

        columnID.setCellValueFactory(new PropertyValueFactory<>("airportID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        columnIATA.setCellValueFactory(new PropertyValueFactory<>("IATA"));
        columnICAO.setCellValueFactory(new PropertyValueFactory<>("ICAO"));
        columnLat.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        columnLng.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        columnAlt.setCellValueFactory(new PropertyValueFactory<>("altitude"));
        columnTZ.setCellValueFactory(new PropertyValueFactory<>("timezone"));
        columnDST.setCellValueFactory(new PropertyValueFactory<>("DST"));

        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter("COUNTRY = 'New Zealand'", null));
        ArrayList<Airport> filteredAirports = DataHandler.GetInstance().FetchAirports(filters);
        airportTable.getItems().addAll(filteredAirports);
    }

    /**
     * FXML button action that takes place when the Filter button is clicked on the Airports data view.
     * This function takes the filters from the GetFilters method and gets the filtered Airport ArrayList from the DataHandler.
     * This function also check the sortChoiceBox for any sort preference, and applies sorting if applicable.
     * Then it inputs the data into the data viewer table.
     */

    @FXML
    private void OnAirportFilterButtonClicked() {
        ArrayList<Filter> filters =  dataHandler.GetFilters(filterAirportTextFields);

        String sortValue = (String) sortChoiceBox.getValue();

        int numRowsLeftOut = 0;

        try {
            ArrayList<Airport> filteredAirports;
            if (sortValue.equals("Sort by most routes")) {
                filteredAirports = dataHandler.FetchAirports(filters, "DESC");
                numRowsLeftOut = dataHandler.FetchAirports(filters).size() - filteredAirports.size();

            } else if (sortValue.equals("Sort by least routes")) {
                filteredAirports = dataHandler.FetchAirports(filters, "ASC");
                numRowsLeftOut = filteredAirports.size() - dataHandler.FetchAirports(filters).size();
            } else {
                filteredAirports = dataHandler.FetchAirports(filters);
            }

            airportTable.getItems().clear();
            airportTable.getItems().addAll(filteredAirports);
        }
        catch (Exception ignored) {
        }

        if (numRowsLeftOut > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING, String.format("%d airports left out as they have no routes departing", numRowsLeftOut), ButtonType.OK);
            alert.showAndWait();
        }
    }
}

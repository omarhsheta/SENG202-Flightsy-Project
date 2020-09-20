package seng202.team6.gui.controller.dataviewer;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team6.gui.components.FilterTextField;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airport;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controls handling the airport tab
 */
public class AirportTabController extends TabController<Airport>
{
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
    ChoiceBox<String> sortChoiceBox;

    /**
     * Initialize this tab with default data from New Zealand.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter("COUNTRY = 'New Zealand'", null));
        ArrayList<Airport> filteredAirports = DataHandler.GetInstance().FetchAirports(filters);
        table.getItems().addAll(filteredAirports);
    }

    /**
     * Set table cell value factories for the table view.
     */
    @Override
    protected void SetCellFactories() {
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
    }

    /**
     * FXML button action that takes place when the Filter button is clicked on the Airports data view.
     * This function takes the filters from the GetFilters method and gets the filtered Airport ArrayList from the DataHandler.
     * This function also check the sortChoiceBox for any sort preference, and applies sorting if applicable.
     * Then it inputs the data into the data viewer table.
     */
    @Override
    protected void OnFilterButtonClicked() {
        ArrayList<Filter> filters =  FilterTextField.ExtractFilters(filterTextFields);

        String sortValue = sortChoiceBox.getValue();

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

            table.getItems().clear();
            table.getItems().addAll(filteredAirports);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

        if (numRowsLeftOut > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING, String.format("%d airports left out as they have no routes departing", numRowsLeftOut), ButtonType.OK);
            alert.showAndWait();
        }
    }
}

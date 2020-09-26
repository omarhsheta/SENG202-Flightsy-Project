package seng202.team6.gui.controller.dataviewer;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team6.gui.components.FilterTextField;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airline;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controls handling the airline tab
 */
public class AirlineTabController extends TabController<Airline>
{
    @FXML
    private TableColumn<Airline, ?> columnID;
    @FXML
    private TableColumn<Airline, ?> columnName;
    @FXML
    private TableColumn<Airline, ?> columnAlias;
    @FXML
    private TableColumn<Airline, ?> columnIATA;
    @FXML
    private TableColumn<Airline, ?> columnICAO;
    @FXML
    private TableColumn<Airline, ?> columnCallsign;
    @FXML
    private TableColumn<Airline, ?> columnCountry;
    @FXML
    private TableColumn<Airline, ?> columnActive;

    /**
     * Initialize this tab with default data from New Zealand.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter("COUNTRY = 'New Zealand'", null));
        ArrayList<Airline> filteredAirlines = dataExport.FetchAirlines(filters);
        table.getItems().addAll(filteredAirlines);
    }

    /**
     * Set table cell value factories for the table view.
     */
    @Override
    protected void SetCellFactories() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("airlineID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAlias.setCellValueFactory(new PropertyValueFactory<>("alias"));
        columnIATA.setCellValueFactory(new PropertyValueFactory<>("IATA"));
        columnICAO.setCellValueFactory(new PropertyValueFactory<>("ICAO"));
        columnCallsign.setCellValueFactory(new PropertyValueFactory<>("callsign"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        columnActive.setCellValueFactory(new PropertyValueFactory<>("active"));
    }

    /**
     * FXML button action that takes place when the Filter button is clicked on the Airlines data view.
     * This function takes the filters from the GetFilters method and gets the filtered Airline ArrayList from the DataHandler.
     * Then it inputs the data into the data viewer table.
     */
    @Override
    protected void OnFilterButtonClicked() {
        ArrayList<Filter> filters =  FilterTextField.ExtractFilters(filterTextFields);

        try {
            table.getItems().clear();
            ArrayList<Airline> filteredAirlines = dataExport.FetchAirlines(filters);
            table.getItems().addAll(filteredAirlines);
        }
        catch (Exception ignored) {
        }
    }
}

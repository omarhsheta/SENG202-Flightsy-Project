package seng202.team6.gui.controller.dataviewer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import seng202.team6.gui.components.FilterTextField;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airport;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AirportTabController implements Initializable
{
    @FXML
    private TableView<Airport> airportTable;

    @FXML
    private Button airportFilterButton;

    @FXML
    private Pane airportFilterPane;
    private final ArrayList<FilterTextField> filterAirportTextFields = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<TableColumn<Airport, ?>> columns = airportTable.getColumns();

        TableColumn<Airport, Integer> columnID = new TableColumn<>("ID");
        columnID.setCellValueFactory(new PropertyValueFactory<>("AirportID"));
        columns.add(columnID);

        TableColumn<Airport, String> columnName = new TableColumn<>("Name");
        columnID.setCellValueFactory(new PropertyValueFactory<>("Name"));
        columns.add(columnName);

        TableColumn<Airport, String> columnCity = new TableColumn<>("City");
        columnID.setCellValueFactory(new PropertyValueFactory<>("City"));
        columns.add(columnCity);

        TableColumn<Airport, String> columnCountry = new TableColumn<>("Country");
        columnID.setCellValueFactory(new PropertyValueFactory<>("Country"));
        columns.add(columnCountry);

        TableColumn<Airport, String> columnIATA = new TableColumn<>("IATA");
        columnID.setCellValueFactory(new PropertyValueFactory<>("IATA"));
        columns.add(columnIATA);

        TableColumn<Airport, String> columnICAO = new TableColumn<>("ICAO");
        columnID.setCellValueFactory(new PropertyValueFactory<>("ICAO"));
        columns.add(columnICAO);

        TableColumn<Airport, Float> columnLatitude = new TableColumn<>("Latitude");
        columnID.setCellValueFactory(new PropertyValueFactory<>("Latitude"));
        columns.add(columnLatitude);

        TableColumn<Airport, Float> columnLongitude = new TableColumn<>("Longitude");
        columnID.setCellValueFactory(new PropertyValueFactory<>("Longitude"));
        columns.add(columnLongitude);

        TableColumn<Airport, Integer> columnAltitude = new TableColumn<>("Altitude");
        columnID.setCellValueFactory(new PropertyValueFactory<>("Altitude"));
        columns.add(columnAltitude);

        TableColumn<Airport, Float> columnTimezone = new TableColumn<>("Timezone");
        columnID.setCellValueFactory(new PropertyValueFactory<>("Timezone"));
        columns.add(columnTimezone);

        TableColumn<Airport, Character> columnDST = new TableColumn<>("DST");
        columnID.setCellValueFactory(new PropertyValueFactory<>("DST"));
        columns.add(columnDST);

        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter("COUNTRY = 'New Zealand'", ""));
        ArrayList<Airport> filteredAirports = DataHandler.GetInstance().FetchAirports(filters);
        airportTable.getItems().addAll(filteredAirports);
    }

    /**
     * FXML button action that takes place when the Filter button is clicked on the Airports data view.
     * This function takes the filters from the GetFilters method and gets the filtered Airport ArrayList from the DataHandler.
     * Then it inputs the data into the data viewer table.
     */
    @FXML
    private void OnAirportFilterButtonClicked() {
        ArrayList<Filter> filters = GetFilters(filterAirportTextFields);
        ArrayList<Airport> filteredAirports = DataHandler.GetInstance().FetchAirports(filters);
        airportTable.getItems().addAll(filteredAirports);
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

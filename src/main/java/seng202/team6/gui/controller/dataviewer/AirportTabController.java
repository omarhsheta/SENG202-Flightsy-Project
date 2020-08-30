package seng202.team6.gui.controller.dataviewer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        columnDST.setCellValueFactory(new PropertyValueFactory<>("timezone"));

        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter("COUNTRY = 'New Zealand'", null));
        ArrayList<Airport> filteredAirports = DataHandler.GetInstance().FetchAirports(filters);
        airportTable.getItems().addAll(filteredAirports);
    }
}

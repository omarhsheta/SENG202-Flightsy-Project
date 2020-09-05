package seng202.team6.gui.controller.dataviewer;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import seng202.team6.gui.components.FilterTextField;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Airline;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AirlineTabController implements Initializable
{
    @FXML
    private TableView<Airline> airlineTable;

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

    @FXML
    private Button airlineFilterButton;


    @FXML
    private Pane airlineFilterPane;
    private final ArrayList<FilterTextField> filterAirlineTextFields = new ArrayList<>();

    DataHandler dataHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataHandler = DataHandler.GetInstance();

        //Grab all airline text filter components
        for (Node node : airlineFilterPane.getChildren()) {
            if (node != null && node.getClass() == FilterTextField.class) {
                filterAirlineTextFields.add((FilterTextField) node);
            }
        }

        ObservableList<TableColumn<Airline, ?>> columns = airlineTable.getColumns();

        columnID.setCellValueFactory(new PropertyValueFactory<>("airlineID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAlias.setCellValueFactory(new PropertyValueFactory<>("alias"));
        columnIATA.setCellValueFactory(new PropertyValueFactory<>("IATA"));
        columnICAO.setCellValueFactory(new PropertyValueFactory<>("ICAO"));
        columnCallsign.setCellValueFactory(new PropertyValueFactory<>("callsign"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        columnActive.setCellValueFactory(new PropertyValueFactory<>("active"));

        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter("COUNTRY = 'New Zealand'", null));
        ArrayList<Airline> filteredAirlines = dataHandler.FetchAirlines(filters);
        airlineTable.getItems().addAll(filteredAirlines);
    }

    /**
     * FXML button action that takes place when the Filter button is clicked on the Airlines data view.
     * This function takes the filters from the GetFilters method and gets the filtered Airline ArrayList from the DataHandler.
     * Then it inputs the data into the data viewer table.
     */
    @FXML
    private void OnAirlineFilterButtonClicked() {
        ArrayList<Filter> filters =  dataHandler.GetFilters(filterAirlineTextFields);

        try {
            airlineTable.getItems().clear();
            ArrayList<Airline> filteredAirlines = dataHandler.FetchAirlines(filters);
            airlineTable.getItems().addAll(filteredAirlines);
        }
        catch (Exception ignored) {
        }
    }

}

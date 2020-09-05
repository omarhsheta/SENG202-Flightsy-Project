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
import seng202.team6.model.entities.Route;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RouteTabController implements Initializable
{
    @FXML
    private TableView<Route> routeTable;

    @FXML
    private TableColumn<Route, ?> columnID;
    @FXML
    private TableColumn<Route, ?> columnAirline;
    @FXML
    private TableColumn<Route, ?> columnSrc;
    @FXML
    private TableColumn<Route, ?> columnSrcID;
    @FXML
    private TableColumn<Route, ?> columnDst;
    @FXML
    private TableColumn<Route, ?> columnDstID;
    @FXML
    private TableColumn<Route, ?> columnCodeshare;
    @FXML
    private TableColumn<Route, ?> columnStops;
    @FXML
    private TableColumn<Route, ?> columnEquipment;

    @FXML
    private Button routeFilterButton;

    DataHandler dataHandler;

    @FXML
    private Pane routeFilterPane;
    private final ArrayList<FilterTextField> filterRouteTextFields = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataHandler = DataHandler.GetInstance();

        //Grab all airline text filter components
        for (Node node : routeFilterPane.getChildren()) {
            if (node != null && node.getClass() == FilterTextField.class) {
                filterRouteTextFields.add((FilterTextField) node);
            }
        }

        ObservableList<TableColumn<Route, ?>> columns = routeTable.getColumns();

        columnID.setCellValueFactory(new PropertyValueFactory<>("airlineID"));
        columnAirline.setCellValueFactory(new PropertyValueFactory<>("airline"));
        columnSrc.setCellValueFactory(new PropertyValueFactory<>("sourceAirport"));
        columnSrcID.setCellValueFactory(new PropertyValueFactory<>("sourceAirportID"));
        columnDst.setCellValueFactory(new PropertyValueFactory<>("destinationAirport"));
        columnDstID.setCellValueFactory(new PropertyValueFactory<>("destinationAirportID"));
        columnCodeshare.setCellValueFactory(new PropertyValueFactory<>("codeshare"));
        columnStops.setCellValueFactory(new PropertyValueFactory<>("stops"));
        columnEquipment.setCellValueFactory(new PropertyValueFactory<>("equipment"));

        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter("SOURCE_AIRPORT = 'AKL'", null));
        ArrayList<Route> filteredRoutes = DataHandler.GetInstance().FetchRoutes(filters);
        routeTable.getItems().addAll(filteredRoutes);
    }

    /**
     * FXML button action that takes place when the Filter button is clicked on the Routes data view.
     * This function takes the filters from the GetFilters method and gets the filtered Route ArrayList from the DataHandler.
     * Then it inputs the data into the data viewer table.
     */
    @FXML
    private void OnRouteFilterButtonClicked() {
        ArrayList<Filter> filters =  dataHandler.GetFilters(filterRouteTextFields);

        try {
            routeTable.getItems().clear();
            ArrayList<Route> filteredRoutes = dataHandler.FetchRoutes(filters);
            routeTable.getItems().addAll(filteredRoutes);
        }
        catch (Exception ignored) {
        }

        //Input into table here
    }

}

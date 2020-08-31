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
    private TableColumn<Route, ?> columnSourceAirport;
    @FXML
    private TableColumn<Route, ?> columnSourceAirportID;
    @FXML
    private TableColumn<Route, ?> columnDestinationAirport;
    @FXML
    private TableColumn<Route, ?> columnDestinationAirportID;
    @FXML
    private TableColumn<Route, ?> columnCodeshare;
    @FXML
    private TableColumn<Route, ?> columnStops;
    @FXML
    private TableColumn<Route, ?> columnEquipment;

    @FXML
    private Button routeFilterButton;

    @FXML
    private Pane routeFilterPane;
    private final ArrayList<FilterTextField> filterRouteTextFields = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<TableColumn<Route, ?>> columns = routeTable.getColumns();

        columnID.setCellValueFactory(new PropertyValueFactory<>("airlineID"));
        columnAirline.setCellValueFactory(new PropertyValueFactory<>("airline"));
        columnSourceAirport.setCellValueFactory(new PropertyValueFactory<>("sourceAirport"));
        columnSourceAirportID.setCellValueFactory(new PropertyValueFactory<>("sourceAirportID"));
        columnDestinationAirport.setCellValueFactory(new PropertyValueFactory<>("destinationAirport"));
        columnDestinationAirportID.setCellValueFactory(new PropertyValueFactory<>("destinationAirportID"));
        columnCodeshare.setCellValueFactory(new PropertyValueFactory<>("codeshare"));
        columnStops.setCellValueFactory(new PropertyValueFactory<>("stops"));
        columnEquipment.setCellValueFactory(new PropertyValueFactory<>("equipment"));

        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter("", null));
        ArrayList<Route> filteredRoutes = DataHandler.GetInstance().FetchRoutes(filters);
        routeTable.getItems().addAll(filteredRoutes);
    }
}

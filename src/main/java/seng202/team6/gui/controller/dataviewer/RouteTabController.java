package seng202.team6.gui.controller.dataviewer;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team6.gui.components.FilterTextField;
import seng202.team6.model.data.DataExportHandler;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.Route;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controls handling the route tab
 */
public class RouteTabController extends TabController<Route>
{
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

    /**
     * Initialize this tab with default data from Auckland
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter("SOURCE_AIRPORT = 'AKL'", null));
        ArrayList<Route> filteredRoutes = DataExportHandler.GetInstance().FetchRoutes(filters);
        table.getItems().addAll(filteredRoutes);
    }

    /**
     * Set table cell value factories for the table view.
     */
    @Override
    protected void SetCellFactories() {
        columnID.setCellValueFactory(new PropertyValueFactory<>("airlineID"));
        columnAirline.setCellValueFactory(new PropertyValueFactory<>("airline"));
        columnSrc.setCellValueFactory(new PropertyValueFactory<>("sourceAirport"));
        columnSrcID.setCellValueFactory(new PropertyValueFactory<>("sourceAirportID"));
        columnDst.setCellValueFactory(new PropertyValueFactory<>("destinationAirport"));
        columnDstID.setCellValueFactory(new PropertyValueFactory<>("destinationAirportID"));
        columnCodeshare.setCellValueFactory(new PropertyValueFactory<>("codeshare"));
        columnStops.setCellValueFactory(new PropertyValueFactory<>("stops"));
        columnEquipment.setCellValueFactory(new PropertyValueFactory<>("equipment"));
    }

    @Override
    protected void OnFilterButtonClicked() {
        ArrayList<Filter> filters =  FilterTextField.ExtractFilters(filterTextFields);

        try {
            table.getItems().clear();
            ArrayList<Route> filteredRoutes = dataExport.FetchRoutes(filters);
            table.getItems().addAll(filteredRoutes);
        }
        catch (Exception ignored) {
        }
    }

}

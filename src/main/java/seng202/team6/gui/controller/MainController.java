package seng202.team6.gui.controller;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team6.model.data.CSVLoader;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.data.Filter;
import seng202.team6.model.entities.RoutePath;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private WebView webView2;

    private WebEngine webEngine;
    private final String mapHTML = "/map/main.html";
    private MapController controller;

    /**
     * Called when this FXML page is loaded
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView2.getEngine();
        webEngine.load(getClass().getResource(mapHTML).toExternalForm());

        controller = new MapController(webEngine);

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                OnLoad();
            }
        });
    }

    /**
     * Called when WebEngine is finished loading
     */
    private void OnLoad() {
        /* Temporary test */
        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter("COUNTRY = 'New Zealand'", "OR"));
        filters.add(new Filter("COUNTRY = 'Australia'", null));
        ArrayList<Airport> airports = DataHandler.GetInstance().FetchAirports(filters);
        controller.DrawAirportMarks(airports);

        CSVLoader loader = new CSVLoader();
        RoutePath path = loader.GetCSVRoutePath("src/test/resources/CSVLoader/NZCH-WSSS.csv");
        controller.DrawRoutePath(path);
        /* End temporary */
    }
}

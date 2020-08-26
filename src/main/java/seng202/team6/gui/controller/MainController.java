package seng202.team6.gui.controller;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Pair;
import seng202.team6.model.data.CSVLoader;
import seng202.team6.model.data.DataHandler;
import seng202.team6.model.entities.Airport;
import seng202.team6.model.entities.Filter;
import seng202.team6.model.entities.RoutePath;
import seng202.team6.model.interfaces.IMapDrawable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML
    private WebView webView2;

    @FXML
    private Button button;

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

        //Temporary test to show how to use MapController
        controller = new MapController(webEngine);

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                OnLoad();
            }
        });
        //End temporary
    }

    /**
     * Called when WebEngine is finished loading
     * TEMPORARY
     */
    private void OnLoad() {
        ArrayList<Filter> filters = new ArrayList<>();
        filters.add(new Filter("COUNTRY = 'New Zealand'", "OR"));
        filters.add(new Filter("COUNTRY = 'Australia'", null));
        try {
            ArrayList<Airport> airports = DataHandler.GetInstance().FetchAirports(filters);
            controller.DrawAirportMarks(airports);
        } catch (SQLException ignored) {
        }

        CSVLoader loader = new CSVLoader();
        RoutePath path = loader.GetCSVRoutePath("src/test/resources/CSVLoader/NZCH-WSSS.csv");
        controller.DrawRoutePath(path);
    }
}
